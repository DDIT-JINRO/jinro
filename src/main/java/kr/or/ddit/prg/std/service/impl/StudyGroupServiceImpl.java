package kr.or.ddit.prg.std.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import kr.or.ddit.chat.service.ChatMemberVO;
import kr.or.ddit.chat.service.ChatRoomVO;
import kr.or.ddit.chat.service.ChatService;
import kr.or.ddit.chat.service.impl.ChatMapper;
import kr.or.ddit.prg.std.service.StdBoardVO;
import kr.or.ddit.prg.std.service.StdReplyVO;
import kr.or.ddit.prg.std.service.StudyGroupService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudyGroupServiceImpl implements StudyGroupService{

	@Autowired
	StudyGroupMapper studyGroupMapper;

	@Autowired
	ChatService chatService;

	private final Map<String, String> regionMap = new HashMap<>();

	@PostConstruct
	public void setRegionMap() {
		List<Map<String, Object>> regionCodeList = this.studyGroupMapper.selectRegionNamesFromComCode();
		for(Map<String, Object> map : regionCodeList) {
	        String codeId = String.valueOf(map.get("CC_ID"));
	        String codeName = String.valueOf(map.get("CC_ETC"));
	        regionMap.put(codeId, codeName);
		}
		log.info("regionMap 초기화 완료: {}", regionMap);
	}

	@Override
	public List<StdBoardVO> selectStudyGroupList(StdBoardVO stdBoardVO) {
		List<StdBoardVO> list = this.studyGroupMapper.selectStudyGroupList(stdBoardVO);
		// content에 json형식의 문자열이 들어가있고 해당 값을 그대로 받아왔기때문에 처리단계.
		ObjectMapper mapper = new ObjectMapper();
		for (StdBoardVO board : list) {
			JsonNode json;
			try {
				json = mapper.readTree(board.getBoardContent());
				board.setRegion(regionMap.get(json.path("region").asText()));
				board.setGender(json.path("gender").asText());
				board.setInterest(json.path("interest").asText());
				board.setMaxPeople(json.path("maxPeople").asInt());
				board.setParsedContent(json.path("content").asText());
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public int insertStdBoard(StdBoardVO stdBoardVO) {
		int result = 0;
		int boardId = 0;
		result += this.studyGroupMapper.insertStdBoard(stdBoardVO);

		ChatRoomVO chatRoomVO = new ChatRoomVO();
		chatRoomVO.setTargetId(stdBoardVO.getBoardId());
		chatRoomVO.setCrTitle(stdBoardVO.getChatTitle());
		chatRoomVO.setCcId("G04001");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode json = objectMapper.readTree(stdBoardVO.getBoardContent());
			chatRoomVO.setCrMaxCnt(json.path("maxPeople").asInt());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		result += this.chatService.insertChatRoom(chatRoomVO);

		ChatMemberVO chatMemberVO = new ChatMemberVO();
		chatMemberVO.setCrId(chatRoomVO.getCrId());
		chatMemberVO.setMemId(stdBoardVO.getMemId());
		if(result >= 2) {
			boardId = stdBoardVO.getBoardId();
		}
		this.chatService.participateChatRoom(chatMemberVO);

		return boardId;
	}

	@Override
	public int selectStudyGroupTotalCount(StdBoardVO stdBoardVO) {
		return this.studyGroupMapper.selectStudyGroupTotalCount(stdBoardVO);
	}

	@Override
	public Map<String, String> getInterestsMap(){
		Map<String, String> interestMap = Map.ofEntries(
			    Map.entry("study.general", "공부"),
			    Map.entry("study.exam", "수능준비"),
			    Map.entry("study.assignment", "과제"),

			    Map.entry("career.path", "진로"),
			    Map.entry("career.admission", "진학"),

			    Map.entry("job.prepare", "취업준비"),
			    Map.entry("job.concern", "취업고민"),

			    Map.entry("social.neighbor", "동네친구"),
			    Map.entry("social.talk", "잡담")
			);
		return interestMap;
	}


	@Override
	public StdBoardVO selectStudyGroupDetail(int stdGroupId) {
		StdBoardVO stdBoardVO =this.studyGroupMapper.selectStudyGroupDetail(stdGroupId);
		if(stdBoardVO == null) return stdBoardVO;

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode json = objectMapper.readTree(stdBoardVO.getBoardContent());
			stdBoardVO.setRegion(regionMap.get(json.path("region").asText()));
			stdBoardVO.setGender(json.path("gender").asText());
			stdBoardVO.setInterest(json.path("interest").asText());
			stdBoardVO.setMaxPeople(json.path("maxPeople").asInt());
			stdBoardVO.setParsedContent(json.path("content").asText());

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		List<StdReplyVO> parentReplyList = stdBoardVO.getStdReplyVOList();
		if(parentReplyList != null && !parentReplyList.isEmpty() && parentReplyList.get(0).getReplyContent() != null) {
			for(StdReplyVO stdReplyVO : parentReplyList) {
				int childCount =stdReplyVO.getChildCount();
				if(childCount == 0) continue;

		         if (childCount > 0) {
		                int parentReplyId = stdReplyVO.getReplyId();
		                List<StdReplyVO> childReplies = getChildReplies(parentReplyId);
		                // 자식댓글 리스트 세팅
		                stdReplyVO.setChildReplyVOList(childReplies);
		         }
			}
		}else {
			parentReplyList.clear();
		}
		return stdBoardVO;
	}

	private List<StdReplyVO> getChildReplies(int replyId) {
		List<StdReplyVO> replyList = this.studyGroupMapper.selectChildReplyList(replyId);
		for (StdReplyVO replyVO : replyList) {
			if (replyVO.getChildCount() == 0)
				continue;
			int parentReplyId = replyVO.getReplyId();
			List<StdReplyVO> childReplies = getChildReplies(parentReplyId);
			// 자식댓글 리스트 세팅
			replyVO.setChildReplyVOList(childReplies);
		}
		return replyList;
	}

	@Override
	public Map<String, String> getRegionMap() {
		return this.regionMap;
	}

	@Override
	public void increaseViewCnt(int stdGroupId) {
		this.studyGroupMapper.increaseViewCnt(stdGroupId);
	}
}

package kr.or.ddit.prg.std.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.chat.service.ChatMemberVO;
import kr.or.ddit.chat.service.ChatRoomVO;
import kr.or.ddit.chat.service.ChatService;
import kr.or.ddit.prg.std.service.StdBoardVO;
import kr.or.ddit.prg.std.service.StdReplyVO;
import kr.or.ddit.prg.std.service.StudyGroupService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.alarm.service.AlarmService;
import kr.or.ddit.util.alarm.service.AlarmType;
import kr.or.ddit.util.alarm.service.AlarmVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/prg/std")
public class StudyGroupController {

	@Autowired
	StudyGroupService studyGroupService;

	@Autowired
	ChatService chatService;

	@Autowired
	AlarmService alarmService;


	@GetMapping("/stdGroupList.do")
	public String selectStdGroupList(@RequestParam(required = false) String region,
		    						@RequestParam(required = false) String gender,
		    						@RequestParam(required = false) String interest,
		    						@RequestParam(required = false) List<String> interestItems,
		    						@RequestParam(required = false) Integer maxPeople,
		    						@RequestParam(required = false) String searchType,
		    						@RequestParam(required = false) String searchKeyword,
		    						@RequestParam(required = false, defaultValue = "1") int currentPage,
		    						@RequestParam(required = false, defaultValue = "5") int size,
		    						@RequestParam(required = false, defaultValue = "createDesc") String sortBy,
		    						StdBoardVO stdBoardVO,
		    						Principal principal,
		    						Model model) {
		if(stdBoardVO!=null && stdBoardVO.getSize() == 0) stdBoardVO.setSize(size);
		if(stdBoardVO!=null && stdBoardVO.getCurrentPage() == 0) stdBoardVO.setCurrentPage(currentPage);

		int totalCount = this.studyGroupService.selectStudyGroupTotalCount(stdBoardVO);
		List<StdBoardVO> list = this.studyGroupService.selectStudyGroupList(stdBoardVO);

		ArticlePage<StdBoardVO> articlePage = new ArticlePage<>(totalCount, currentPage, size, list, searchKeyword);
		String baseUrl = buildQueryString(region, gender, interestItems, maxPeople, searchType, searchKeyword, size, sortBy);
		articlePage.setUrl(baseUrl);
		articlePage.setPagingArea("");

		if(principal!=null && !principal.getName().equals("anonymousUser")) {
			List<ChatRoomVO> roomList = chatService.findRoomsByMemId(principal.getName());
			Set<Integer> myChatRoomIds = roomList.stream()
				    .map(ChatRoomVO::getCrId)
				    .collect(Collectors.toSet());
			model.addAttribute("myRoomSet", myChatRoomIds);
		}
		// 지역목록맵<지역코드, 지역명> 을 받아와서 지역코드순으로 출력하기 위해 리스트로 변환하고 정렬
		Map<String, String> regionMap = this.studyGroupService.getRegionMap();
		ArrayList<Map.Entry<String, String>> regionList = new ArrayList<>(regionMap.entrySet());
		regionList.sort(Map.Entry.comparingByKey());

		model.addAttribute("articlePage", articlePage);
		model.addAttribute("interestMap", this.studyGroupService.getInterestsMap());
		model.addAttribute("regionList", regionList);

		model.addAttribute("region",region);
		model.addAttribute("gender", gender);
		model.addAttribute("interestItems", interestItems);
		model.addAttribute("maxPeople", maxPeople);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("size", size);
		model.addAttribute("sortBy", sortBy);
		System.out.println("###################################"+interestItems);
		return "prg/std/stdGroupList2";
	}

	@GetMapping("/stdGroupDetail.do")
	public String selectStdGroupDetail(@RequestParam int stdGroupId, Model model, Principal principal)  {
		model.addAttribute("stdGroupId", stdGroupId);
		// 단일 게시글 전체 내용에 댓글 리스트 + 채팅방정보 챙겨오기

		this.studyGroupService.increaseViewCnt(stdGroupId);
		StdBoardVO stdBoardVO = this.studyGroupService.selectStudyGroupDetail(stdGroupId);

		// 채팅방 참여했는지 여부를 체크하는 값 가져오기
		ChatRoomVO chatRoomVO = stdBoardVO.getChatRoomVO();
		if(principal!= null && !principal.getName().equals("unonymousUser") && chatRoomVO != null) {
			boolean isEntered = this.chatService.isEntered(chatRoomVO.getCrId(), principal.getName());
			model.addAttribute("isEntered", isEntered);
		}

		model.addAttribute("stdBoardVO", stdBoardVO);
		model.addAttribute("interestMap", this.studyGroupService.getInterestsMap());
		return "prg/std/stdGroupDetail2";
	}

	// page번호 버튼에 url 입력을 위한 base 쿼리스트링 구성
	private String buildQueryString(String region,String gender, List<String> interestItems, Integer maxPeople
								, String searchType, String searchKeyword, int size, String sortBy) {
		StringBuilder sb = new StringBuilder();
		sb.append("/prg/std/stdGroupList.do");
		sb.append("?").append("region=").append(region == null ? "" : region);
		sb.append("&").append("gender=").append(gender == null ? "" : gender);
		sb.append("&").append("maxPeople=").append(maxPeople == null ? "" : maxPeople);
		sb.append("&").append("searchType=").append(searchType == null ? "" : searchType);
		sb.append("&").append("searchKeyword=").append(searchKeyword == null ? "" : searchKeyword);
		sb.append("&").append("size=").append(size);

		if(interestItems == null || interestItems.size() == 0) return sb.toString();
		for(String interest : interestItems) {
			sb.append("&").append("interestItems=").append(interest == null ? "" : interest);
		}

		return sb.toString();
	}

	@PostMapping("/api/enterStdGroup")
	public ResponseEntity<String> enterStdGroup(@RequestBody ChatMemberVO chatMemberVO){
		try {
			this.chatService.participateChatRoom(chatMemberVO);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/createStdGroup.do")
	public String createStdGroup(@AuthenticationPrincipal String memId, Model model) {
		// jsp 측에서 막아놨지만 혹시 몰라서 걸어둠.
		if(memId == null || memId.equals("anonymousUser")) {
			return "/login";
		}

		Map<String, String> interestMap = this.studyGroupService.getInterestsMap();
		Map<String, String> regionMap = this.studyGroupService.getRegionMap();

		// 보관되어있는 regionMap<지역코드 : 지역명> 을 순서대로 정렬해서 보내기 위해 리스트로 변환 후 key순 정렬
		ArrayList<Map.Entry<String, String>> regionList = new ArrayList<>(regionMap.entrySet());
		regionList.sort(Map.Entry.comparingByKey());

		model.addAttribute("interestMap", interestMap);
		model.addAttribute("regionList", regionList);

		return "prg/std/createStdGroup";
	}

	@PostMapping("/createStdGroup.do")
	public String createStdGroupPost(StdBoardVO stdBoardVO) {
		int resultBoardId = this.studyGroupService.insertStdBoard(stdBoardVO);
		if(resultBoardId > 0) {
			return "redirect:/prg/std/stdGroupDetail.do?stdGroupId="+resultBoardId;
		}


		return "redirect:/prg/std/createStdGroup.do";
	}

	@PostMapping("/createStdReply.do")
	public String createStdReply(StdReplyVO stdReplyVO) {
		stdReplyVO.setReplyId(6);
		// 댓글 테이블에 삽입 (먼저 삽입이 되어야함 -> join해서 memId 챙겨옴)
		// 해당 댓글 번호를 targetId 로 넣어주기
		// 좋아요인 경우에는 좋아요가 달린 boardId나 replyId 넣어주기
		AlarmVO alarmVO = new AlarmVO();
		alarmVO.setAlarmTargetType(AlarmType.REPLY_TO_BOARD);
		alarmVO.setAlarmTargetId(stdReplyVO.getReplyId());
		alarmVO.setAlarmTargetUrl("/prg/std/stdGroupDetail.do?stdGroupId="+stdReplyVO.getBoardId());
		try {
			this.alarmService.sendEvent(alarmVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/prg/std/stdGroupDetail.do?stdGroupId="+stdReplyVO.getBoardId();
	}
}

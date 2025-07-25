package kr.or.ddit.prg.std.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.chat.service.ChatMemberVO;
import kr.or.ddit.chat.service.ChatRoomVO;
import kr.or.ddit.chat.service.ChatService;
import kr.or.ddit.mpg.mat.bmk.web.BookMarkController;
import kr.or.ddit.prg.std.service.StdBoardVO;
import kr.or.ddit.prg.std.service.StudyGroupService;
import kr.or.ddit.prg.std.service.impl.StudyGroupServiceImpl;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/prg/std")
public class StudyGroupController {

	@Autowired
	StudyGroupService studyGroupService;

	@Autowired
	ChatService chatService;


	@GetMapping("/stdGroupList.do")
	public String selectStdGroupList(@RequestParam(required = false) String region,
		    						@RequestParam(required = false) String gender,
		    						@RequestParam(required = false) String interest,
		    						@RequestParam(required = false) Integer maxPeople,
		    						@RequestParam(required = false) String searchType,
		    						@RequestParam(required = false) String searchKeyword,
		    						@RequestParam(required = false, defaultValue = "1") int currentPage,
		    						@RequestParam(required = false, defaultValue = "5") int size,
		    						StdBoardVO stdBoardVO,
		    						Principal principal,
		    						Model model) {
		if(stdBoardVO!=null && stdBoardVO.getSize() == 0) stdBoardVO.setSize(size);
		if(stdBoardVO!=null && stdBoardVO.getCurrentPage() == 0) stdBoardVO.setCurrentPage(currentPage);

		int totalCount = this.studyGroupService.selectStudyGroupTotalCount(stdBoardVO);
		List<StdBoardVO> list = this.studyGroupService.selectStudyGroupList(stdBoardVO);

		ArticlePage<StdBoardVO> articlePage = new ArticlePage<>(totalCount, currentPage, size, list, searchKeyword);
		String baseUrl = buildQueryString(region, gender, interest, maxPeople, searchType, searchKeyword, size);
		articlePage.setUrl(baseUrl);
		articlePage.setPagingArea("");

		Map<String, String> interestMap = this.studyGroupService.getInterestsMap();

		if(principal!=null && !principal.getName().equals("anonymousUser")) {
			List<ChatRoomVO> roomList = chatService.findRoomsByMemId(principal.getName());
			Set<Integer> myChatRoomIds = roomList.stream()
				    .map(ChatRoomVO::getCrId)
				    .collect(Collectors.toSet());
			model.addAttribute("myRoomSet", myChatRoomIds);
		}

		model.addAttribute("articlePage", articlePage);
		model.addAttribute("interestMap", interestMap);
		model.addAttribute("genderMap", Map.of("all", "성별무관", "men", "남자만", "women", "여자만"));
		return "prg/std/stdGroupList";
	}

	@GetMapping("/stdGroupDetail.do")
	public String selectStdGroupDetail(@RequestParam int stdGroupId, Model model, Principal principal)  {
		model.addAttribute("stdGroupId", stdGroupId);
		// 단일 게시글 전체 내용에 댓글 리스트 + 채팅방정보 챙겨오기

		StdBoardVO stdBoardVO = this.studyGroupService.selectStudyGroupDetail(stdGroupId);
		if(stdBoardVO == null) {
			// 게시글이 존재하지 않으면 badRequest 리턴 (파라미터값 임의로 변경시 스터디그룹이 아니면 막아야함)
		}else {

		}
		
		
		// 채팅방 참여했는지 여부를 체크하는 값 가져오기
		ChatRoomVO chatRoomVO = stdBoardVO.getChatRoomVO();
		if(principal!= null && !principal.getName().equals("unonymousUser") && chatRoomVO != null) {
			boolean isEntered = this.chatService.isEntered(chatRoomVO.getCrId(), principal.getName());
			model.addAttribute("isEntered", isEntered);
		}
		
		model.addAttribute("stdBoardVO", stdBoardVO);
		model.addAttribute("interestMap", this.studyGroupService.getInterestsMap());
		return "prg/std/stdGroupDetail";
	}

	// page번호 버튼에 url 입력을 위한 base 쿼리스트링 구성
	private String buildQueryString(String region,String gender, String interest, Integer maxPeople
								, String searchType, String searchKeyword, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append("/prg/std/stdGroupList.do");
		sb.append("?").append("region=").append(region == null ? "" : region);
		sb.append("&").append("gender=").append(gender == null ? "" : gender);
		sb.append("&").append("interest=").append(interest == null ? "" : interest);
		sb.append("&").append("maxPeople=").append(maxPeople == null ? "" : maxPeople);
		sb.append("&").append("searchType=").append(searchType == null ? "" : searchType);
		sb.append("&").append("searchKeyword=").append(searchKeyword == null ? "" : searchKeyword);
		sb.append("&").append("size=").append(size);

		return sb.toString();
	}

	@PostMapping("/api/enterStdGroup")
	public ResponseEntity<String> enterStdGroup(@RequestBody ChatMemberVO chatMemberVO){
		log.info("enterStdGroup -> chatMemberVO : "+chatMemberVO);
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
		System.out.println("@@@@@@@@@@@@@@memId : "+memId);
		if(memId == null || memId.equals("anonymousUser")) {
			return "/login";
		}
		
		Map<String, String> interestMap = this.studyGroupService.getInterestsMap();
		Map<String, String> regionMap = this.studyGroupService.getRegionMap();
		
		model.addAttribute("interestMap", interestMap);
		model.addAttribute("regionMap", regionMap);
		
		return "prg/std/createStdGroup";
	}
	
	@PostMapping("/createStdGroup.do")
	public String createStdGroupPost(StdBoardVO stdBoardVO) {
		log.info("createStdGroupPost -> stdBoardVO : "+ stdBoardVO);
		int resultBoardId = this.studyGroupService.insertStdBoard(stdBoardVO);
		if(resultBoardId > 0) {
			return "redirect:/prg/std/stdGroupDetail.do?stdGroupId="+resultBoardId;
		}
		
		
		return "redirect:/prg/std/createStdGroup.do";
	}

}

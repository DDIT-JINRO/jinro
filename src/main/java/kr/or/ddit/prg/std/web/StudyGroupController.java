package kr.or.ddit.prg.std.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.chat.service.ChatRoomVO;
import kr.or.ddit.chat.service.ChatService;
import kr.or.ddit.mpg.mat.bmk.web.BookMarkController;
import kr.or.ddit.prg.std.service.StdBoardVO;
import kr.or.ddit.prg.std.service.StudyGroupService;
import kr.or.ddit.prg.std.service.impl.StudyGroupServiceImpl;
import kr.or.ddit.util.ArticlePage;

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
		System.out.println("===================stdGroupList.do 파라미터조회==========================");
		System.out.println("region : "+region);
		System.out.println("gender : "+gender);
		System.out.println("interest : "+interest);
		System.out.println("maxPeople : "+maxPeople);
		System.out.println("searchType : "+searchType);
		System.out.println("searchKeyword : "+searchKeyword);
		System.out.println("stdBoardVO : "+stdBoardVO);
		System.out.println("currentPage : "+currentPage);
		System.out.println("size : "+size);
		System.out.println("======================================================================");
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
		return "prg/std/stdGroupList";
	}
	
	@GetMapping("/stdGroupDetail.do")
	public String selectStdGroupDetail(@RequestParam int stdGroupId, Model model) {
		model.addAttribute("stdGroupId", stdGroupId);
		
		return "prg/std/stdGroupDetail";
	}
	
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
	
}

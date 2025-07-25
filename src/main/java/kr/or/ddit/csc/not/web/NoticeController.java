package kr.or.ddit.csc.not.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.csc.not.service.NoticeService;
import kr.or.ddit.csc.not.service.NoticeVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/csc")
// 공지사항 컨트롤러
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	
	// 공지사항 리스트
	@GetMapping("/noticeList.do")
	public String noticeList(Model model,
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(value="size",required=false,defaultValue="5") int size,
			@RequestParam(value="keyword",required=false) String keyword) {

		int startNo = (currentPage - 1) * size + 1;
		int endNo = currentPage * size;
		
		// 파라미터
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("currentPage", currentPage);
		map.put("startNo", startNo);
		map.put("endNo", endNo);
		log.info("map : "+map);
		
		
		// 공지사항 게시글
		List<NoticeVO>list = noticeService.getList(map);
		log.info("list"+list);
		
		// 전체 게시글 수
		int getAllNotice = noticeService.getAllNotice(map);
		log.info("getAllNotice : "+getAllNotice);
		
		//---URL 설정 필수 ########################################
		//페이징 객체 생성
		ArticlePage<NoticeVO> articlePage = 
				new ArticlePage<NoticeVO>(getAllNotice, currentPage, 5, list, keyword);
		log.info("list->articlePage : " + articlePage);
		articlePage.setUrl("/csc/noticeList.do");
		
		model.addAttribute("articlePage", articlePage);
		model.addAttribute("getAllNotice", getAllNotice);
		model.addAttribute("getList",list);
		
		
		return "csc/noticeList";
	}

	
	
	// 공지사항 세부 화면
	@GetMapping("/noticeDetail.do")
	public String noticeDetail(@RequestParam String no, Model model) {

		log.info("no : " + no);
		// 게시글 조회수 +1
		int cnt  = noticeService.upNoticeCnt(no);
		log.info("no : "+no);
		
		// 게시글 상세 조회
		NoticeVO noticeDetail = noticeService.getNoticeDetail(no);
		log.info("noticeDetail : " + noticeDetail);
		
		model.addAttribute("noticeDetail", noticeDetail);

		return "csc/noticeDetail"; // JSP 경로: /WEB-INF/views/csc/noticeDetail.jsp
	}
	
//	//관리자 공지사항 목록 조회
//	//비동기 통신
//	@ResponseBody
//	@PostMapping("admin/noticeList.do")
//	public Map<String, Object> adminNoticeList() {
//		log.info("adminNoticeList");
//	    List<NoticeVO> list = noticeService.getList();
//	    int getAllNotice = noticeService.getAllNotice();
//
//	    Map<String, Object> result = new HashMap<>();
//	    result.put("getList", list);
//	    result.put("getAllNotice", getAllNotice);
//
//	    return result;
//	}
	
	
}

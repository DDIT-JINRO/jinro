package kr.or.ddit.csc.not.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import kr.or.ddit.csc.not.service.NoticeService;
import kr.or.ddit.csc.not.service.NoticeVO;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.file.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/csc/not")
// 공지사항 컨트롤러
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	
	@Autowired
	FileService fileService;
	
	// 공지사항 리스트
	@GetMapping("/noticeList.do")
	public String noticeList(Model model,
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(value="size",required=false,defaultValue="5") int size,
			@RequestParam(value="keyword",required=false) String keyword) {

		
	    ArticlePage<NoticeVO> articlePage = noticeService.getUserNoticePage(currentPage, size, keyword);
	    model.addAttribute("articlePage", articlePage);
	    model.addAttribute("getAllNotice", articlePage.getTotal());
	    model.addAttribute("getList", articlePage.getContent());
		
		return "csc/noticeList";
	}
	
	// 공지사항 세부 화면
	@GetMapping("/noticeDetail.do")
	public String noticeDetail(@RequestParam String noticeId, Model model) {

		NoticeVO noticeDetail = noticeService.getUserNoticeDetail(noticeId);
		model.addAttribute("noticeDetail", noticeDetail);

		return "csc/noticeDetail";
	}
	
	// 관리자 목록 조회
	@ResponseBody
	@GetMapping("/admin/noticeList.do")
	public ArticlePage<NoticeVO> adminNoticeList(@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(value="size",required=false,defaultValue="5") int size,
			@RequestParam(value="keyword",required=false) String keyword,
			//연도별 구분
			@RequestParam(value="status",required = false)String status) {

		ArticlePage<NoticeVO> articlePage = noticeService.getAdminNoticePage(currentPage, size, keyword, status);

		return articlePage;
	}
	
	// 관리자 공지사항 세부 화면
	@ResponseBody
	@GetMapping("/admin/noticeDetail.do")
	public NoticeVO adminNoticeDetail(@RequestParam String noticeId) {

		// 게시글 상세 조회
		NoticeVO noticeDetail = noticeService.getAdminNoticeDetail(noticeId);
		
		return noticeDetail ;
	}

	// 관리자 공지사항 등록
	// consumes = MediaType.MULTIPART_FORM_DATA_VALUE => 멀티파트 요청임을 명시하면 MultipartResolver 가 동작해서 List<MultipartFile> 을 채워 줍니다.
	// @ModelAttribute => 파일(MultipartFile)과 폼 필드를 섞어 받을 땐 무조건 @ModelAttribute 로 바인딩해야 합니다.
	@ResponseBody
	@PostMapping(value = "/admin/insertNotice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public int insertNotice(@ModelAttribute NoticeVO noticeVo) {
		
		int noticeCount =noticeService.insertNotice(noticeVo); 
	
		return noticeCount;
	}
	
	//파일삭제
	@ResponseBody
	@GetMapping("/admin/deleteFile")
	public boolean deleteFile(@RequestParam Long groupId, @RequestParam int seq) {

		boolean resultDeleteFile =fileService.deleteFile(groupId, seq);

		return resultDeleteFile;
	}
	
	//파일 수정
	@ResponseBody
	@PostMapping(value = "/admin/updateNotice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public int updateNotice(@ModelAttribute NoticeVO noticeVo) {

        int noticeCount = noticeService.updateNotice(noticeVo);
        
		return noticeCount;
	}
	
	@ResponseBody
	@PostMapping("/admin/deleteNotice")
	public int deleteNotice(@ModelAttribute NoticeVO noticeVO) {
		
		int noticeId = noticeVO.getNoticeId();
		int resultNotice = noticeService.deleteNotice(noticeId);
		
		List<MultipartFile> files = noticeVO.getFiles();

		if (files != null && !files.isEmpty()) {
		    // 유효한 파일만 필터링 (빈 파일, 이름 없는 파일 제외)
		    List<MultipartFile> validFiles = files.stream()
		        .filter(file -> file != null && !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
		        .toList();

		    if (!validFiles.isEmpty()) {
		    	boolean result = fileService.deleteFileGroup(null);
		    }
		} 
		
		return resultNotice;
	}
}

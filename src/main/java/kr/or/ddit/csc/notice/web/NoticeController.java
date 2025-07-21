package kr.or.ddit.csc.notice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/csc")
// 공지사항 컨트롤러
public class NoticeController {

	// 공지사항 리스트
	@GetMapping("/noticeList.do")
	public String noticeList() {
		return "csc/noticeList";
	}

	// 공지사항 세부화면
	@GetMapping("/noticeDetail.do")
	public String noticeDetail() {
		return "csc/noticeDetail";
	}
}

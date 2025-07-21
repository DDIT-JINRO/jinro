package kr.or.ddit.csc.faq.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
//FAQ컨트롤러
@RequestMapping("/csc")
public class FAQController {

	// FAQ 리스트
	@GetMapping("/faqList.do")
	public String noticeList() {
		return "csc/faqList";
	}
}

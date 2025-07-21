package kr.or.ddit.csc.faq.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
//FAQ컨트롤러
public class FAQController {

	// FAQ 리스트
	@GetMapping("/FAQList")
	public String noticeList() {
		return "csc/FAQList";
	}
}

package kr.or.ddit.csc.inqry.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
//1:1문의 컨트롤러
@RequestMapping("/csc")
public class InqryController {
	// 1:1문의 리스트
	@GetMapping("/INQRYList.do")
	public String noticeList() {
		return "csc/INQRYList";
	}
}

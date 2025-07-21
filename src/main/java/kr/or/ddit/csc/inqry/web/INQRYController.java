package kr.or.ddit.csc.inqry.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
//1:1문의 컨트롤러
public class INQRYController {
	// 1:1문의 리스트
	@GetMapping("/INQRYList")
	public String noticeList() {
		return "csc/INQRYList";
	}
}

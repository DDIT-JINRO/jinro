package kr.or.ddit.account.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	
	// 로그인 페이지 이동
	@GetMapping("/login")
	public String login() {
		
		log.info("login arrive");
		
		return "account/login";
	}
	
	@GetMapping("/test")
	public String testPg() {
		return "account/test";
	}
	
	@GetMapping("/error/logReq")
	public String errorLogReq() {
		
		return ("account/loginReq");
	}
	
}

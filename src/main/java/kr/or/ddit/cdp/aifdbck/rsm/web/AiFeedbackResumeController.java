package kr.or.ddit.cdp.aifdbck.rsm.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/aifdbck/rsm")
@Controller
@Slf4j
public class AiFeedbackResumeController {
	@GetMapping("/aiFeedbackResumeList.do")
	public String aiImitationInterviewPage(Principal principal) {

		// 로그인하지 않은 경우 처리
		if (principal == null || principal.getName().equals("anonymousUser")) {
			return "redirect:/login"; // 로그인 페이지로 리다이렉트
		}

		
		return "cdp/aifdbck/rsm/aiFeedbackResume";
	}
	
	@GetMapping("/detail.do")
	public String aiImitationInterviewDetailPage() {
		return "cdp/aifdbck/rsm/aiFeedbackResumeDetail";
	}
}

package kr.or.ddit.cdp.aifdbck.sint.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.web.AiImitationInterviewController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/aifdbck/sint")
@Controller
@Slf4j
public class AiFeedbackSelfIntroController {
	@GetMapping("/aiFeedbackSelfIntroList.do")
	public String aiImitationInterviewPage() {
		return "cdp/aifdbck/sint/aiFeedbackSelfIntro";
	}
	
	@GetMapping("/detail.do")
	public String aiImitationInterviewDetailPage() {
		return "cdp/aifdbck/sint/aiFeedbackSelfIntroDetail";
	}
}

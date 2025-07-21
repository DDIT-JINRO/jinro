package kr.or.ddit.cdp.aifdbck.rsm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.web.AiImitationInterviewController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/aifdbck/rsm")
@Controller
@Slf4j
public class AiFeedbackResumeController {
	@GetMapping()
	public String aiImitationInterviewPage() {
		return "cdp/aifdbck/rsm/aiFeedbackResume";
	}
	
	@GetMapping("/detail.do")
	public String aiImitationInterviewDetailPage() {
		return "cdp/aifdbck/rsm/aiFeedbackResumeDetail";
	}
}

package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/imtintrvw/aiimtintrvw")
@Controller
@Slf4j
public class AiImitationInterviewController {
	
	@GetMapping("/aiImitationInterview.do")
	public String aiImitationInterviewPage() {
		return "cdp/imtintrvw/aiimtintrvw/aiImitationInterview";
	}
	
	@GetMapping("/detail.do")
	public String aiImitationInterviewDetailPage() {
		return "cdp/imtintrvw/aiimtintrvw/aiImitationInterviewDetail";
	}
}

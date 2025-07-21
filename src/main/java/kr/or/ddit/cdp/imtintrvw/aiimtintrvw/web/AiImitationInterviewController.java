package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.imtintrvw.bsintrvw.web.BassInterviewController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/imtintrvw/aiimtintrvw")
@Controller
@Slf4j
public class AiImitationInterviewController {
	@GetMapping()
	public String aiImitationInterviewPage() {
		return "cdp/imtintrvw/aiimtintrvw/aiImitationInterview";
	}
}

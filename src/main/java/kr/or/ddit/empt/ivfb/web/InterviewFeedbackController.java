package kr.or.ddit.empt.ivfb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empt")
public class InterviewFeedbackController {
	
	@GetMapping("/ivfb/interViewFeedback.do")
	public String interViewFeedbackList() {
		return "empt/ivfb/interviewFeedback";
	}
	
	
}

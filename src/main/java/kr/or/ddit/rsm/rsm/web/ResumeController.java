package kr.or.ddit.rsm.rsm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;



@RequestMapping("/resume")
@Controller
@Slf4j
public class ResumeController {

	@GetMapping()
	public String resumePage() {
		return "rsm/rsm/resume"; // /WEB-INF/views/resume/resume.jsp
	}
	
	@GetMapping("/resumedetail")
	public String resumedeatilPage() {
		return "rsm/rsm/resumeDetail"; // /WEB-INF/views/resume/resume.jsp
	}
	
}
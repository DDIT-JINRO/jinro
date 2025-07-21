package kr.or.ddit.cdp.rsm.rsm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;



@RequestMapping("/rsm/rsm")
@Controller
@Slf4j
public class ResumeController {

	@GetMapping()
	public String resumePage() {
		return "cdp/rsm/rsm/resume";
	}
	
	@GetMapping("/detail.do")
	public String resumedeatilPage() {
		return "cdp/rsm/rsm/resumeDetail";
	}
	
}
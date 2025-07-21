package kr.or.ddit.cdp.rsm.rsmb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.rsm.rsm.web.ResumeController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/rsm/rsmb")
@Controller
@Slf4j
public class ResumeBoardController {
	@GetMapping()
	public String resumeBoardPage() {
		return "cdp/rsm/rsmb/resumeBoard";
	}
	
	@GetMapping("/detail.do")
	public String resumeBoardDetailPage() {
		return "cdp/rsm/rsmb/resumeBoardDetail";
	}
}

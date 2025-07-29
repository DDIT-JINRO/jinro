package kr.or.ddit.cdp.rsm.rsm.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.rsm.rsm.service.ResumeSectionVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@RequestMapping("/rsm/rsm")
@Controller
@Slf4j
public class ResumeController {
	
	private final ResumeService resumeService;

	@GetMapping()
	public String resumePage() {
		return "cdp/rsm/rsm/resume";
	}
	
	@GetMapping("/detail.do")
	public String resumedeatilPage() {
		return "cdp/rsm/rsm/resumeDetail";
	}
	
	@GetMapping("/getElement")
	public ResponseEntity<String>getElement(@ModelAttribute  ResumeSectionVO resumeSectionVO){
		log.info("resumeSectionVO "+resumeSectionVO);
		
		String element = resumeService.getElement(resumeSectionVO);
		
		return ResponseEntity.ok(element);
		
	}
	
	
}
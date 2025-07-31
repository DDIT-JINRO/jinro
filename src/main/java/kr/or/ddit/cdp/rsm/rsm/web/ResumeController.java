package kr.or.ddit.cdp.rsm.rsm.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.cdp.rsm.rsm.service.ResumeDetailVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeSectionVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeService;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeVO;
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
	
	@GetMapping("/resumeWriter")
	public String resumedeatilPage(@ModelAttribute ResumeVO resumeVO,Model model) {
		log.info("resumeVO"+resumeVO);
		
		resumeVO =  resumeService.selectResumeByResumeId(resumeVO);
		
		model.addAttribute("resumeVO",resumeVO);
		return "cdp/rsm/rsm/resumeWriter";
	}
	
	@GetMapping("/getElement")
	public ResponseEntity<String>getElement(@ModelAttribute  ResumeSectionVO resumeSectionVO){
		log.info("resumeSectionVO "+resumeSectionVO);
		
		String element = resumeService.getElement(resumeSectionVO);
		
		return ResponseEntity.ok(element);
		
	}
	
	@PostMapping("/insertResume")
	public String insertResume(
								Principal principal,
								@ModelAttribute ResumeVO resumeVO,
								Model model) throws UnsupportedEncodingException{
		// URL 디코딩;
	      if(principal!=null && !principal.getName().equals("anonymousUser")) {
	    	  resumeVO.setMemId(Integer.parseInt( principal.getName()));
	    	  resumeVO = resumeService.mergeIntoResume(resumeVO);
	    	  
	    	  return "redirect:/rsm/rsm/resumeWriter?resumeId="+resumeVO.getResumeId();//+resumeId;
	       }else {
	    	   
	    	   return "redirect:/login";
	       }
	      
	}
	
	
	
}
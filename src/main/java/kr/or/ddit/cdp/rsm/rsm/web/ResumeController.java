package kr.or.ddit.cdp.rsm.rsm.web;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public Map<String, Object> insertResume(
								Principal principal,
								@ModelAttribute ResumeVO resumeVO,
								Model model) throws UnsupportedEncodingException{
			log.info("resumeVO :" +resumeVO);
			Map<String, Object> result = new HashMap<>();
	      if(principal!=null && !principal.getName().equals("anonymousUser")) {
	    	  resumeVO.setMemId(Integer.parseInt( principal.getName()));
	    	  resumeVO = resumeService.mergeIntoResume(resumeVO);
	    	  
	    	  result.put("status", "success");
	          result.put("resumeId", resumeVO.getResumeId());
	       }else {
	    	   result.put("status", "unauthorized");
	       }
	      log.info("result : "+result);
		  return result;
	      
	}
	
	
	
}
package kr.or.ddit.cdp.rsm.rsm.web;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.cdp.rsm.rsm.service.ResumeSectionVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeService;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeVO;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.util.ArticlePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@RequestMapping("/rsm/rsm")
@Controller
@Slf4j
public class ResumeController {
	
	private final ResumeService resumeService;

	@GetMapping()//resumeList.do
	public String resumePage(Principal principal, Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) String status,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size) {
		
		if (principal != null && !principal.getName().equals("anonymousUser")) {
			String memId = principal.getName();

			ResumeVO resumeVO = new ResumeVO();
			resumeVO.setMemId(Integer.parseInt(memId));
			resumeVO.setCurrentPage(currentPage);
			resumeVO.setSize(5);
			resumeVO.setKeyword(keyword);
			resumeVO.setStatus(status);

			// 사용자 자소서 리스트 불러옴
			List<ResumeVO> ResumeVOList = resumeService.selectResumeBymemId(resumeVO);

			ArticlePage<ResumeVO> articlePage = new ArticlePage<ResumeVO>(ResumeVOList.size(), currentPage, 5,
					ResumeVOList, keyword);
			log.info("list->articlePage : " + articlePage);
			articlePage.setUrl("/rsm/rsm");

			model.addAttribute("articlePage", articlePage);

		} else {
			return "redirect:/login";
		}
		
		return "cdp/rsm/rsm/resume";
	}
	
	@GetMapping("/resumeWriter")
	public String resumedeatilPage(@ModelAttribute ResumeVO resumeVO,Model model) {
		resumeVO =  resumeService.selectResumeByResumeId(resumeVO);
		
		model.addAttribute("resumeVO",resumeVO);
		return "cdp/rsm/rsm/resumeWriter";
	}
	
	@GetMapping("/getElement")
	public ResponseEntity<String>getElement(@ModelAttribute  ResumeSectionVO resumeSectionVO){
		
		String element = resumeService.getElement(resumeSectionVO);
		
		return ResponseEntity.ok(element);
		
	}
	
	@PostMapping("/insertResume")
	@ResponseBody
	public Map<String, Object> insertResume(
								Principal principal,
								@ModelAttribute ResumeVO resumeVO,
								Model model) throws UnsupportedEncodingException{
		log.info("resumeVO -> "+resumeVO);
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
package kr.or.ddit.cnslt.resve.cnsh.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cnslt.resve.crsv.service.CounselingReserveService;
import kr.or.ddit.cnslt.resve.crsv.service.CounselingVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cnslt/resve")
@Controller
@Slf4j
@RequiredArgsConstructor
public class CounselingReserveHistoryController {
	
	CounselingReserveService counselingReserveService;
	
	@GetMapping("/cnsh/counselingReserveHistory.do")
	public String counselingHistory(
			@ModelAttribute CounselingVO counselingVO,
			Principal principal) {
		
	    if(principal!=null && !principal.getName().equals("anonymousUser")) {
	         
	      }else {
	    	  return "/login";
	      }
	    
		return "cnslt/resve/cnsh/counselingreservehistory";
	}
	
}

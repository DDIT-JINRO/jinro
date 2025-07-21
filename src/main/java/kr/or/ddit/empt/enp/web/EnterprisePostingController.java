package kr.or.ddit.empt.enp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empt")
public class EnterprisePostingController {
	
	@GetMapping("/enp/enterprisePosting.do")
	public String enterprisePosting() {
		
		return "empt/enp/enterprisePosting";
	}
}

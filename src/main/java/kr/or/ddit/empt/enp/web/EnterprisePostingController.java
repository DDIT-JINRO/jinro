package kr.or.ddit.empt.enp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.empt.enp.service.EnterprisePostingService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/empt")
@RequiredArgsConstructor
public class EnterprisePostingController {
	
	private final EnterprisePostingService enterprisePostingService;
	
	@GetMapping("/enp/enterprisePosting.do")
	public String enterprisePosting() {
		
		return "empt/enp/enterprisePosting";
	}
}

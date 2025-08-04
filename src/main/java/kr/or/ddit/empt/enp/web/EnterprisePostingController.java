package kr.or.ddit.empt.enp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.empt.enp.service.EnterprisePostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/empt")
@RequiredArgsConstructor
@Slf4j
public class EnterprisePostingController {
	
	private final EnterprisePostingService enterprisePostingService;
	
	@GetMapping("/enp/enterprisePosting.do")
	public String enterprisePosting() {
		
		return "empt/enp/enterprisePosting";
	}
	
	
}

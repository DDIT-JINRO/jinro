package kr.or.ddit.empt.ema.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empt")
public class EmploymentAdvertisementController {
	
	@GetMapping("/ema/employmentAdvertisement.do")
	public String emplymentAdvertisementList() {
		return "empt/ema/employmentAdvertisement";
	}
	
}

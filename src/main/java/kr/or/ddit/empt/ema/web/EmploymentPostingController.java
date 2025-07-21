package kr.or.ddit.empt.ema.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empt")
public class EmploymentPostingController {
	
	@GetMapping("/emp/employmentPosting.do")
	public String emplymentPostingList() {
		return "empt/emp/employmentPosting";
	}
	
}

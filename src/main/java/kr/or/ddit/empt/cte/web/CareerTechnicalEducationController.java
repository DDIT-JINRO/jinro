package kr.or.ddit.empt.cte.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empt")
public class CareerTechnicalEducationController {
	
	@GetMapping("/cte/careerTechnicalEducation.do")
	public String careerTechEduList() {
		
		return "empt/cte/careerTechnicalEducation";
	}
	
}

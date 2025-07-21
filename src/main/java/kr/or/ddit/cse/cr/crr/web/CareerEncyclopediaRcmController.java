package kr.or.ddit.cse.cr.crr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/cse")
@Slf4j
public class CareerEncyclopediaRcmController {
	
	@GetMapping("/cr/crr/selectCareerRcmList.do")
	public String selectCareerRcmList () {
		return "cse/cr/crr/careerEncyclopediaRcmList";
	}
	
	@GetMapping("/cr/crr/selectCareerRcmDetail.do")
	public String selectCareerRcmDetail () {
		return "cse/cr/crr/careerEncyclopediaRcmDetail";
	}
}

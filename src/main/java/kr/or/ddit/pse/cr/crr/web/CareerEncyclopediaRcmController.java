package kr.or.ddit.pse.cr.crr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/pse")
@Slf4j
public class CareerEncyclopediaRcmController {
	
	@GetMapping("/cr/crr/selectCareerRcmList.do")
	public String selectCareerRcmList () {
		return "pse/cr/crr/careerEncyclopediaRcmList";
	}
	
	@GetMapping("/cr/crr/selectCareerRcmDetail.do")
	public String selectCareerRcmDetail () {
		return "pse/cr/crr/careerEncyclopediaRcmDetail";
	}
}

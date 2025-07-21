package kr.or.ddit.pse.cr.crl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/pse")
@Slf4j
public class CareerEncyclopediaController {
	
	@GetMapping("/cr/crl/selectCareerList.do")
	public String selectCareerList () {
		return "pse/cr/crl/careerEncyclopediaList";
	}
	
	@GetMapping("/cr/crl/selectCareerDetail.do")
	public String selectCareerDetail () {
		return "pse/cr/crl/careerEncyclopediaDetail";
	}
}

package kr.or.ddit.cse.cr.crl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/cse")
@Slf4j
public class CareerEncyclopediaController {
	
	@GetMapping("/cr/crl/selectCareerList.do")
	public String selectCareerList () {
		return "cse/cr/crl/careerEncyclopediaList";
	}
	
	@GetMapping("/cr/crl/selectCareerDetail.do")
	public String selectCareerDetail () {
		return "cse/cr/crl/careerEncyclopediaDetail";
	}
}

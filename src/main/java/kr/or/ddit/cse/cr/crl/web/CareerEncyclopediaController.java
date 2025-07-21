package kr.or.ddit.cse.cr.crl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/career")
@Slf4j
public class CareerEncyclopediaController {
	
	@GetMapping("/cse/cr/crl/selectCareerList.do")
	public String selectCareerList () {
		return "cse/cr/crl/careerEncyclopediaList";
	}
	
	@GetMapping("/cse/cr/crl/selectCareerDetail.do")
	public String selectCareerDetail () {
		return "cse/cr/crl/careerEncyclopediaDetail";
	}
}

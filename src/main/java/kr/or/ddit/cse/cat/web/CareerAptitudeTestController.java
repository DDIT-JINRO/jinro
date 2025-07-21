package kr.or.ddit.cse.cat.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/career")
@Slf4j
public class CareerAptitudeTestController {
	
	@GetMapping("/cse/cat/CareerAptitudeTest.do")
	public String selectCareerList () {
		return "cse/cat/CareerAptitudeTest";
	}
}

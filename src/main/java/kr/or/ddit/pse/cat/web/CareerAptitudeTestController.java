package kr.or.ddit.pse.cat.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/pse")
@Slf4j
public class CareerAptitudeTestController {
	
	@GetMapping("/cat/careerAptitudeTest.do")
	public String careerAptitudeTestView () {
		return "pse/cat/careerAptitudeTestView";
	}
}

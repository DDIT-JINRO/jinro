package kr.or.ddit.pse.cr.cco.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/pse")
@Slf4j
public class CareerComparisonController {

	@GetMapping("/cr/cco/careerComparisonView.do")
	public String careerComparisonView () {
		return "pse/cr/cco/careerComparisonView";
	}
}

package kr.or.ddit.mpg.mif.inq.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class MyInfoController {

	@GetMapping("/mif/inq/selectMyInfoView.do")
	public String selectCareerList () {
		return "mpg/mif/inq/selectMyInfoView";
	}
	
}

package kr.or.ddit.cdp.imtintrvw.intrvwitr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/imtintrvw/intrvwitr")
@Controller
@Slf4j
public class ImitationInterviewIntroController {

	@GetMapping("/interviewIntro.do")
	public String interviewIntroPage() {
		return "cdp/imtintrvw/intrvwitr/interviewIntro";
	}
}

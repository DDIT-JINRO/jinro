package kr.or.ddit.cdp.imtintrvw.intrvwqestnmn.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/imtintrvw/intrvwqestnmn")
@Controller
@Slf4j
public class InterviewQuestionMangementController {
	
	@GetMapping("/interviewQuestionMangementList.do")
	public String interviewQuestionMangement() {
		return "cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangement";
	}
	
	@GetMapping("detail.do")
	public String interviewQuestionMangementDetail() {
		return "cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangementDetail";
	}
}

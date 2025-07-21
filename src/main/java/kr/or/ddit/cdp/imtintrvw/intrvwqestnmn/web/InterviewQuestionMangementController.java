package kr.or.ddit.cdp.imtintrvw.intrvwqestnmn.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.imtintrvw.intrvwqestnlst.web.InterviewQuestionListController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/imtintrvw/intrvwqestnmn")
@Controller
@Slf4j
public class InterviewQuestionMangementController {
	@GetMapping()
	public String interviewQuestionMangement() {
		return "cdp/imtintrvw/intrvwqestnmn/interviewquestionmangement";
	}
	
	@GetMapping("detail.do")
	public String interviewQuestionMangementDetail() {
		return "cdp/imtintrvw/intrvwqestnmn/interviewquestionmangementDetail";
	}
}

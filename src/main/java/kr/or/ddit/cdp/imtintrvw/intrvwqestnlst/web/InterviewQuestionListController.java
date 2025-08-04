package kr.or.ddit.cdp.imtintrvw.intrvwqestnlst.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/imtintrvw/intrvwqestnlst")
@Controller
@Slf4j
public class InterviewQuestionListController {
	
	@GetMapping("/intrvwQuestionList.do")
	public String interviewQuestionListPage() {
		return "cdp/imtintrvw/intrvwqestnlst/interviewQuestionList";
	}
	
	
}

package kr.or.ddit.cdp.sint.qestnlst.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.rsm.rsm.web.ResumeController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/sint/qestnlst")
@Controller
@Slf4j
public class QuestionListController {
	@GetMapping()
	public String interviewQuestionListPage() {
		return "cdp/sint/qestnlst/questionList";
	}
	
	
}

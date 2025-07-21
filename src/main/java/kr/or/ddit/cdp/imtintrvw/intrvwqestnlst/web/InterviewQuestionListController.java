package kr.or.ddit.cdp.imtintrvw.intrvwqestnlst.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.rsm.rsm.web.ResumeController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/imtintrvw/qestnlst")
@Controller
@Slf4j
public class InterviewQuestionListController {
	@GetMapping()
	public String questionListPage() {
		return "cdp/imtintrvw/intrvwqestnlst/intrvwquestionList";
	}
	
	
}

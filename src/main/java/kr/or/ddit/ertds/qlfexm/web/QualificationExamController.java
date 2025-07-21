package kr.or.ddit.ertds.qlfexm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/qlfexm")
@Controller
@Slf4j
public class QualificationExamController {

	// 검정고시
	@GetMapping("/selectQlfexmList.do")
	public String qlfexmListPage() {

		return "ertds/qlfexm/list"; // /WEB-INF/views/erds/qlfexm/list.jsp

	}

}

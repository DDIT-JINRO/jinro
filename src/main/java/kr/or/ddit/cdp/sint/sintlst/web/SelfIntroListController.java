package kr.or.ddit.cdp.sint.sintlst.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.sint.qestnlst.web.QuestionListController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/sint/sintlst")
@Controller
@Slf4j
public class SelfIntroListController {
	@GetMapping()
	public String selfIntroListPage() {
		return "cdp/sint/sintlst/selfIntroList";
	}
	
	@GetMapping("detail.do")
	public String selfIntroDetailPage() {
		return "cdp/sint/sintlst/selfIntroDetail";
	}
}

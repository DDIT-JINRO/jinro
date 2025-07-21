package kr.or.ddit.cdp.sint.sintwrt.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.sint.sintlst.web.SelfIntroListController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/sint/sintwrt")
@Controller
@Slf4j
public class SelfIntroWritingController {

	@GetMapping()
	public String selfIntroWritingPage() {
		return "cdp/sint/sintwrt/selfIntroWriting";
	}
}

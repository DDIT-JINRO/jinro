package kr.or.ddit.cnslt.aicns.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cnslt/aicns")
public class AICounselingController {

	@GetMapping("/aicns.do")
	public String aicns() {
		return "cnslt/aicns/aicns";
	}

}

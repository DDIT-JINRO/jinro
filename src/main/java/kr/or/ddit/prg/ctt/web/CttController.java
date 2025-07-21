package kr.or.ddit.prg.ctt.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prg/ctt")
public class CttController {
	
	@GetMapping("/cttList.do")
	public String selectStdGroupList() {
		return "prg/ctt/cttList";
	}
	
}

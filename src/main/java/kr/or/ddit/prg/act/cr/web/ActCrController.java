package kr.or.ddit.prg.act.cr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prg/act/cr")
public class ActCrController {
	
	@GetMapping("/crList.do")
	public String selectCrList() {
		return "prg/act/cr/crList";
	}
}

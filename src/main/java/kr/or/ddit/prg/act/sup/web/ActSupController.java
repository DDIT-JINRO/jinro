package kr.or.ddit.prg.act.sup.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prg/act/sup")
public class ActSupController {

	@GetMapping("/supList.do")
	public String selectSupList() {
		return "prg/act/sup/supList";
	}
}

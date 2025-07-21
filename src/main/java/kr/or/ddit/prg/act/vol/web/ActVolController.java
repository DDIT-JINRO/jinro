package kr.or.ddit.prg.act.vol.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prg/act/vol")
public class ActVolController {
	
	@GetMapping("/volList.do")
	public String selectVolList() {
		return "prg/act/vol/volList";
	}
}

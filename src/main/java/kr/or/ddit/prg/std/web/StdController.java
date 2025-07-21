package kr.or.ddit.prg.std.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prg/std")
public class StdController {
	
	@GetMapping("/stdGroupList.do")
	public String selectStdGroupList() {
		return "prg/std/stdGroupList";
	}
	
}

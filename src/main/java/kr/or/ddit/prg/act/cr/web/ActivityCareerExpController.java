package kr.or.ddit.prg.act.cr.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prg/act/cr")
public class ActivityCareerExpController {
	
	@GetMapping("/crList.do")
	public String selectCrList() {
		return "prg/act/cr/crList";
	}
	
	@GetMapping("/crDetail.do")
	public String selectCrDetail(@RequestParam int crId, Model model) {
		model.addAttribute("crId", crId);
		
		return "prg/act/cr/crDetail";
	}
}

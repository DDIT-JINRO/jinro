package kr.or.ddit.prg.act.sup.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.prg.act.sup.service.ActivitySupportersService;

@Controller
@RequestMapping("/prg/act/sup")
public class ActivitySupportersController {
	
	@Autowired
	ActivitySupportersService activitySupportersService;

	@GetMapping("/supList.do")
	public String selectSupList() {
		return "prg/act/sup/supList";
	}
	
	@GetMapping("supDetail.do")
	public String selectSupDetail(@RequestParam int supId, Model model) {
		model.addAttribute("supId", supId);
		
		return "prg/act/sup/supDetail";
	}
}

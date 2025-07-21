package kr.or.ddit.prg.ctt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.prg.ctt.service.ContestService;

@Controller
@RequestMapping("/prg/ctt")
public class ContestController {
	
	@Autowired
	ContestService contestService;
	
	@GetMapping("/cttList.do")
	public String selectCttList() {
		return "prg/ctt/cttList";
	}
	
	@GetMapping("/cttDetail.do")
	public String selectCttDetail(@RequestParam int cttId, Model model) {
		model.addAttribute("cttId", cttId);
		
		return "prg/ctt/cttDetail";
	}
	
}

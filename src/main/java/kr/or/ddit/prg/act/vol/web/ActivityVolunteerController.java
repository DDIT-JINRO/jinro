package kr.or.ddit.prg.act.vol.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.prg.act.vol.service.ActivityVolunteerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/prg/act/vol")
public class ActivityVolunteerController {
	
	@Autowired
	ActivityVolunteerService activityVolunteerService;
	
	@GetMapping("/volList.do")
	public String selectVolList() {
		return "prg/act/vol/volList";
	}
	
	@GetMapping("/volDetail.do")
	public String selectVolDetail(@RequestParam int volId, Model model) {
		log.info("selectVolDetail -> volId : " + volId);
		model.addAttribute("volId", volId);
		
		return "prg/act/vol/volDetail";
	}
	
}

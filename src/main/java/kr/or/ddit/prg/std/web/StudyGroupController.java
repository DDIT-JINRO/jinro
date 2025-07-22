package kr.or.ddit.prg.std.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.prg.std.service.StudyGroupService;

@Controller
@RequestMapping("/prg/std")
public class StudyGroupController {
	
	@Autowired
	StudyGroupService studyGroupService;
	
	@GetMapping("/stdGroupList.do")
	public String selectStdGroupList() {
		return "prg/std/stdGroupList";
	}
	
	@GetMapping("/stdGroupDetail.do")
	public String selectStdGroupDetail(@RequestParam int stdGroupId, Model model) {
		model.addAttribute("stdGroupId", stdGroupId);
		
		return "prg/std/stdGroupDetail";
	}
	
	@GetMapping("/stdChatTest.do")
	public String stdChatTest() {
		return "prg/std/stdChatTest";
	}
	
}

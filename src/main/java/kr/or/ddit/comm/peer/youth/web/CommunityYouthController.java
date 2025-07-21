package kr.or.ddit.comm.peer.youth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comm/peer/youth")
public class CommunityYouthController {
	
	@GetMapping("/youthList.do")
	public String selectYouthList() {
		return "comm/peer/youth/youthList";
	}
	
	@GetMapping("/youthDetail.do")
	public String selectYouthDetail(@RequestParam int boardId, Model model) {
		model.addAttribute("boardId", boardId);
		return "comm/peer/youth/youthDetail";
	}
	
	@PostMapping("/youthInsert.do")
	public String insertYouth() {
		return "comm/peer/youth/youthInsert";
	}
	
	// 미연결 상태입니다
	@PostMapping("/youthModify.do")
	public String modifyYouth() {
		return "";
	}
}

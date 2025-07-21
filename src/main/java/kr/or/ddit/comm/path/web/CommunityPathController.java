package kr.or.ddit.comm.path.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comm/path")
public class CommunityPathController {
	
	@GetMapping("/pathList.do")
	public String selectPathList() {
		return "comm/path/pathList";
	}
	
	@GetMapping("/pathDetail.do")
	public String selectPathDetail(@RequestParam int boardId, Model model) {
		model.addAttribute("boardId", boardId);
		return "comm/path/pathDetail";
	}
	
	@GetMapping("/pathInsert.do")
	public String insertPath() {
		return "comm/path/pathInsert";
	}
	
	// 미연결 상태입니다.
	@PostMapping("/pathModify.do")
	public String modifyPath() {
		return "";
	}
	
}

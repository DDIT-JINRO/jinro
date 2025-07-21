package kr.or.ddit.comm.peer.teen.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 2025/07/21
 * 최초 생성 김석원 입니다.
 * 커뮤니티 또래게시판 파트 작성하시는 분께 주의점 남깁니다
 * 청소년 청년 분리되어있는데 화면에서 또래 게시판 클릭시 청소년으로 이동하게 되어있습니다
 * 회원정보 혹은 권한체크해서 청년이면 청년 게시판으로 이동되도록 구현해야합니다.
 */
@Controller
@RequestMapping("/comm/peer/teen")
public class CommunityTeenController {
	
	@GetMapping("/teenList.do")
	public String selectTeenList() {
		return "comm/peer/teen/teenList";
	}
	
	@GetMapping("/teenDetail.do")
	public String selectTeenDetail(@RequestParam int boardId, Model model) {
		model.addAttribute("boardId", boardId);
		return "comm/peer/teen/teenDetail";
	}
	
	@GetMapping("/teenInsert.do")
	public String insertTeen() {
		return "comm/peer/teen/teenInsert";
	}
	
	// 미연결 상태 입니다
	@PostMapping("/teenModify.do")
	public String modifyTeen() {
		return "";
	}
}

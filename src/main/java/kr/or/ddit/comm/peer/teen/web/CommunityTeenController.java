package kr.or.ddit.comm.peer.teen.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.account.lgn.service.LoginService;
import kr.or.ddit.comm.peer.teen.service.TeenCommService;
import kr.or.ddit.comm.vo.CommBoardVO;
import kr.or.ddit.comm.vo.CommReplyVO;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.mpg.mif.inq.service.MyInquiryService;
import lombok.extern.slf4j.Slf4j;

/**
 * 2025/07/21 최초 생성 김석원 입니다. 커뮤니티 또래게시판 파트 작성하시는 분께 주의점 남깁니다 청소년 청년 분리되어있는데 화면에서
 * 또래 게시판 클릭시 청소년으로 이동하게 되어있습니다 회원정보 혹은 권한체크해서 청년이면 청년 게시판으로 이동되도록 구현해야합니다.
 */
@Controller
@Slf4j
@RequestMapping("/comm/peer/teen")
public class CommunityTeenController {

	@Autowired
	TeenCommService teenCommService;
	@Autowired
	MyInquiryService myInquiryService;

	@GetMapping("/teenList.do")
	public String selectTeenList(Model model) {

		List<CommBoardVO> teenList = teenCommService.selectTeenList();

		model.addAttribute("teenList", teenList);

		return "comm/peer/teen/teenList";
	}

	@GetMapping("/teenDetail.do")
	public String selectTeenDetail(@RequestParam int boardId, @AuthenticationPrincipal String memId,  Model model) {
		
		if(memId == null || memId == "" || memId == "anonymousUser") {
			return "redirect:/error/logReq";
		}
		
		
		CommBoardVO boardVO = teenCommService.selectTeenDetail(boardId, memId);
		
		List<CommReplyVO> replyVO = teenCommService.selectBoardReply(boardId, memId); 
	
		MemberVO memVO = (MemberVO) myInquiryService.selectMyInquiryView(boardVO.getMemId()+"").get("member");
		memVO = myInquiryService.getProfileFile(memVO);
		
		model.addAttribute("memId", memId);
		model.addAttribute("memVO", memVO);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("replyVO", replyVO);
		
		
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

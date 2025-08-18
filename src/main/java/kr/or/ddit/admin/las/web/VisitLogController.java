package kr.or.ddit.admin.las.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.las.service.VisitLogService;

@RestController
@RequestMapping("/admin")
public class VisitLogController {

	@Autowired
	VisitLogService visitLogService;

	@PostMapping("/las/roadMapVisitLog.do")
	public void roadMapVisitLog(@AuthenticationPrincipal String memId) {
		visitLogService.insertPageLog(memId, "로드맵", "/roadmap", null);
	}

	@PostMapping("/las/worldCupVisitLog.do")
	public void worldCupVisitLog(@AuthenticationPrincipal String memId) {
		visitLogService.insertPageLog(memId, "월드컵", "/worldcup", null);
	}

	@PostMapping("/las/chatVisitLog.do")
	public void chatVisitLog(@AuthenticationPrincipal String memId) {
		visitLogService.insertPageLog(memId, "채팅", "/chat", null);
	}

	@PostMapping("/las/aiResumeVisitLog.do")
	public void aiResumeVisitLog(@AuthenticationPrincipal String memId) {
		visitLogService.insertPageLog(memId, "이력서AI요청", "/aiResume", null);
	}

	@PostMapping("/las/aiSelfIntroVisitLog.do")
	public void aiSelfIntroVisitLog(@AuthenticationPrincipal String memId) {
		visitLogService.insertPageLog(memId, "자기소개서AI요청", "/aiSelfIntro", null);
	}
}

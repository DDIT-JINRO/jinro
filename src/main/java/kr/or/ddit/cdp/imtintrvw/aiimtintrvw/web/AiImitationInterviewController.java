package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.AiImitationInterviewService;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewDetailListVO;
import kr.or.ddit.cdp.imtintrvw.bsintrvw.web.BassInterviewController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/imtintrvw/aiimtintrvw")
@Controller
@Slf4j
public class AiImitationInterviewController {

	@Autowired
	AiImitationInterviewService aiImitationInterviewService;

	@GetMapping("/aiImitationInterview.do")
	public String aiImitationInterviewPage() {
		return "cdp/imtintrvw/aiimtintrvw/aiImitationInterview";
	}
	
	@GetMapping("/getQuestionLists")
	@ResponseBody
	public List<InterviewDetailListVO> getQuestionList() {
		List<InterviewDetailListVO> list = aiImitationInterviewService.getQuestionList();
		log.info("list : " + list);
		
		return aiImitationInterviewService.getQuestionList();
	}
	
	@GetMapping("/detail.do")
	public String aiImitationInterviewDetailPage() {
		return "cdp/imtintrvw/aiimtintrvw/aiImitationInterviewDetail";
	}
}

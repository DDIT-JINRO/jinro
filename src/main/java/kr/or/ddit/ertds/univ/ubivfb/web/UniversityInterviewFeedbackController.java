package kr.or.ddit.ertds.univ.ubivfb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.empt.enp.service.InterviewReviewVO;
import kr.or.ddit.empt.ivfb.service.InterviewFeedbackService;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/ertds")
public class UniversityInterviewFeedbackController {

	@Autowired
	private InterviewFeedbackService interviewFeedbackService;

	// 중분류 학과정보로 이동
	@GetMapping("/univ/uvivfb/selectInterviewList.do")
	public String selectInterviewList(InterviewReviewVO interviewReviewVO, Model model) {
		try {
			interviewReviewVO.setIrType("G02001");
			ArticlePage<InterviewReviewVO> articlePage = interviewFeedbackService.selectInterviewFeedbackList(interviewReviewVO);
			articlePage.setUrl("/ertds/univ/uvivfb/selectInterviewList.do");
			model.addAttribute("articlePage", articlePage);
		} catch (CustomException e) {
			log.error("면접 후기 리스트 조회 중 에러 발생 : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
		} catch (Exception e) {
			log.error("면접 후기 리스트 조회 중 에러 발생 : {}", e.getMessage());
			model.addAttribute("errorMessage", "면접 후기 리스트 조회 중 에러가 발생했습니다.");
		}
		
		return "ertds/univ/uvivfb/selectInterviewList";
	}
}
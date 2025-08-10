package kr.or.ddit.empt.ivfb.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.empt.enp.service.InterviewReviewVO;
import kr.or.ddit.util.ArticlePage;

public interface InterviewFeedbackService {

	ArticlePage<InterviewReviewVO> selectInterviewFeedbackList(InterviewReviewVO interviewReviewVO);

	List<CompanyVO> selectCompanyList(String cpName);

	void updateInterViewFeedback(String memId, InterviewReviewVO interviewReview, MultipartFile file, String veriCategory);

}

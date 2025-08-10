package kr.or.ddit.empt.ivfb.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.empt.enp.service.InterviewReviewVO;

public interface InterviewFeedbackService {

	List<CompanyVO> selectCompanyList(String cpName);

	void updateInterViewFeedback(String memId, InterviewReviewVO interviewReview, MultipartFile file, String veriCategory);

}

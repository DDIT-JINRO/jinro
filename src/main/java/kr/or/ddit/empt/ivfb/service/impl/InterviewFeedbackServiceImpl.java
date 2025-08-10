package kr.or.ddit.empt.ivfb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.empt.enp.service.InterviewReviewVO;
import kr.or.ddit.empt.ivfb.service.InterviewFeedbackService;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.exception.ErrorCode;

@Service
public class InterviewFeedbackServiceImpl implements InterviewFeedbackService{

	@Autowired
	InterviewFeedbackMapper interviewFeedbackMapper;
	
	@Override
	public List<CompanyVO> selectCompanyList(String cpName) {
		
		List<CompanyVO> companyList = interviewFeedbackMapper.selectCompanyList(cpName);

		if(companyList == null || companyList.isEmpty()) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		return companyList;
	}

	@Override
	@Transactional
	public void updateInterViewFeedback(String memIdStr, InterviewReviewVO interviewReview, MultipartFile file, String veriCategory) {
		if (null == memIdStr || "anonymousUser".equals(memIdStr)) {
			throw new CustomException(ErrorCode.INVALID_AUTHORIZE);
		}
		int memId = Integer.parseInt(memIdStr);
		interviewReview.setMemId(memId);
		try {
			interviewFeedbackMapper.updateInterViewFeedback(interviewReview);
		} catch (Exception e) {
			throw e;
		}
}

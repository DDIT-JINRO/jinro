package kr.or.ddit.empt.ivfb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.empt.enp.service.CompanyVO;
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

}

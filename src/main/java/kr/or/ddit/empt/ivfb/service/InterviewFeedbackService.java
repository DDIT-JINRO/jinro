package kr.or.ddit.empt.ivfb.service;

import java.util.List;

import kr.or.ddit.empt.enp.service.CompanyVO;

public interface InterviewFeedbackService {

	List<CompanyVO> selectCompanyList(String cpName);

}

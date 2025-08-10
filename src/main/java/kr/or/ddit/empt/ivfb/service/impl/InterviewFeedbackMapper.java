package kr.or.ddit.empt.ivfb.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.empt.enp.service.InterviewReviewVO;

@Mapper
public interface InterviewFeedbackMapper {

	List<CompanyVO> selectCompanyList(String cpName);

	void updateInterViewFeedback(InterviewReviewVO interviewReview);
}

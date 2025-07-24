package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.AiImitationInterviewService;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewDetailListVO;

@Service
public class AiImitationInterviewServiceImpl implements AiImitationInterviewService {

	@Autowired
	InterviewDetailListMapper interviewDetailListMapper; 
	
	@Override
	public List<InterviewDetailListVO> getQuestionList() {
		return interviewDetailListMapper.getQuestionList();
	}

}

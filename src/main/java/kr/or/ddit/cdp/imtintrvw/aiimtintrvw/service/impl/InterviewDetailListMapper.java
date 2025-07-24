package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewDetailListVO;

@Mapper
public interface InterviewDetailListMapper {

	List<InterviewDetailListVO> getQuestionList();
	
}

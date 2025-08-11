package kr.or.ddit.cdp.imtintrvw.intrvwqestnmn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.cdp.imtintrvw.intrvwqestnmn.service.InterviewQuestionMangementService;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.exception.ErrorCode;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewDetailListVO;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewDetailVO;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewQuestionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewQuestionMangementServiceImpl implements InterviewQuestionMangementService {
	
	@Autowired
	private InterviewQuestionMangementMapper interviewQuestionMangementMapper;
	
	// 사용자 면접질문 갯수
	@Override
	public int selectInterviewQuestionTotalBymemId(InterviewDetailListVO interviewDetailListVO) {
		// TODO Auto-generated method stub
		return interviewQuestionMangementMapper.selectInterviewQuestionTotalBymemId(interviewDetailListVO);
	}

	// 사용자 면접질문 리스트
	@Override
	public List<InterviewDetailListVO> selectInterviewQuestionBymemId(InterviewDetailListVO interviewDetailListVO) {
		// TODO Auto-generated method stub
		return interviewQuestionMangementMapper.selectInterviewQuestionBymemId(interviewDetailListVO);
	}

	// 면접이 존재하지 않을 경우 에러 반환
	// 면접질문 정보 가져오기
	@Override
	public InterviewDetailListVO selectByInterviewQuestionId(InterviewDetailListVO interviewDetailListVO) {
		interviewDetailListVO = interviewQuestionMangementMapper.selectBySelfIntroId(interviewDetailListVO);
		
		if(interviewDetailListVO == null) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}
		return interviewQuestionMangementMapper.selectBySelfIntroId(interviewDetailListVO);
	}
	// 본인 면접질문인지 확인
	@Override
	public void cheakInterviewQuestionbyMemId(InterviewDetailListVO interviewDetailListVO, String memId) {
		int idlMemId = interviewDetailListVO.getMemId();
		int loginMemId = Integer.valueOf(memId);
		
		// 2) 면접질문이 없거나 작성자 정보가 없거나, 작성자가 아니면 모두 403
				if (!(idlMemId == loginMemId)) {
					throw new CustomException(ErrorCode.ACCESS_DENIED);
				}
	}

	// 항목별 자소서내용가져오기
	@Override
	public List<InterviewDetailVO> selectByInterviewQuestionContentIdList(InterviewDetailListVO interviewDetailListVO) {
		// TODO Auto-generated method stub
		return interviewQuestionMangementMapper.selectByInterviewQuestionContentIdList(interviewDetailListVO);
	}


	// 공통질문 가져오기
	@Override
	public List<InterviewQuestionVO> selectCommonQuestions() {
		// TODO Auto-generated method stub
		return interviewQuestionMangementMapper.selectCommonQuestions();
	}

	//질문 가져오기
	@Override
	public InterviewQuestionVO selectByInterviewQuestionQId(InterviewDetailVO interviewDetailVO) {
		// TODO Auto-generated method stub
		return interviewQuestionMangementMapper.selectByInterviewQuestionQId(interviewDetailVO);
	}

	//신규 면접 정보 등록
	@Override
	public int insertInterviewQuestionId(InterviewDetailListVO interviewDetailListVO) {
		int idlId = interviewQuestionMangementMapper.selectMaxInterviewQuestionId();
		interviewDetailListVO.setIdlId(idlId);
		interviewQuestionMangementMapper.insertInterviewQuestion(interviewDetailListVO);
		
		return idlId;
	}

}

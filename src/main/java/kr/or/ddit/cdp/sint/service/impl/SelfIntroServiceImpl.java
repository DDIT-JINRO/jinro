package kr.or.ddit.cdp.sint.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.cdp.sint.service.SelfIntroContentVO;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SelfIntroServiceImpl implements SelfIntroService {

	private final SelfIntroMapper selfIntroMapper;

	@Override
	public List<SelfIntroQVO> selectSelfIntroQList(SelfIntroQVO selfIntroQVO) {
		// TODO Auto-generated method stub
		return selfIntroMapper.selectSelfIntroQList(selfIntroQVO);
	}

	@Override
	public int selectSelfIntroQCount(SelfIntroQVO selfIntroQVO) {
		// TODO Auto-generated method stub
		return selfIntroMapper.selectSelfIntroQCount(selfIntroQVO);
	}

	@Override
	public List<ComCodeVO> selectSelfIntroComCodeList() {
		// TODO Auto-generated method stub
		return selfIntroMapper.selectSelfIntroComCodeList();
	}

	@Override
	@Transactional
	public int insertIntro(SelfIntroVO selfIntroVO, List<Long> questionIds) {

		int siId = selfIntroMapper.selectMaxIntroId();
		int cnt = 1;
		selfIntroVO.setSiId(siId);
		selfIntroVO.setSiStatus("작성중");
		selfIntroMapper.insertIntro(selfIntroVO);

		List<SelfIntroQVO> commonQList = selfIntroMapper.selectCommonQuestions();
		for (int i = 0; i < commonQList.size(); i++) {
			int sicId = selfIntroMapper.selectMaxSICId();
			SelfIntroContentVO selfIntroContentVO = new SelfIntroContentVO();
			selfIntroContentVO.setSicId(sicId);
			selfIntroContentVO.setSiId(siId);
			selfIntroContentVO.setSiqId(commonQList.get(i).getSiqId());
			selfIntroContentVO.setSicLimit(1500);
			selfIntroContentVO.setSicOrder(cnt);
			selfIntroMapper.insertContent(selfIntroContentVO);
			cnt++;
		}
		
		
		for (int i = 0; i < questionIds.size(); i++) {

			int sicId = selfIntroMapper.selectMaxSICId();
			SelfIntroContentVO selfIntroContentVO = new SelfIntroContentVO();
			selfIntroContentVO.setSicId(sicId);
			selfIntroContentVO.setSiId(siId);
			selfIntroContentVO.setSiqId(questionIds.get(i).intValue());
			selfIntroContentVO.setSicLimit(1500);
			selfIntroContentVO.setSicOrder(cnt);
			selfIntroMapper.insertContent(selfIntroContentVO);
			cnt++;
		}

		return siId;
	}

	@Override
	public List<SelfIntroContentVO> selectBySelfIntroContentIdList(SelfIntroVO selfIntroVO) {
		// TODO Auto-generated method stub
		return selfIntroMapper.selectBySelfIntroContentIdList(selfIntroVO);
	}

	@Override
	public SelfIntroQVO selectBySelfIntroQId(SelfIntroContentVO selfIntroContentVO) {
		// TODO Auto-generated method stub
		return selfIntroMapper.selectBySelfIntroQId(selfIntroContentVO);
	}

	@Override
	public SelfIntroVO selectBySelfIntroId(SelfIntroVO selfIntroVO) {
		// TODO Auto-generated method stub
		selfIntroVO = selfIntroMapper.selectBySelfIntroId(selfIntroVO);
		if (selfIntroVO == null) {
			throw new CustomException(ErrorCode.INVALID_INPUT,"/sint/sintwrt");
		}
		return selfIntroMapper.selectBySelfIntroId(selfIntroVO);
	}

	@Override
	public List<SelfIntroQVO> selectQuestionsBySiId(int siId) {
		// TODO Auto-generated method stub
		return selfIntroMapper.selectQuestionsBySiId(siId);
	}

	@Override
	public List<SelfIntroQVO> selectCommonQuestions() {
		// TODO Auto-generated method stub
		return selfIntroMapper.selectCommonQuestions();
	}

	// 본인 자소서인지 확인
	@Override
	public void cheakselfIntrobyMemId(SelfIntroVO selfIntroVO, String memId) {
		int siId = selfIntroVO.getMemId();
		int loginMemId = Integer.valueOf(memId);

		// 2) 자소서가 없거나 작성자 정보가 없거나, 작성자가 아니면 모두 403
		if (! (siId ==loginMemId)) {
			throw new CustomException(ErrorCode.ACCESS_DENIED,"/sint/sintwrt");
		}
	}

	@Override
	public int insertIntroId(SelfIntroVO selfIntroVO) {
		int siId = selfIntroMapper.selectMaxIntroId();
		int cnt = 1;
		selfIntroVO.setSiId(siId);
		selfIntroMapper.insertIntro(selfIntroVO);
		return siId;
	}

	@Override
	public void insertContent(int newSiId, Long questionId, String answer, int i) {
		int sicId = selfIntroMapper.selectMaxSICId();
        SelfIntroContentVO vo = new SelfIntroContentVO();
        vo.setSicId(sicId);
        vo.setSiId(newSiId);
        vo.setSiqId(questionId.intValue());
        vo.setSicContent(answer);
        vo.setSicLimit(1500);
        vo.setSicOrder(i);
        selfIntroMapper.insertContent(vo);
		
	}

	@Override
	public void updateIntro(SelfIntroVO selfIntroVO) {
		selfIntroMapper.updateIntro(selfIntroVO);
		
	}

	@Override
	public void updateContent(int sicId, int siqId, String sicContent, int sicOrder) {
		selfIntroMapper.updateContent(sicId, siqId, sicContent, sicOrder);
		
	}

}

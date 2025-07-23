package kr.or.ddit.cdp.sint.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.cdp.sint.service.SelfIntroContentVO;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.com.ComCodeVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
	public int insertIntro(SelfIntroVO selfIntroVO,List<Long> questionIds) {
		
		int siId = selfIntroMapper.selectMaxIntroId();
		
		selfIntroVO.setSiId(siId);
		selfIntroVO.setSiStatus("작성중");
		selfIntroMapper.insertIntro(selfIntroVO);
		
		for(int i=0; i<questionIds.size(); i++) {
			
			int sicId = selfIntroMapper.selectMaxSICId();
			SelfIntroContentVO selfIntroContentVO = new SelfIntroContentVO();
			selfIntroContentVO.setSicId(sicId);
			selfIntroContentVO.setSiId(siId);
			selfIntroContentVO.setSiqId(questionIds.get(i).intValue());
			selfIntroContentVO.setSicLimit(1500);
			selfIntroContentVO.setSicOrder(i+1);
			selfIntroMapper.insertContent(selfIntroContentVO);
		}
		
		
		return siId;
	}


	@Override
	public SelfIntroContentVO selectBySelfIntroContentId(SelfIntroVO selfIntroVO) {
		// TODO Auto-generated method stub
		return null;
	}

}

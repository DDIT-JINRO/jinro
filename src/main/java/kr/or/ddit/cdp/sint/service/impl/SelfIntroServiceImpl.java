package kr.or.ddit.cdp.sint.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
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

}

package kr.or.ddit.cdp.sint.service;

import java.util.List;

import kr.or.ddit.com.ComCodeVO;

public interface SelfIntroService {
	// 질문리스트 검색 결과, 페이징
	public List<SelfIntroQVO> selectSelfIntroQList(SelfIntroQVO selfIntroQVO);

	// 검색 결과 질문 갯수
	public int selectSelfIntroQCount(SelfIntroQVO selfIntroQVO);

	public List<ComCodeVO> selectSelfIntroComCodeList();
	
	public int insertIntro(SelfIntroVO selfIntroVO,List<Long> questionIds);
	
	public SelfIntroContentVO selectBySelfIntroContentId(SelfIntroVO selfIntroVO);
}

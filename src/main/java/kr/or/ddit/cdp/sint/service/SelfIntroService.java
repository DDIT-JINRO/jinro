package kr.or.ddit.cdp.sint.service;

import java.util.List;

public interface SelfIntroService {
	// 질문리스트 검색 결과, 페이징
	List<SelfIntroQVO> selectSelfIntroQList(SelfIntroQVO selfIntroQVO);

	// 검색 결과 질문 갯수
	int selectSelfIntroQCount(SelfIntroQVO selfIntroQVO);
}

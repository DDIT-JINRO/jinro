package kr.or.ddit.cdp.sint.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.cdp.sint.service.SelfIntroQVO;

@Mapper
public interface SelfIntroMapper {
	
	// 질문리스트 검색 결과, 페이징
	List<SelfIntroQVO> selectSelfIntroQList(SelfIntroQVO selfIntroQVO);

	// 검색 결과 질문 갯수
	int selectSelfIntroQCount(SelfIntroQVO selfIntroQVO);
}

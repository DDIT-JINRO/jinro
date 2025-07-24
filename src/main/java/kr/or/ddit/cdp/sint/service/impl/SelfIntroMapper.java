package kr.or.ddit.cdp.sint.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.cdp.sint.service.SelfIntroContentVO;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.com.ComCodeVO;

@Mapper
public interface SelfIntroMapper {

	// 질문리스트 검색 결과, 페이징
	public List<SelfIntroQVO> selectSelfIntroQList(SelfIntroQVO selfIntroQVO);

	// 검색 결과 질문 갯수
	public int selectSelfIntroQCount(SelfIntroQVO selfIntroQVO);

	public List<ComCodeVO> selectSelfIntroComCodeList();
	
	public int selectMaxIntroId();
	
	public int insertIntro(SelfIntroVO selfIntroVO);
	
	public int selectMaxSICId();
	
	public int insertContent(SelfIntroContentVO SelfIntroContentVO);
	
	public List<SelfIntroContentVO> selectBySelfIntroContentIdList(SelfIntroVO selfIntroVO);
	
	public SelfIntroQVO selectBySelfIntroQId(SelfIntroContentVO selfIntroContentVO);
	
	public SelfIntroVO selectBySelfIntroId(SelfIntroVO selfIntroVO);
	
	//자기소개서 번호로 선택한 자기소개서 질문 가져오기
	public List<SelfIntroQVO> selectQuestionsBySiId(int siId);
	
	//공통질문 가져오기
	public List<SelfIntroQVO> selectCommonQuestions();

	public void updateIntro(SelfIntroVO selfIntroVO);

	public void updateContent( @Param("sicId") int sicId, @Param("siqId") int siqId,@Param("sicContent") String sicContent,@Param("sicOrder")  int sicOrder);
}

package kr.or.ddit.cdp.sint.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.com.ComCodeVO;

public interface SelfIntroService {
	// 질문리스트 검색 결과, 페이징
	public List<SelfIntroQVO> selectSelfIntroQList(SelfIntroQVO selfIntroQVO);

	// 검색 결과 질문 갯수
	public int selectSelfIntroQCount(SelfIntroQVO selfIntroQVO);

	public List<ComCodeVO> selectSelfIntroComCodeList();
	
	public int insertIntroToQList(SelfIntroVO selfIntroVO,List<Long> questionIds);
	
	public List<SelfIntroContentVO> selectBySelfIntroContentIdList(SelfIntroVO selfIntroVO);
	
	public SelfIntroQVO selectBySelfIntroQId(SelfIntroContentVO selfIntroContentVO);
	
	public SelfIntroVO selectBySelfIntroId(SelfIntroVO selfIntroVO);
	
	//자기소개서 번호로 선택한 자기소개서 질문 가져오기
	public List<SelfIntroQVO> selectQuestionsBySiId(int siId);
	
	//공통질문 가져오기
	public List<SelfIntroQVO> selectCommonQuestions();

	public void cheakselfIntrobyMemId(SelfIntroVO selfIntroVO, String memId);
	//신규 자소서 정보등롯	
	public int insertIntroId(SelfIntroVO intro);

	// 이미 있을 때 수정 제목과 상태
	 public void updateIntro(SelfIntroVO selfIntroVO);

	 public void insertContent(int newSiId, List<Long> siqIdList, List<String> sicContentList);

	 public void updateContent(List<Long> siqIdList, Map<Integer, Integer> qToSicId, List<String> sicContentList);
}

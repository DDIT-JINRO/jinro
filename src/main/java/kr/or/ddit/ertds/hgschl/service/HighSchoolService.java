package kr.or.ddit.ertds.hgschl.service;

import java.util.List;

import kr.or.ddit.com.ComCodeVO;

public interface HighSchoolService {
	// 모든 고등학교 리스트
	public List<HighSchoolVO> highSchoolList(HighSchoolVO highSchoolVO);

	// 고등학교 상세
	public HighSchoolVO highSchoolDetail(int hsId);

	// 검색 결과 갯수
	public int selectHighSchoolCount(HighSchoolVO highSchoolVO);
	
	//지역 필터 옵션 목록 조회
    public List<ComCodeVO> selectRegionList();

    //학교 종류 필터 옵션 목록 조회
    public List<ComCodeVO> selectSchoolTypeList();

    //공학 여부 필터 옵션 목록 조회
    public List<ComCodeVO> selectCoedTypeList();
    
    //특정 고등학교의 학과 목록 조회
    public List<HighSchoolDeptVO> selectDeptsBySchoolId(int hsId);
    
}

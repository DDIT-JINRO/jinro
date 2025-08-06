package kr.or.ddit.ertds.hgschl.service;

import java.util.List;

public interface HighSchoolService {
	// 모든 고등학교 리스트
	public List<HighSchoolVO> highSchoolList(HighSchoolVO highSchoolVO);

	// 고등학교 상세
	public HighSchoolVO highSchoolDetail(int hsId);

	// 검색 결과 갯수
	public int selectHighSchoolCount(HighSchoolVO highSchoolVO);
}

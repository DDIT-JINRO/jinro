package kr.or.ddit.ertds.hgschl.service;

import java.util.List;

public interface HighSchoolService {
	// 모든 고등학교 리스트
	public List<HighSchoolVO> getAllHighSchools();

	// 고등학교 상세
	public HighSchoolVO getHighSchoolById(int hsId);

	// 특정 이름으로 고등학교를 조회하는 메서드 추가
	public List<HighSchoolVO> getHighSchoolsByName(String schoolName);
}

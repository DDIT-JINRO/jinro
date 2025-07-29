package kr.or.ddit.highSchool.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.highSchool.service.HighSchoolVO;

@Mapper
public interface HighSchoolMapper {

	// 모든 고등학교 정보를 조회하는 메서드 (주소 포함)
	public List<HighSchoolVO> selectAllHighSchools();

	// 특정 고등학교 ID로 상세 정보를 조회
	public HighSchoolVO selectHighSchoolById(@Param("hsId") Long hsId); // hsId가 Long 타입으로 변경

	// 특정 고등학교 이름을 파라미터로 받아 HighSchoolVO 리스트를 반환
	public List<HighSchoolVO> selectHighSchoolsByName(@Param("schoolName") String schoolName);

}

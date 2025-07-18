package kr.or.ddit.highSchool.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.highSchool.service.HighSchoolVO;

@Mapper
public interface HighSchoolMapper {

	// 모든 고등학교 정보를 조회하는 메서드 (주소 포함)
	public List<HighSchoolVO> selectAllHighSchools();

}

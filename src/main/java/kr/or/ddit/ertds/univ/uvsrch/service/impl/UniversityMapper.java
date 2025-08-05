package kr.or.ddit.ertds.univ.uvsrch.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.ertds.univ.uvsrch.service.UniversityVO;

@Mapper
public interface UniversityMapper {
	
	List<UniversityVO> selectUniversityList();
	
	int selectUniversityTotalCount(UniversityVO universityVO);
}

package kr.or.ddit.ertds.hgschl.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.ertds.hgschl.service.HighSchoolVO;

@Mapper
public interface HighSchoolMapper {
	// 모든 고등학교 정보를 조회하는 메서드 (주소 포함)
	public List<HighSchoolVO> highSchoolList(HighSchoolVO highSchoolVO);

	// 특정 고등학교 상세 정보를 조회
	public HighSchoolVO highSchoolDetail(@Param("hsId") int hsId);

	// 전체 개수를 조회
	public int selectHighSchoolCount(HighSchoolVO highSchoolVO);

	//지역 필터 옵션 목록 조회
	public List<ComCodeVO> selectRegionList();

    //학교 유형 필터 옵션 목록 조회
	public List<ComCodeVO> selectSchoolTypeList();

    //공학 여부 필터 옵션 목록 조회
	public List<ComCodeVO> selectCoedTypeList();
}

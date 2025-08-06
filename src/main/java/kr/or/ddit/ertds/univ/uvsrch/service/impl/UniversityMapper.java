package kr.or.ddit.ertds.univ.uvsrch.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.ertds.univ.uvsrch.service.UniversityVO;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;

@Mapper
public interface UniversityMapper {
	
	List<UniversityVO> selectUniversityList(UniversityVO universityVO);
	
	int selectUniversityTotalCount(UniversityVO universityVO);
	
	List<BookMarkVO> selectBookMarkVO(BookMarkVO bookMarkVO);
}

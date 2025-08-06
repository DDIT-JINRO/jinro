package kr.or.ddit.ertds.univ.uvsrch.service;

import java.util.List;

import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;

public interface UniversityService {

	List<UniversityVO> selectUniversityList(UniversityVO universityVO);

	int selectUniversityTotalCount(UniversityVO universityVO);
	
	List<BookMarkVO> selectBookMarkVO(BookMarkVO bookmarkVO);
}

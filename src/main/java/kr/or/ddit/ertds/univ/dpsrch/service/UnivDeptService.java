package kr.or.ddit.ertds.univ.dpsrch.service;

import java.util.List;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;

public interface UnivDeptService {
	
	List<UnivDeptVO> selectUnivDeptList(UnivDeptVO univDeptVO);

	int selectUniversityTotalCount(UnivDeptVO univDeptVO);
	
	List<ComCodeVO> selectCodeVOUnivDeptLClassList();
	
	List<BookMarkVO> selectBookMarkVO(BookMarkVO bookmarkVO);
	
	UnivDeptDetailVO selectDeptDetail(int uddId);

}

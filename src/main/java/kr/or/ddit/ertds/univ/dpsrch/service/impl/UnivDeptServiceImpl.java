package kr.or.ddit.ertds.univ.dpsrch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.ertds.univ.dpsrch.service.UnivDeptDetailVO;
import kr.or.ddit.ertds.univ.dpsrch.service.UnivDeptService;
import kr.or.ddit.ertds.univ.dpsrch.service.UnivDeptVO;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UnivDeptServiceImpl implements UnivDeptService {
	
	@Autowired
	UnivDeptMapper univDeptMapper;

	@Override
	public List<UnivDeptVO> selectUnivDeptList(UnivDeptVO univDeptVO) {
		List<UnivDeptVO> list = this.univDeptMapper.selectUnivDeptList(univDeptVO);

		log.info("selectUniversityList : ", list);
		return list;
	}

	@Override
	public int selectUniversityTotalCount(UnivDeptVO univDeptVO) {
		return this.univDeptMapper.selectUniversityTotalCount(univDeptVO);
	}

	@Override
	public List<ComCodeVO> selectCodeVOUnivDeptLClassList() {
		return this.univDeptMapper.selectCodeVOUnivDeptLClassList();
	}

	@Override
	public List<BookMarkVO> selectBookMarkVO(BookMarkVO bookmarkVO) {
		return this.univDeptMapper.selectBookMarkVO(bookmarkVO);
	}

	@Override
	public UnivDeptDetailVO selectDeptDetail(int uddId) {
		return this.univDeptMapper.selectDeptDetail(uddId);
	}

	
}

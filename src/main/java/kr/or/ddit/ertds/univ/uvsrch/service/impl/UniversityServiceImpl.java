package kr.or.ddit.ertds.univ.uvsrch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.ertds.univ.uvsrch.service.UniversityService;
import kr.or.ddit.ertds.univ.uvsrch.service.UniversityVO;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UniversityServiceImpl implements UniversityService {

	@Autowired
	UniversityMapper universityMapper;

	@Override
	public List<UniversityVO> selectUniversityList(UniversityVO universityVO) {
		List<UniversityVO> list = this.universityMapper.selectUniversityList(universityVO);
		int deptCount = 0;
		for (UniversityVO univ : list) {
			deptCount = this.universityMapper.selectUnivDeptCount(univ.getUnivId());
			univ.setDeptCount(deptCount);
		}
		log.info("selectUniversityList : ", list);
		return list;
	}

	@Override
	public int selectUniversityTotalCount(UniversityVO universityVO) {
		return this.universityMapper.selectUniversityTotalCount(universityVO);
	}

	@Override
	public List<BookMarkVO> selectBookMarkVO(BookMarkVO bookmarkVO) {
		return this.universityMapper.selectBookMarkVO(bookmarkVO);
	}

	@Override
	public List<ComCodeVO> selectCodeVOUniversityTypeList() {
		return this.universityMapper.selectCodeVOUniversityTypeList();
	}

	@Override
	public List<ComCodeVO> selectCodeVOUniversityGubunList() {
		return this.universityMapper.selectCodeVOUniversityGubunList();
	}

	@Override
	public List<ComCodeVO> selectCodeVORegionList() {
		return this.universityMapper.selectCodeVORegionList();
	}
	
	
}

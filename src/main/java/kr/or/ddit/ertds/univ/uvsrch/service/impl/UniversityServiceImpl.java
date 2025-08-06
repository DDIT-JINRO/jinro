package kr.or.ddit.ertds.univ.uvsrch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
}

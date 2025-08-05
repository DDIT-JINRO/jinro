package kr.or.ddit.ertds.univ.uvsrch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.ertds.univ.uvsrch.service.UniversityService;
import kr.or.ddit.ertds.univ.uvsrch.service.UniversityVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UniversityServiceImpl implements UniversityService {

	@Autowired
	UniversityMapper universityMapper;

	@Override
	public List<UniversityVO> selectUniversityList() {
		List<UniversityVO> list = this.universityMapper.selectUniversityList();

		log.info("selectUniversityList : ", list);
		return list;
	}

	@Override
	public int selectUniversityTotalCount(UniversityVO universityVO) {
		return this.universityMapper.selectUniversityTotalCount(universityVO);
	}
	
	
}

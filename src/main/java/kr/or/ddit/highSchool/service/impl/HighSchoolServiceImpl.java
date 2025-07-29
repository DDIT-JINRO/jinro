package kr.or.ddit.highSchool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.highSchool.service.HighSchoolService;
import kr.or.ddit.highSchool.service.HighSchoolVO;

@Service
public class HighSchoolServiceImpl implements HighSchoolService {

	@Autowired
	private HighSchoolMapper highSchoolMapper;

	// 모든 고등학교 리스트
	@Override
	public List<HighSchoolVO> getAllHighSchools() {

		return highSchoolMapper.selectAllHighSchools();
	}

	// 고등학교 상세
	@Override
	public HighSchoolVO getHighSchoolById(int hsId) {
		HighSchoolVO highSchool = highSchoolMapper.selectHighSchoolById(hsId);

		return highSchoolMapper.selectHighSchoolById(hsId);
	}

	// 특정 이름의 고등학교 조회
	@Override
	public List<HighSchoolVO> getHighSchoolsByName(String schoolName) {

		return highSchoolMapper.selectHighSchoolsByName(schoolName);
	}
}

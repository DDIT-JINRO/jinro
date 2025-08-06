package kr.or.ddit.ertds.hgschl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.ertds.hgschl.service.HighSchoolService;
import kr.or.ddit.ertds.hgschl.service.HighSchoolVO;

@Service
public class HighSchoolServiceImpl implements HighSchoolService {

	@Autowired
	private HighSchoolMapper highSchoolMapper;

	// 모든 고등학교 리스트
	@Override
	public List<HighSchoolVO> highSchoolList(HighSchoolVO highSchoolVO) {

		return highSchoolMapper.highSchoolList(highSchoolVO);
	}

	// 고등학교 상세
	@Override
	public HighSchoolVO highSchoolDetail(int hsId) {

		return highSchoolMapper.highSchoolDetail(hsId);
	}

	// 검색 결과 갯수
	@Override
	public int selectHighSchoolCount(HighSchoolVO highSchoolVO) {

		return highSchoolMapper.selectHighSchoolCount(highSchoolVO);
	}

}

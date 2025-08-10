package kr.or.ddit.ertds.hgschl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.ertds.hgschl.service.HighSchoolDeptVO;
import kr.or.ddit.ertds.hgschl.service.HighSchoolService;
import kr.or.ddit.ertds.hgschl.service.HighSchoolVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

	// 지역 필터 옵션 목록 조회
	@Override
	public List<ComCodeVO> selectRegionList() {

		return highSchoolMapper.selectRegionList();
	}

	// 학교 유형 필터 옵션 목록 조회
	@Override
	public List<ComCodeVO> selectSchoolTypeList() {

		return highSchoolMapper.selectSchoolTypeList();
	}

	// 공학 여부 필터 옵션 목록 조회
	@Override
	public List<ComCodeVO> selectCoedTypeList() {

		return highSchoolMapper.selectCoedTypeList();
	}

	// 특정 고등학교의 학과 목록 조회
	@Override
	public List<HighSchoolDeptVO> selectDeptsBySchoolId(int hsId) {

		return highSchoolMapper.selectDeptsBySchoolId(hsId);
	}

	//고등학교 삭제
	@Override
	public int highSchoolDelete(int hsId) {

		return highSchoolMapper.highSchoolDelete(hsId);
	}

	//고등학교 학과 삭제
	@Override
	public int highSchoolDeptDelete(int hsdId) {

		return highSchoolMapper.highSchoolDeptDelete(hsdId);
	}

	// 고등학교 정보 입력
	@Override
	public int highSchoolInsert(HighSchoolVO highSchoolVO) {

		return highSchoolMapper.highSchoolInsert(highSchoolVO);
	}

	// 고등학교 학과 입력
	@Override
	public int highSchoolDeptInsert(HighSchoolDeptVO highSchoolDeptVO) {

		return highSchoolMapper.highSchoolDeptInsert(highSchoolDeptVO);
	}

	// 고등학교 정보 수정
	@Override
	public int highSchoolUpdate(HighSchoolVO highSchoolVO) {
	
		return highSchoolMapper.highSchoolUpdate(highSchoolVO);
	}

	// 고등학교 학과 정보 수정
	@Override
	public int highSchoolDeptUpdate(HighSchoolDeptVO highSchoolDeptVO) {

		return highSchoolMapper.highSchoolDeptUpdate(highSchoolDeptVO);
	}

}

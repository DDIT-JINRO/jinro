package kr.or.ddit.ertds.univ.uvsrch.service;

import java.util.List;

public interface UniversityService {

	List<UniversityVO> selectUniversityList();

	int selectUniversityTotalCount(UniversityVO universityVO);
}

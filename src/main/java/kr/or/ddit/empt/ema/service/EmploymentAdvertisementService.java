package kr.or.ddit.empt.ema.service;

import java.util.List;

import kr.or.ddit.com.ComCodeVO;

public interface EmploymentAdvertisementService {

	int selectFilteredHireTotalCount(HireVO hireVO);

	List<HireVO> selectFilteredHireList(HireVO hireVO);

	List<ComCodeVO> selectCodeVOList(ComCodeVO comCodeVO);

}

package kr.or.ddit.empt.ema.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.empt.ema.service.HireVO;

@Mapper
public interface EmploymentAdvertisementMapper {

	int selectFilteredHireTotalCount(HireVO hireVO);

	List<HireVO> selectFilteredHireList(HireVO hireVO);

	List<ComCodeVO> selectCodeVOList(ComCodeVO comCodeVO);

}

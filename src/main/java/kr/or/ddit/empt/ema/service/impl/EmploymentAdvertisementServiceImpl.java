package kr.or.ddit.empt.ema.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.empt.ema.service.EmploymentAdvertisementService;
import kr.or.ddit.empt.ema.service.HireVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmploymentAdvertisementServiceImpl implements EmploymentAdvertisementService {

	private final EmploymentAdvertisementMapper employmentAdvertisementMapper;

	@Override
	public int selectFilteredHireTotalCount(HireVO hireVO) {
		// TODO Auto-generated method stub
		return employmentAdvertisementMapper.selectFilteredHireTotalCount(hireVO);
	}

	@Override
	public List<HireVO> selectFilteredHireList(HireVO hireVO) {
		// TODO Auto-generated method stub
		return employmentAdvertisementMapper.selectFilteredHireList(hireVO);
	}

	@Override
	public List<ComCodeVO> selectCodeVOList(ComCodeVO comCodeVO) {
		// TODO Auto-generated method stub
		return employmentAdvertisementMapper.selectCodeVOList(comCodeVO);
	}
}

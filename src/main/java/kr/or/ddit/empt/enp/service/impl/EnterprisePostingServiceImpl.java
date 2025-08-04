package kr.or.ddit.empt.enp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.empt.enp.service.EnterprisePostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnterprisePostingServiceImpl implements EnterprisePostingService {

	private final EnterprisePostingMapper enterprisePostingMapper;

	@Override
	public int selectCompanyListCount(CompanyVO companyVO) {
		// TODO Auto-generated method stub
		return enterprisePostingMapper.selectCompanyListCount(companyVO);
	}

	@Override
	public List<CompanyVO> selectCompanyList(CompanyVO companyVO) {
		// TODO Auto-generated method stub
		return enterprisePostingMapper.selectCompanyList(companyVO);
	}

	@Override
	public List<ComCodeVO> selectCodeVOCompanyScaleList() {
		// TODO Auto-generated method stub
		return enterprisePostingMapper.selectCodeVOCompanyScaleList();
	}

	@Override
	public List<ComCodeVO> selectCodeVORegionList() {
		// TODO Auto-generated method stub
		return enterprisePostingMapper.selectCodeVORegionList();
	}

}

package kr.or.ddit.empt.enp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public List<CompanyVO> selectCompanyList() {
		// TODO Auto-generated method stub
		return enterprisePostingMapper.selectCompanyList();
	}

	@Override
	public void updateRegion(CompanyVO companyVO) {
		// TODO Auto-generated method stub
		enterprisePostingMapper.updateRegion(companyVO);
	}
}

package kr.or.ddit.empt.enp.service;

import java.util.List;

public interface EnterprisePostingService {

	List<CompanyVO> selectCompanyList();

	void updateRegion(CompanyVO companyVO);

}

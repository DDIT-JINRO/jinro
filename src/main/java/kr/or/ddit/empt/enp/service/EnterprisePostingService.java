package kr.or.ddit.empt.enp.service;

import java.util.List;

import kr.or.ddit.com.ComCodeVO;

public interface EnterprisePostingService {

	int selectCompanyListCount(CompanyVO companyVO);

	List<CompanyVO> selectCompanyList(CompanyVO companyVO);

	List<ComCodeVO> selectCodeVOCompanyScaleList();

	List<ComCodeVO> selectCodeVORegionList();

	int updateEnterprisePosting(CompanyVO companyVO);

	int checkCompanyByCpId(CompanyVO companyVO);

	int deleteEnterprisePosting(CompanyVO companyVO);


}

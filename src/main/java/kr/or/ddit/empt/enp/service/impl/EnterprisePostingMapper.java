package kr.or.ddit.empt.enp.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.empt.enp.service.CompanyVO;

@Mapper
public interface EnterprisePostingMapper {

	List<CompanyVO> selectCompanyList();

	void updateRegion(CompanyVO companyVO);

}

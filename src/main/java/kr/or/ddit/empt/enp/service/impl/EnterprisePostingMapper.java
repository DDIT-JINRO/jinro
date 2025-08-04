package kr.or.ddit.empt.enp.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.empt.enp.service.CompanyVO;

@Mapper
public interface EnterprisePostingMapper {

	int selectCompanyListCount(CompanyVO companyVO);

	List<CompanyVO> selectCompanyList(CompanyVO companyVO);

	List<ComCodeVO> selectCodeVOCompanyScaleList();

	List<ComCodeVO> selectCodeVORegionList();

}

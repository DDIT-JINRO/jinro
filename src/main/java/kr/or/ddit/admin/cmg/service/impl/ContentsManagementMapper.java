package kr.or.ddit.admin.cmg.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.empt.enp.service.CompanyVO;

@Mapper
public interface ContentsManagementMapper {

	List<CompanyVO> getEntList(CompanyVO companyVO);

	int getAllEntList(CompanyVO companyVO);

}

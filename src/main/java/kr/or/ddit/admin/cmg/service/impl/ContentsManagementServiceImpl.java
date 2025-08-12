package kr.or.ddit.admin.cmg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.cmg.service.ContentsManagementService;
import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContentsManagementServiceImpl implements ContentsManagementService {

	@Autowired
	ContentsManagementMapper contentsManagementMapper;

	@Override
	public ArticlePage<CompanyVO> getEntList(CompanyVO companyVO) {

		List<CompanyVO> companyList = contentsManagementMapper.getEntList(companyVO);

		int total = contentsManagementMapper.getAllEntList(companyVO);

		return new ArticlePage<CompanyVO>(total, companyVO.getCurrentPage(), companyVO.getSize(), companyList,
				companyVO.getKeyword());
	}

}

package kr.or.ddit.admin.cmg.service;

import java.util.Map;

import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.util.ArticlePage;

public interface ContentsManagementService {

	ArticlePage<CompanyVO> getEntList(CompanyVO companyVO);

	Map<String, Object> entDetail(String id);

}

package kr.or.ddit.mpg.mat.sih.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.mpg.mat.sih.service.SelfIntroHistoryService;
import kr.or.ddit.mpg.mif.inq.service.MyInquiryService;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SelfIntroHistoryServiceImpl implements SelfIntroHistoryService {

	@Autowired
	MyInquiryService myInquiryService;

	@Autowired
	SelfIntroService selfIntroService;

	@Override
	public ArticlePage<SelfIntroVO> selectSelfIntroHistoryList(String memIdStr, String keyword, String status, int currentPage, int size) {
		
		SelfIntroVO selfIntroVO = new SelfIntroVO();
		selfIntroVO.setKeyword(keyword);
		selfIntroVO.setStatus(status);
		selfIntroVO.setCurrentPage(currentPage);
		selfIntroVO.setSize(size);
		int memId = myInquiryService.parseMemId(memIdStr);

		selfIntroVO.setMemId(memId);
		
		List<SelfIntroVO> selfIntroVOList = this.selfIntroService.selectSelfIntroBymemId(selfIntroVO);
		
		ArticlePage<SelfIntroVO> articlePage = new ArticlePage<SelfIntroVO>(
				selfIntroVOList.size()
				, selfIntroVO.getCurrentPage()
				, selfIntroVO.getSize()
				, selfIntroVOList
				, selfIntroVO.getKeyword());

		articlePage.setUrl("/mpg/mat/sih/selectSelfIntroHistoryList.do");
		
		return articlePage;
	}
}

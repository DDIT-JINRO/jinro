package kr.or.ddit.mpg.mat.sih.service;

import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.util.ArticlePage;

public interface SelfIntroHistoryService {

	ArticlePage<SelfIntroVO> selectSelfIntroHistoryList(String memId, String keyword, String status, int currentPage, int size);

}

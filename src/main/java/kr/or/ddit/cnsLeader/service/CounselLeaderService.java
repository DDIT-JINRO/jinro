package kr.or.ddit.cnsLeader.service;

import kr.or.ddit.cns.service.CounselingLogVO;
import kr.or.ddit.cns.service.CounselingVO;
import kr.or.ddit.util.ArticlePage;

public interface CounselLeaderService {

	/**
	 * 상담일지 작성된 내역들 조회.
	 * @param counselingVO
	 * @return
	 */
	ArticlePage<CounselingVO> selectCounselLogList(CounselingVO counselingVO);

	/**
	 * 상담일지 승인, 반려 처리
	 * @param counselingLogVO
	 * @return
	 */
	boolean updateCounselLog(CounselingLogVO counselingLogVO);

}

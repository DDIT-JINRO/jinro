package kr.or.ddit.cnslt.rvw.service;

import kr.or.ddit.util.ArticlePage;

public interface CounselingReviewService {

	ArticlePage<CounselingReviewVO> selectCounselingReviewList(CounselingReviewVO counselingReview);

	CounselingReviewVO selectCounselingReview(String crId);

}

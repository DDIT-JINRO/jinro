package kr.or.ddit.cnslt.rvw.service;

import kr.or.ddit.util.ArticlePage;

public interface CounselingReviewService {

	ArticlePage<CounselingReviewVO> selectCounselingReview(CounselingReviewVO counselingReview);

}

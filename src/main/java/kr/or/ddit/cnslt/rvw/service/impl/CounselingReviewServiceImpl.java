package kr.or.ddit.cnslt.rvw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.cnslt.rvw.service.CounselingReviewService;
import kr.or.ddit.cnslt.rvw.service.CounselingReviewVO;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.exception.ErrorCode;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CounselingReviewServiceImpl implements CounselingReviewService {

	@Autowired
	private CounselingReviewMapper counselingReviewMapper;
	
	@Override
	public ArticlePage<CounselingReviewVO> selectCounselingReviewList(CounselingReviewVO counselingReview) {

		List<CounselingReviewVO> counselingReviewList = counselingReviewMapper.selectCounselingReviewList(counselingReview);
		
		if(counselingReviewList == null || counselingReviewList.isEmpty()) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		int counselingReviewListTotal = counselingReviewMapper.selectCounselingReviewTotal(counselingReview);
		
		ArticlePage<CounselingReviewVO> articlePage = new ArticlePage<>(counselingReviewListTotal, counselingReview.getCurrentPage(), counselingReview.getSize(), counselingReviewList, counselingReview.getKeyword());
		articlePage.setUrl("/cnslt/rvw/cnsReview.do");
		
		return articlePage;
	}

	@Override
	public CounselingReviewVO selectCounselingReview(String crId) {
		
		CounselingReviewVO counselingReview = counselingReviewMapper.selectCounselingReview(Integer.parseInt(crId));
		
		return counselingReview;
	}
	
}

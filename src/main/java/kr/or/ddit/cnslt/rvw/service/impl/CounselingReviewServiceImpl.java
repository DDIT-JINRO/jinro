package kr.or.ddit.cnslt.rvw.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.cnslt.resve.crsv.service.CounselingVO;
import kr.or.ddit.cnslt.rvw.service.CounselingReviewService;
import kr.or.ddit.cnslt.rvw.service.CounselingReviewVO;
import kr.or.ddit.empt.enp.service.InterviewReviewVO;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.exception.ErrorCode;
import kr.or.ddit.mpg.mif.inq.service.VerificationVO;
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

	@Override
	public List<CounselingVO> selectCounselingHistory(String memIdStr, String counselName) {
		
		CounselingVO counseling = new CounselingVO();
		counseling.setMemId(Integer.parseInt(memIdStr));
		counseling.setCounselName(counselName);
		
		List<CounselingVO> counselingList = counselingReviewMapper.selectCounselingHistory(counseling);
		
		return counselingList;
	}

	@Override
	public void updateCnsReview(CounselingReviewVO counselingReview) {
		counselingReviewMapper.updateCnsReview(counselingReview);
		counselingReviewMapper.updateCounselReviewd(counselingReview.getCrId(), "Y");
	}
	
}

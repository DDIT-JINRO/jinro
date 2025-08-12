package kr.or.ddit.cnslt.rvw.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.cnslt.resve.crsv.service.CounselingVO;
import kr.or.ddit.cnslt.rvw.service.CounselingReviewVO;

@Mapper
public interface CounselingReviewMapper {

	List<CounselingReviewVO> selectCounselingReviewList(CounselingReviewVO counselingReview);

	int selectCounselingReviewTotal(CounselingReviewVO counselingReview);

	CounselingReviewVO selectCounselingReview(int crId);

	List<CounselingVO> selectCounselingHistory(CounselingVO counseling);

}

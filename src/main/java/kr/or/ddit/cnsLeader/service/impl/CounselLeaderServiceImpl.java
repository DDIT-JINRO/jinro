package kr.or.ddit.cnsLeader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.cns.service.CounselingLogVO;
import kr.or.ddit.cns.service.CounselingVO;
import kr.or.ddit.cns.service.VacationVO;
import kr.or.ddit.cnsLeader.service.CounselLeaderService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.file.service.FileService;

@Service
public class CounselLeaderServiceImpl implements CounselLeaderService {

	@Autowired
	CounselLeaderMapper counselLeaderMapper;

	@Autowired
	FileService fileService;

	@Override
	public ArticlePage<CounselingVO> selectCounselLogList(CounselingVO counselingVO) {

		List<CounselingVO> list = counselLeaderMapper.selectCounselLogList(counselingVO);
		int total = counselLeaderMapper.selectTotalCounselLogList(counselingVO);

		ArticlePage<CounselingVO> articlePage = new ArticlePage<>(total, counselingVO.getCurrentPage(), counselingVO.getSize(), list, counselingVO.getKeyword());
		return articlePage;
	}

	@Override
	public boolean updateCounselLog(CounselingLogVO counselingLogVO) {
		int result = this.counselLeaderMapper.updateCounselLog(counselingLogVO);
		return result > 0 ? true : false;
	}

	@Override
	public ArticlePage<VacationVO> selectVacationList(VacationVO vacationVO) {
		List<VacationVO> list = this.counselLeaderMapper.selectVacationList(vacationVO);
		int total = this.counselLeaderMapper.selectTotalVationList(vacationVO);

		ArticlePage<VacationVO> articlePage = new ArticlePage<>(total, vacationVO.getCurrentPage(), vacationVO.getSize(), list, vacationVO.getKeyword());
		return articlePage;
	}

}

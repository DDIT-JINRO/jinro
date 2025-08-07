package kr.or.ddit.prg.ctt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.prg.ctt.service.ContestService;
import kr.or.ddit.prg.ctt.service.ContestVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContestServiceImpl implements ContestService {
	
	@Autowired
	ContestMapper contestMapper;
	
	//공모전 목록 조회
	@Override
	public List<ContestVO> selectCttList(ContestVO contestVO) {
		List<ContestVO> cttList = contestMapper.selectCttList(contestVO);
        log.info("selectCttList 결과 (조회된 공모전 목록): {}개", cttList.size());
		return cttList;
	}
	
	//공모전 총 개수 조회
	@Override
	public int selectCttCount(ContestVO contestVO) {
		int totalCount = contestMapper.selectCttCount(contestVO);
        log.info("selectCttCount 결과 (총 게시물 수): {}", totalCount);
		return totalCount;
	}
	
	// 공모전 상세
	@Override
	public ContestVO selectCttDetail(String cttId) {
        ContestVO detail = contestMapper.selectCttDetail(cttId);
        log.info("selectCttDetail 결과: {}", detail);
        return detail;
	}
}
package kr.or.ddit.prg.ctt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.prg.ctt.service.ContestService;
import kr.or.ddit.prg.ctt.service.ContestVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContestServiceImpl implements ContestService {
	
	@Autowired
	private ContestMapper contestMapper;
	
	//공모전 목록 조회
	@Override
	public List<ContestVO> selectCttList(ContestVO contestVO) {
		
		// 페이징 정보 계산 (startRow, endRow)
        int size = contestVO.getSize();
        int currentPage = contestVO.getCurrentPage();
        
        int startRow = (currentPage - 1) * size + 1;
        int endRow = currentPage * size;
        contestVO.setStartRow(startRow);
        contestVO.setEndRow(endRow);
        
		List<ContestVO> cttList = contestMapper.selectCttList(contestVO);
        log.info("selectCttList 결과 (조회된 공모전 목록): {}개", cttList.size());
		return cttList;
	}
	
	//공모전 총 개수 조회
	@Override
	public int selectCttCount(ContestVO contestVO) {
		
		if(contestVO.getContestGubunFilter() == null || contestVO.getContestGubunFilter().isEmpty()){
			List<String> contestGubunFilter = new ArrayList<>();
			contestGubunFilter.add("G32001");
			contestVO.setContestGubunFilter(contestGubunFilter);
        }
		
		int totalCount = contestMapper.selectCttCount(contestVO);
        log.info("selectCttCount 결과 (총 게시물 수): {}", totalCount);
		return totalCount;
	}
	
	// 공모전 상세
	@Override
	@Transactional
	public ContestVO selectCttDetail(String cttId) {
		// 0. [추가] 조회수를 먼저 1 증가시킵니다.
        contestMapper.updateCttViewCount(cttId);
        
		// 1. DB에서 상세 정보 조회
	    ContestVO detail = contestMapper.selectCttDetail(cttId);
	    
	    if (detail != null && detail.getContestDescription() != null) {
	        // 2. 상세 설명(contestDescription)을 '●' 기준으로 나누기
	        String[] sections = detail.getContestDescription().split("●");
	        
	        // 3. 나눈 조각들을 List<String>으로 변환하여 새로운 필드에 저장
	        List<String> sectionList = new ArrayList<>();
	        for (String section : sections) {
	            String trimmedSection = section.trim();
	            if (!trimmedSection.isEmpty()) {
	                sectionList.add(trimmedSection);
	            }
	        }
	        detail.setDescriptionSections(sectionList);
	    }
	    
	    log.info("selectCttDetail 결과: {}", detail);
	    return detail;
	}
	
	//공모전분류 목록 조회
    @Override
    public List<ComCodeVO> getContestTypeList() {
        return contestMapper.selectContestTypeList();
    }

    //모집 대상 목록 조회
    @Override
    public List<ComCodeVO> getContestTargetList() {
        return contestMapper.selectContestTargetList();
    }

    //merge
	@Override
	public int updateContest(ContestVO contestVO) {
		return contestMapper.updateContest(contestVO);
	}

	//delete
	@Override
	public int deleteContest(String contestId) {
		return contestMapper.deleteContest(contestId);
	}
}
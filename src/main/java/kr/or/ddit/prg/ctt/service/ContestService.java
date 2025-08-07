package kr.or.ddit.prg.ctt.service;

import java.util.List;

public interface ContestService {
	
	//공모전 목록 조회
    List<ContestVO> selectCttList(ContestVO contestVO);

    //공모전 총 개수 조회
    int selectCttCount(ContestVO contestVO);

    // 공모전 상세
    ContestVO selectCttDetail(String cttId);
}

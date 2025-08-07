package kr.or.ddit.prg.ctt.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.prg.ctt.service.ContestVO;

@Mapper
public interface ContestMapper {
	//공모전 목록 조회
    List<ContestVO> selectCttList(ContestVO contestVO);

    //공모전 총 개수 조회
    int selectCttCount(ContestVO contestVO);

    // 공모전 상세
    ContestVO selectCttDetail(String cttId);
}

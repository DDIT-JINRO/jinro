package kr.or.ddit.prg.ctt.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.prg.ctt.service.ContestVO;

@Mapper
public interface ContestMapper {
	//공모전 목록 조회
    public List<ContestVO> selectCttList(ContestVO contestVO);

    //공모전 총 개수 조회
    public int selectCttCount(ContestVO contestVO);

    // 공모전 상세
    public ContestVO selectCttDetail(String cttId);

	//공모전분류 목록 조회
    public List<ComCodeVO> selectContestTypeList();

    //모집 대상 목록 조회
    public List<ComCodeVO> selectContestTargetList();

    //공모전 조회수 증가 메소드
    public int updateCttViewCount(String cttId);
}

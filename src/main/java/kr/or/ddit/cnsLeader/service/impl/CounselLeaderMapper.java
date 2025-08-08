package kr.or.ddit.cnsLeader.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.cns.service.CounselingLogVO;
import kr.or.ddit.cns.service.CounselingVO;

@Mapper
public interface CounselLeaderMapper {

	List<CounselingVO> selectCounselLogList(CounselingVO counselingVO);

	int selectTotalCounselLogList(CounselingVO counselingVO);

	int updateCounselLog(CounselingLogVO counselingLogVO);

}

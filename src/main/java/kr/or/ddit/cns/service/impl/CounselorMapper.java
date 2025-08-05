package kr.or.ddit.cns.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;

import kr.or.ddit.cns.service.CounselingVO;

@Mapper
public interface CounselorMapper {

	/**
	 * 상담사 측에서 본인 상담리스트 조회.
	 * @return
	 */
	public List<CounselingVO> selectCounselList();

	/**
	 * 상담사 측에서 본인 상담리스트 중 상담완료된 리스트 조회.
	 * 상담일지 작성용
	 * @return
	 */
	public List<CounselingVO> selectCompletedCounselList();

}

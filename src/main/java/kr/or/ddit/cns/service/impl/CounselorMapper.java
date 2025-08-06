package kr.or.ddit.cns.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;

import kr.or.ddit.cns.service.CounselingLogVO;
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
	public List<CounselingVO> selectCompletedCounselList(CounselingVO counselingVO);

	/**
	 * 상담사 측에서 본인 상담리스트 중 상담완료된 리스트 조회. 갯수 카운트
	 * 상담일지 작성 페이징용
	 * @param counselingVO
	 * @return
	 */
	public int selectTotalCompletedCounselList(CounselingVO counselingVO);

	/**
	 * 상담번호 파라미터로 상세조회
	 * @param counselId
	 * @return
	 */
	public CounselingVO selectCounselDetail(int counselId);

	/**
	 * 상담일지 삽입, 이미 존재하는 상담일지번호면 수정
	 * @param counselingLogVO
	 * @return
	 */
	public int updateCnsLog(CounselingLogVO counselingLogVO);

}

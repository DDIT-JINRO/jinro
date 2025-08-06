package kr.or.ddit.cns.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.ArticlePage;

public interface CounselorService {

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
	public ArticlePage<CounselingVO> selectCompletedCounselList(CounselingVO counselingVO);

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
	public boolean updateCnsLog(CounselingLogVO counselingLogVO);

	/**
	 * 상담일지에 첨부된 파일 중 단일 파일 삭제.
	 * @param groupId
	 * @param seq
	 * @return
	 */
	public boolean deleteFile(Long fileGroupId, int seq);

}

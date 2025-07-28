package kr.or.ddit.util.alarm.service;

import java.util.Date;
import java.util.List;

public interface AlarmService {

	/**
	 * 알림테이블에 데이터 삽입
	 * @param alarmVO
	 * @return
	 */
	int insertAlarm(AlarmVO alarmVO);

	/**
	 * 멤버아이디로 특정회원 알림 가져오기
	 * @param memId
	 * @return
	 */
	List<AlarmVO> selectAllByMember(int memId);

	/**
	 * 단일 알림건 읽음 처리
	 * @param alarmId
	 * @return
	 */
	int updateMarkRead(int alarmId);

	/**
	 * 단일 알림건 삭제 처리
	 * @param alarmId
	 * @return
	 */
	int deleteById(int alarmId);

	/**
	 * 전체 알림건 삭제 처리
	 * @param memId
	 * @return
	 */
	int deleteAllByMember(int memId);
	
	/**
	 * 댓글이 생기거나 좋아요가 발생하면 호출. 
	 * 필요 파라미터 4개
	 *	private int alarmId;			// X 
		private int memId;				// 회원번호(알림받을 대상) ex.게시글에 새 댓글이면 게시글작성자
		private AlarmType alarmTargetType;	// 알림유형(enum으로 정리해둠)
		private String alarmContent;	// X
		private String alarmIsRead;		// X
		private Date alarmCreatedAt;	// X
		private int alarmTargetId;		// 알림대상번호 (좋아요나 댓글이 달린 대상의 번호)
		private String alarmTargetUrl;	// 알림대상url (알림클릭시 이동시킬 url)
	 * @param alarmVO
	 */
	void sendEvent(AlarmVO alarmVO);
	
}

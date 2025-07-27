package kr.or.ddit.util.alarm.service;

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

}

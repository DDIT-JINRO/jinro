package kr.or.ddit.util.alarm.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.or.ddit.util.alarm.service.AlarmService;
import kr.or.ddit.util.alarm.service.AlarmType;
import kr.or.ddit.util.alarm.service.AlarmVO;

@Service
public class AlarmServiceImpl implements AlarmService{
	
	@Autowired
	AlarmMapper alarmMapper;
	
	@Autowired
	AlarmEmitterManager emitterManager;

	@Override
	public int insertAlarm(AlarmVO alarmVO) {
		return this.alarmMapper.insertAlarm(alarmVO);
	}

	@Override
	public List<AlarmVO> selectAllByMember(int memId) {
		return this.alarmMapper.selectAllByMember(memId);
	}

	@Override
	public int updateMarkRead(int alarmId) {
		return this.alarmMapper.updateMarkRead(alarmId);
	}

	@Override
	public int deleteById(int alarmId) {
		return this.alarmMapper.deleteById(alarmId);
	}

	@Override
	public int deleteAllByMember(int memId) {
		return this.alarmMapper.deleteAllByMember(memId);
	}

	@Override
	public void sendEvent(AlarmVO alarmVO) {
		// 알림 내용 설정
		String content = createContent(alarmVO.getAlarmTargetType());
		int targetMemId = alarmVO.getMemId();
		alarmVO.setAlarmContent(content);
		// DB에 저장.
		this.alarmMapper.insertAlarm(alarmVO);
		// alarm 대상 꺼내서 emitter에서 확인
		SseEmitter sseEmitter = this.emitterManager.getEmitter(targetMemId);
		// 있으면 전송
		if(sseEmitter != null) {
			try {
				sseEmitter.send(SseEmitter.event()
										.name("alarm")
										.data(alarmVO)
										.build());
			} catch (IOException e) {
				// 클라이언트의 EventSource 객체와 sseEmitter 연결이 끊겨서
				// 가지고 있는 객체가 send 할 수 없을경우 에러 발생
				// sseEmitter를 종료시킴.
				sseEmitter.completeWithError(e);
			}	
		}
	}
	
	private String createContent(AlarmType type) {
		switch (type) {
		case REPLY_TO_BOARD:
			return "게시글에 새 댓글이 달렸습니다";
		case REPLY_TO_REPLY:
			return "댓글에 새 댓글이 달렸습니다";
		case LIKE_TO_BOARD:
			return "게시글에 좋아요가 눌렸습니다";
		case LIKE_TO_REPLY:
			return "댓글에 좋아요가 눌렸습니다";
		default:
			throw new IllegalArgumentException("정의되지 않은 알림 유형 : "+type);
		}
	}

}

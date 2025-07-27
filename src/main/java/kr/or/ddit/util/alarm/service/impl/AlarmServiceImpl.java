package kr.or.ddit.util.alarm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.util.alarm.service.AlarmService;
import kr.or.ddit.util.alarm.service.AlarmVO;

@Service
public class AlarmServiceImpl implements AlarmService{

	AlarmMapper alarmMapper;

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

}

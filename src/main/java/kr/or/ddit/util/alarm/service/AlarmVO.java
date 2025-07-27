package kr.or.ddit.util.alarm.service;

import java.util.Date;

import lombok.Data;

@Data
public class AlarmVO {
	private int alarmId;
	private int memId;
	private String alarmTargetType;
	private String alarmContent;
	private String alarmIsRead;
	private Date alarmCreatedAt;
	private int boardId;
	private String replyId;
}

package kr.or.ddit.chat.service;

import java.time.LocalDateTime;

public class ChatReceiverVO {
	private int msgId;				// 메시지번호
	private int receiverId;			// 수신자번호
	private LocalDateTime readAt;	// 읽음 시각
}

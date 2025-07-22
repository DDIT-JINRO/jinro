package kr.or.ddit.chat.service;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatMessageVO {
	private int msgId;		//	채팅 메시지번호 시퀀스처리
	private int crId;		//	채팅방 번호
	private int memId;		//	발송자 아이디
	private String message;	//	채팅내용
	private LocalDateTime sentAt;	//	발송 일시
}

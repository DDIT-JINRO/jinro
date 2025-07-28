package kr.or.ddit.chat.service;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatRoomVO {
	private int crId;			//	채팅방번호 시퀀스처리 필요
	private String ccId;		//	채팅방 타입(목적구분) 공통코드 처리 G04001스터디 G04002상담
	private int targetId;		//	채팅방이 열린 게시글 혹은 상담의 번호
	private String crTitle;		//	채팅방 제목
	private int crMaxCnt;		//	채팅방 제한 인원
	private LocalDateTime createdAt;	//	채팅방 생성일
}

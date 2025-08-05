package kr.or.ddit.chat.service;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatMessageVO {
	private int msgId;				//	채팅 메시지번호 시퀀스처리
	private int crId;				//	채팅방 번호
	private int memId;				//	발송자 아이디
	private String message;			//	채팅내용
	private LocalDateTime sentAt;	//	발송 일시
	private String messageType;		// 	메시지 종류 ('TEXT', 'FILE', 'IMG')
	private int fileGroupId;		//	파일그룹번호 (메시지 종류가 TEXT가 아닌경우)

	// 채팅 메시지 작성자 회원정보(사진받아오기)
	private Long fileProfile;
	private Long fileBadge;
	private Long fileSub;
	private String fileProfileStr;
	private String fileBadgeStr;
	private String fileSubStr;
	private String memNickname;
}

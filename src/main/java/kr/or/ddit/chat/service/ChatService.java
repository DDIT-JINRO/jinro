package kr.or.ddit.chat.service;

import java.util.List;

public interface ChatService {

	/**
	 * 회원아이디로 현재 참여중인 채팅방 정보 불러오기 
	 * @param memId
	 */
	List<ChatRoomVO> findRoomsByMemId(String memId);
	
	
	
}

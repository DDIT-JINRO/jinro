package kr.or.ddit.chat.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.chat.service.ChatRoomVO;

@Mapper
public interface ChatMapper {

	/**
	 * 회원아이디로 현재 참여중인 채팅방 정보 불러오기 
	 * @param memId
	 */
	List<ChatRoomVO> findRoomsByMemId(String memId);
	
}

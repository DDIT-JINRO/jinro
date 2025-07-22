package kr.or.ddit.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.chat.service.ChatRoomVO;
import kr.or.ddit.chat.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	ChatMapper chatMapper;

	@Override
	public List<ChatRoomVO> findRoomsByMemId(String memId) {
		return this.chatMapper.findRoomsByMemId(memId);
	}

}

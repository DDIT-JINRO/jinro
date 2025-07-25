package kr.or.ddit.chat.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.chat.service.ChatMemberVO;
import kr.or.ddit.chat.service.ChatMessageVO;
import kr.or.ddit.chat.service.ChatReceiverVO;
import kr.or.ddit.chat.service.ChatMemberVO;
import kr.or.ddit.chat.service.ChatMessageVO;
import kr.or.ddit.chat.service.ChatReceiverVO;
import kr.or.ddit.chat.service.ChatRoomVO;
import kr.or.ddit.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
	
	
	@Autowired
	ChatRoomSessionManager chatRoomSessionManager;
	
	@Autowired
	ChatMapper chatMapper;

	@Override
	public List<ChatRoomVO> findRoomsByMemId(String memId) {
		return this.chatMapper.findRoomsByMemId(memId);
	}

	@Override
	public void participateChatRoom(ChatMemberVO chatMemberVO) {
		ChatMemberVO participatedMember = this.chatMapper.selectChatMember(chatMemberVO);
		// 이 객체가 null 값이면 최초입장. 객체가 있으면 퇴장했던 멤버의 재입장. 
		log.info("participateChatRoom -> participatedMember : "+participatedMember);
		
		int result = this.chatMapper.insertAndUpdateChatMember(chatMemberVO);
	}

	@Override
	public void exitChatRoom(int crId, String memId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ChatMessageVO> selectChatMsgByChatRoomIdAndMemId(ChatMemberVO vo) {
		return this.chatMapper.selectChatMsgByChatRoomIdAndMemId(vo);
	}

	@Override
	public void saveChatMessage(ChatMessageVO chatMessageVO) {
		System.out.println(chatMessageVO);
        if(chatMessageVO.getMessageType()==null) {
        	chatMessageVO.setMessageType("TEXT");
        }
        // 1. 메시지 insert
        this.chatMapper.insertChatMessage(chatMessageVO);
        
        // 2. 채팅방 참여자 전체 목록 불러오기
        List<Integer> enteredMemList = this.chatMapper.findChatMemberIdsByCrId(chatMessageVO.getCrId());
        // 3. 채팅방 구독중(채팅방을 오픈한 유저) 목록 불러오기
        Set<Integer> subscribeMemList = this.chatRoomSessionManager.getOpendUser(chatMessageVO.getCrId());
        
        // 4. 수신자 테이블 처리 (구독중인 사람은 현재시간, 아닌사람은 null)
        for(int receiverId : enteredMemList) {
        	ChatReceiverVO chatReceiverVO = new ChatReceiverVO();
        	chatReceiverVO.setMsgId(chatMessageVO.getMsgId());
        	chatReceiverVO.setReceiverId(receiverId);
        	
        	if(receiverId == chatMessageVO.getMemId() || subscribeMemList.contains(receiverId)) {
        		chatReceiverVO.setReadAt(LocalDateTime.now());
        	}else {
        		chatReceiverVO.setReadAt(null);
        	}
        	
        	this.chatMapper.insertChatReceiver(chatReceiverVO);
        }
        
	}

	@Override
	public List<ChatReceiverVO> selectUnreadCountGroupByMemId() {
		return this.chatMapper.selectUnreadCountGroupByMemId();
	}

	@Override
	public List<ChatReceiverVO> selectUnreadCountOfRoomsByMemId(int memId) {
		return this.chatMapper.selectUnreadCountOfRoomsByMemId(memId);
	}

	@Override
	public int receiverReadAtUpdate(ChatMemberVO chatMemberVO) {
		return this.chatMapper.receiverReadAtUpdate(chatMemberVO);
	}

	@Override
	public ChatReceiverVO selectUnreadCountByMemId(int memId) {
		return this.chatMapper.selectUnreadCountByMemId(memId);
	}

	@Override
	public int insertChatRoom(ChatRoomVO chatRoomVO) {
		return this.chatMapper.insertChatRoom(chatRoomVO);
	}

	@Override
	public boolean isEntered(int crId, String memIdStr) {
		List<ChatRoomVO> rooms = this.findRoomsByMemId(memIdStr);
		if(rooms == null || rooms.size() == 0) return false;
		
		for(ChatRoomVO chatRoomVO : rooms) {
			if(chatRoomVO.getCrId() == crId) {
				return true;
			}
		}
		
		return false;
	}
	

}

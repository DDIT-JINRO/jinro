package kr.or.ddit.chat.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.chat.service.ChatMessageVO;
import kr.or.ddit.chat.service.ChatRoomVO;
import kr.or.ddit.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {
	
	@Autowired
	SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	ChatService chatService;
	
	// 현재 참여중인 스터디그룹 채팅방 목록 조회하기
	@GetMapping("/api/chat/rooms")
	public ResponseEntity<List<ChatRoomVO>> getMyChatRooms(@AuthenticationPrincipal String memId) {
		log.info("getMyChatRooms -> memId : "+memId);
		// 로그인 안되어 있을 경우 처리
//		if(memId==null || memId.equals("anonymousUser")) {
//			System.out.println("로그인 안됨 확인");
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//		}
	    List<ChatRoomVO> rooms = chatService.findRoomsByMemId(memId);
	    System.out.println(rooms);
	    return ResponseEntity.ok(rooms);
	}
	
	// 채팅 메시지 전송
	@MessageMapping("/chat/message")  // 클라이언트에서 /pub/chat/message로 전송 시 매핑
	public void sendMessage(ChatMessageVO chatMessageVO, SimpMessageHeaderAccessor headerAccessor) {
		//log.info("sendMessage -> headerAccessor.getSessionId() : "+headerAccessor.getSessionId());
		//log.info("sendMessage -> headerAccessor.getDestination() : "+headerAccessor.getDestination());
		//log.info("sendMessage -> headerAccessor.getSubscriptionId() : "+headerAccessor.getSubscriptionId());
		System.out.println("chat : "+chatMessageVO);
		// 클라이언트에서 /sub/chat/room 경로로 구독중이면 전송 발생
		messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessageVO.getCrId(), chatMessageVO);
	}
	
}

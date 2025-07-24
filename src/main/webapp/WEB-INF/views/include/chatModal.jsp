<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 채팅 모달 -->
<div id="chat-modal">
	<div class="chat-header">
	  <span>채팅</span>
	  <button onclick="closeChatModal()">X</button>
	</div>
	
	<div class="chat-content">
	  <!-- 왼쪽: 채팅방 목록 -->
	  <div id="chatRoomList" class="chat-room-list"></div>
	
	  <!-- 오른쪽: 채팅 메시지 및 입력 -->
	  <div class="chat-message-area">
	    <div id="chat-container"></div>
	    <div class="chat-input">
	      <textarea id="chatMessageInput" placeholder="메시지를 입력하세요..."></textarea>
	      <button id="sendMsgBtn">전송</button>
	    </div>
	  </div>
	</div>
</div>
<!-- 채팅 모달 끝 -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
<script>
const sender = '<sec:authentication property="name" />';
let stompClient = null;	// 소켓 연결 객체
let currentChatRoomId = null; // 현재 보고 있는 채팅방 ID
let unreadCounts = {}; // 안 보고 있는 채팅방의 읽지 않은 메시지 수 관리
let chatRoomSubscription = null; // 채팅방 구독 관리. (현재 보고 있는 채팅방만 구독 : 채팅주고받기);
let unreadDetailSubscription = null; // 전체 채팅방별 안 읽은 갯수 구독 관리

</script>
<script type="text/javascript" src="/js/chatModal.js" ></script>
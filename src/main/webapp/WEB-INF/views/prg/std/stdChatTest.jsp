<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
<!-- 스타일 여기 적어주시면 가능 -->
<style>
#chat-container {
    width: 400px;
    height: 400px;
    border: 1px solid #ddd;
    padding: 10px;
    overflow-y: auto;
}
#chat-input {
    width: 100%;
    box-sizing: border-box;
}
.chat-message {
    max-width: 70%;
    margin: 5px;
    padding: 8px 12px;
    border-radius: 12px;
    word-break: break-word;
    display: inline-block;
    clear: both;
}

.chat-message.mine {
    background-color: #d1e7dd;
    color: #000;
    float: right;
    text-align: right;
}

.chat-message.other {
    background-color: #f1f1f1;
    color: #000;
    float: left;
    text-align: left;
}
</style>
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">프로그램</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/prg/ctt/cttList.do">공모전</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/prg/act/vol/volList.do">대외활동</a></div>
		<div class="channel-sub-section-item"><a href="/prg/std/stdGroupList.do">스터디그룹</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
  		<div class="public-wrapper-main">
  			스터디그룹 채팅방 테스트
		    <h2>채팅 테스트</h2>
   			<div id="chat-container"></div>
			<input type="text" id="chat-input" placeholder="메시지를 입력하세요..." />
			<button type="button" id="sendBtn">채팅 메시지 전송</button>
  			<br/>
			<a href="/prg/std/stdGroupList.do">목록으로</a>
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>

</body>
</html>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
<script>
const roomId = 1;				// 테스트용
const sender = '<sec:authentication property="name" />';
console.log('sender:',sender);
let stompClient = null;

function connect() {
    const socket = new SockJS("/ws-stomp");         // 서버의 config 에서 설정한 엔드포인트로 연결 소켓생성
    stompClient = Stomp.over(socket);               // 연결한 소켓을 Stomp로 (전송, 구독 형태)
    stompClient.connect({
        memId : sender                              // 채팅 추적을 위한 헤더에 보내는 사람 아이디값 추가
    }, function () {
        stompClient.subscribe('/sub/chat/room/' + roomId, function (msg) {
            const container = document.getElementById('chat-container');
            const msgVO = JSON.parse(msg.body);
            let chatMsg = "";
            if(msgVO.memId == sender){
                chatMsg = `<div class="chat-message mine">나 : \${msgVO.message}</div>`;
            }else{
                chatMsg = `<div class="chat-message other">\${msgVO.memId} : \${msgVO.message}</div>`;
            }
            container.innerHTML += chatMsg;
            container.scrollTop = container.scrollHeight;

        });

        // 입장 메시지
        stompClient.send("/pub/chat/message", {}, JSON.stringify({
            type: 'ENTER',
            roomId: roomId,
            sender: sender,
            message: '입장!'
        }));

        document.getElementById("sendBtn").addEventListener("click", function(e) {
            let msg = document.getElementById('chat-input').value;
            if (msg && stompClient) {
                stompClient.send("/pub/chat/message", {}, JSON.stringify({
                    crId : roomId,
                    memId : sender,
                    message : msg,
                    messageType : 'TEXT',
                }));
                document.getElementById('chat-input').value = '';
            }
        });

        document.getElementById('chat-input').addEventListener('keydown', function(e){
            if(e.code=='Enter'){
                document.getElementById("sendBtn").click();
            }
        })
    });
}
connect();
</script>
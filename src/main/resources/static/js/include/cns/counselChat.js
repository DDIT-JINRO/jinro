/**
 *
 */
const crId = document.body.dataset.crId;
let stompClient = null;

document.addEventListener('DOMContentLoaded', function(){
	if(memId && memId !='anonymousUser'){
		const msgInputBox = document.getElementById('chatMessage');
		const sendBtn 	  = document.getElementById('btnSend');

		// 소켓 연결
		connectSocket();

		sendBtn.addEventListener("click", function(){
			const msg = msgInputBox.value.trim();
			if(msg==null && msg==''){
				alert('메시지를 입력해주세요');
				return;
			}

			sendMessage(crId, msg);
		})

	}
})

// 소켓 연결 함수
function connectSocket() {
    const socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);

	//stompClient.debug = () => {};	// 콘솔 출력안되게 덮어쓰기
    stompClient.connect({}, (frame) => {
		stompClient.subscribe(`/sub/chat/counsel/${crId}`, (message) => {
			const data = JSON.parse(message.body);
			console.log(data);
		});
    });
}

// 메시지 전송
function sendMessage(crId, content) {
	content = content.replace(/\n/g, '<br/>');
    const msg = {
        crId: crId,
        message: content,
        memId: memId, // 전역에서 선언된 로그인된 사용자 ID
    };

    stompClient.send("/pub/chat/counsel", {}, JSON.stringify(msg));
}
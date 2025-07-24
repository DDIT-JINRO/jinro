/**
 * 헤더의 채팅 모달을 컨트롤 하기 위한 js
 */

document.addEventListener('DOMContentLoaded', function(){
	// 소켓 연결
	connectSocket();

	// 플로팅 버튼 클릭시 모달 오픈
	document.getElementById('chatRooms').addEventListener('click',openChatModal);

	// 입력창, 전송버튼에 이벤트 등록
	const inputEl = document.getElementById('chatMessageInput');
	const sendBtn = document.getElementById('sendMsgBtn');
	sendBtn.addEventListener('click', function () {
	    sendCurrentInput();
	});
	inputEl.addEventListener('keyup', function (e) {
	    if (e.code === 'Enter' && !e.shiftKey) {
	        e.preventDefault();
	        sendCurrentInput();
	    }
	});

	function sendCurrentInput() {
	    const content = inputEl.value.trim();
	    if (!content) return;

	    inputEl.value = '';
	    sendMessage(currentChatRoomId, content);
	}

})

document.addEventListener('click', function(e){
	// 모달 바깥쪽 클릭시 모달창 닫기
	if(!e.target.closest('#chat-modal')&&!e.target.closest('#chatRooms')){
		closeChatModal();
	}
})

// 모달 닫기
function closeChatModal(){
	// 채팅방 목록 비우기
	document.getElementById('chatRoomList').innerHTML = "";
	// 채팅창 영역 비우기
	document.getElementById('chat-container').innerHTML = "";
	document.getElementById('chat-modal').style.display = 'none';

	// 보고 있는 채팅방 초기화
	currentChatRoomId = null;

	// 구독중인 특정 채팅방이 있으면 구독 해제
	if(chatRoomSubscription){
		chatRoomSubscription.unsubscribe();
		chatRoomSubscription = null;
	}

	// 구독중인 채팅방별 안일음갯수 구독 해제
	if(unreadDetailSubscription){
		unreadDetailSubscription.unsubscribe();
		unreadDetailSubscription = null;
	}
}

// 모달 열기
async function openChatModal(){
	await printChatRoomList();
	subscribeToUnreadDetail();
	document.getElementById('chat-modal').style.display = 'flex';
}

// 채팅방 목록 채우기 -> 모달 열때 호출
// 유저가 참여중인 채팅방 목록 불러와서 출력
async function printChatRoomList() {
    const list = document.getElementById("chatRoomList");
    list.innerHTML = "";
    const response = await fetch('/api/chat/rooms')
    const chatRoomList = await response.json();

	const unreadResponse = await fetch('/api/chat/unread');
	const unreadList = await unreadResponse.json();

	const unreadMap = {};
	unreadList.forEach((unreadVO)=>{
		unreadMap[unreadVO.crId] = unreadVO.unreadCnt;
	})

    chatRoomList.forEach(chatRoom =>{
		const wrapper = document.createElement("div");
		wrapper.classList.add("chat-room-entry");
		wrapper.dataset.crId = chatRoom.crId;

		// 왼쪽: 채팅방 제목
		const title = document.createElement("span");
		title.textContent = chatRoom.crTitle;
		title.classList.add("chat-room-title");

		// 오른쪽: 읽지 않은 메시지 수 뱃지 (초기엔 숨김)
		const badge = document.createElement("span");
		badge.classList.add("chat-unread-badge");

		const unreadCnt = unreadMap[chatRoom.crId];

		if(unreadCnt && unreadCnt > 0){
			badge.style.display = 'inline-block';
			badge.textContent = unreadCnt;
		}else{
			badge.style.display = 'none'; // 초기엔 숨김
			badge.textContent = "0";
		}

		wrapper.appendChild(title);
		wrapper.appendChild(badge);

		wrapper.onclick = () => printFetchMessages(wrapper);
		list.appendChild(wrapper);
    })
}

// 참여중인 채팅방 별 안읽은 갯수 받아오기 구독 -> 모달 열때 호출
function subscribeToUnreadDetail() {
    if (stompClient) {
        unreadDetailSubscription = stompClient.subscribe(`/sub/chat/unread/detail/${sender}`, (message) => {
			const data = JSON.parse(message.body);

			if(data.length >= 1){
				data.forEach(unreadVO =>{
					const crId = unreadVO.crId;
					const unreadCnt = unreadVO.unreadCnt;
					unreadCounts[crId] = unreadCnt;
					showUnreadBadge(crId);
				})
			}
			
			if(data.length >= 1){
				data.forEach(unreadVO =>{
					const crId = unreadVO.crId;
					const unreadCnt = unreadVO.unreadCnt;
					unreadCounts[crId] = unreadCnt;
					showUnreadBadge(crId);
				})	
			}
        });
    }
}

// 채팅방 채팅 불러와서 채우기 -> 채팅방 목록 클릭했을 때 호출
async function printFetchMessages(el) {
    const crId = el.dataset.crId;

    // 현재 채팅방 ID 업데이트
    currentChatRoomId = crId;	// 현재 보고있는 채팅방 변경
    unreadCounts[crId] = 0;		// 현재 채팅방 안읽음 숫자 변경
    await removeUnreadBadge(crId);	// 현재 채팅방 안읽음 UI 제거

	// 채팅방 클릭 후 플로팅의 변경을 위해 안읽은 토탈카운트 호출
	const resp = await fetch('/api/chat/totalUnread');
	const data = await resp.json();
	updateFloatingBadge(data.unreadCnt);

	// 다른 채팅방 구독중이면 해제
	if(chatRoomSubscription){
		chatRoomSubscription.unsubscribe();
	}

	// 채팅 이력 불러오기
	const container = document.getElementById('chat-container');
	container.innerHTML = "";

	fetch(`/api/chat/message/list?crId=${crId}`)
	    .then(resp => resp.json())
	    .then(data => {
	        data.forEach(msgVO => appendMessage(msgVO));
	    });

    // 새 구독 등록 (현재 채팅방)
    const sub = stompClient.subscribe(`/sub/chat/room/${crId}`, (message) => {
        const msg = JSON.parse(message.body);

        if (currentChatRoomId === crId) {
            appendMessage(msg);
        }
    });
    chatRoomSubscription = sub;

}

// 소켓 연결 함수
function connectSocket() {
    const socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
		// 연결된 직후 최초 전체 안읽음 갯수 받아오기
		fetch('/api/chat/totalUnread')
		.then(resp =>{
			if(!resp.ok) throw new Error('에러 발생');
			return resp.json();
		})
		.then(data =>{
			updateFloatingBadge(data.unreadCnt);
		})
		.catch(err=>{
			console.error(err);
		})

		// 플로팅 뱃지에 전체 안읽음 갯수를 세팅하기 위한 구독
		stompClient.subscribe(`/sub/chat/unread/summary/${sender}`, (message) => {
			const data = JSON.parse(message.body);
			console.log("플로팅용전체",data);
		    const { unreadCnt } = JSON.parse(message.body);
		    updateFloatingBadge(unreadCnt);
		});
    });
}


// 메시지 전송
function sendMessage(roomId, content) {
	content = content.replace(/\n/g, '<br/>');
    const msg = {
        crId: roomId,
        message: content,
        memId: sender, // 전역에서 선언된 로그인된 사용자 ID
    };

    stompClient.send("/pub/chat/message", {}, JSON.stringify(msg));
}

// 메시지 출력
function appendMessage(msgVO) {
    const container = document.getElementById('chat-container');
    const isMine = msgVO.memId == sender;

    const chatHTML = `<div class="chat-message ${isMine ? 'mine' : 'other'}">
                        ${isMine ? '나' : msgVO.memId} : ${msgVO.message}
                      </div>`;
    container.innerHTML += chatHTML;
    container.scrollTop = container.scrollHeight;
}

// 안읽음 UI 반영 (채팅방 목록)
function showUnreadBadge(roomId) {
    const roomEl = document.querySelector(`.chat-room-entry[data-cr-id="${roomId}"]`);
    if (!roomEl) return;

    const badge = roomEl.querySelector('.chat-unread-badge');
    if (badge) {
        badge.textContent = unreadCounts[roomId];
        badge.style.display = 'inline-block';
    }
}

// 안읽음 UI 제거 (채팅방 목록)
async function removeUnreadBadge(roomId) {
    const roomEl = document.querySelector(`.chat-room-entry[data-cr-id="${roomId}"]`);
    if (!roomEl) return;

    const badge = roomEl.querySelector('.chat-unread-badge');
    if (badge) {
        badge.style.display = 'none';
        badge.textContent = "0";
    }

	// 서버에 해당 채팅방&현재 유저 전체 읽음으로 처리.
	await fetch(`/api/chat/updateRead?crId=${roomId}`, {
	    method: 'POST'
	}).then(res => {
	    if (!res.ok) throw new Error("서버 읽음 처리 실패");
	    console.log(`채팅방 ${roomId} 읽음 처리 완료`);
	}).catch(err => {
	    console.error("읽음 처리 오류:", err);
	});
}

// 플로팅 버튼 안읽음 업데이트
function updateFloatingBadge(totalUnread) {
    const badge = document.getElementById("chatFloatingBadge");
    if (!badge) return;
    if (totalUnread && totalUnread > 0) {
        badge.textContent = totalUnread;
        badge.style.display = 'inline-block';
    } else {
        badge.textContent = "0";
        badge.style.display = 'none';
    }
}

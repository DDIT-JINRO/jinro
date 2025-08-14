/**
 * 헤더의 채팅 모달을 컨트롤 하기 위한 js
 */

document.addEventListener('DOMContentLoaded', function(){
	// 로그인 여부 확인
	if(memId && memId !='anonymousUser'){
		// 소켓 연결
		connectSocket();
		// 플로팅 버튼 클릭시 모달 오픈

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
			const imageInput = document.getElementById('attach-input-img');
			const fileInput = document.getElementById('attach-input-file');

		    inputEl.value = '';
			const fileObj = {};
			if(imageInput.files && imageInput.files.length > 0){
				fileObj.messageType = 'IMAGE';
				fileObj.files = imageInput.files;
				sendMessage(currentChatRoomId, content, fileObj);
				return;
			}
			if(fileInput.files && fileInput.files.length > 0){
				fileObj.messageType = 'FILE';
				fileObj.files = fileInput.files;
				sendMessage(currentChatRoomId, content, fileObj);
				return;
			}

			// 파일 첨부 안한 경우에 메시지도 입력 안했으면 요청안되도록.
		    if (!content) return;
		    sendMessage(currentChatRoomId, content);
		}

	}

	document.getElementById('chatRooms').addEventListener('click',openChatModal);

	const exitBtn = document.getElementById('exitBtn');
	if(exitBtn){
		exitBtn.addEventListener('click',function(){
			const crId =  exitBtn.dataset.crId;
			const data = {memId, crId}
			fetch(`/api/chat/exit`,{
				method:"POST",
				headers:{"Content-Type":"application/json"},
				body:JSON.stringify(data),
			})
			.then(resp =>resp.json())
			.then(result =>{
				if(result){
					// 채팅방 구독 해제
					if(chatRoomSubscription){
						chatRoomSubscription.unsubscribe();
						chatRoomSubscription = null;
					}
					document.querySelector(`.chat-room-entry[data-cr-id="${crId}"]`).remove();
					document.getElementById('chat-input').style.display = 'none';
					document.querySelector('.chat-room-meta').style.display = 'none';
					const emptyChatMsg = `
						<p class="chat-room-no-selected">목록에서 채팅방을 선택해주세요</p>
					`;
					document.getElementById('chat-container').innerHTML = emptyChatMsg;

					const roomList = document.querySelectorAll('.chat-room-entry');
					if(roomList.length == 0){
						const emptyRoomListMsg = `
							<p class="chat-room-no-selected">
							입장한 채팅방이 없습니다<br/>
							<a href="/prg/std/stdGroupList.do">스터디그룹 보러가기</a>
							</p>
						`;
						document.getElementById('chatRoomList').innerHTML = emptyRoomListMsg;
					}
				}
			})
			.catch(err =>{
				console.error(err);
			})
		})
	}

	const imgAttachBtn = document.getElementById('chatImgBtn');
	const fileAttachBtn = document.getElementById('chatFileBtn');
	const imgInput = document.getElementById('attach-input-img');
	const fileInput = document.getElementById('attach-input-file');
	const previewBarEl  = document.getElementById('attach-preview-bar');
	const previewListEl = document.getElementById('attachPreviewList');
	const clearAttachBtn = document.getElementById('clearAttachBtn');
	imgAttachBtn.addEventListener('click', function(){
		imgInput.value = '';
		fileInput.value = '';
		imgInput.click();
	})
	fileAttachBtn.addEventListener('click', function(){
		imgInput.value = '';
		fileInput.value = '';
		fileInput.click();
	})

	function renderAttachOverlay() {
	  const imgCount  = (imgInput.files && imgInput.files.length) || 0;
	  const fileCount = (fileInput.files && fileInput.files.length) || 0;

	  if (imgCount === 0 && fileCount === 0) {
	    previewBarEl.style.display = 'none';
	    previewListEl.innerHTML = '';
	    return;
	  }

	  previewBarEl.style.display = 'flex';

	  if (imgCount > 0) {
	    previewListEl.innerHTML =
	      `<span>🖼️ <b>이미지 첨부</b> · ${imgCount}개 선택됨</span>`;
	  } else {
	    previewListEl.innerHTML =
	      `<span>📎 <b>파일 첨부</b> · ${fileCount}개 선택됨</span>`;
	  }
	}


	imgInput.addEventListener('input', () => {
	  if (imgInput.files?.length) {
	    fileInput.value = '';        // 파일 선택 비우기 (파일 모드 종료)
	  }
	  renderAttachOverlay();
	});
	imgInput.addEventListener('change', () => {
	  if (imgInput.files?.length) {
	    fileInput.value = '';
	  }
	  renderAttachOverlay();
	});

	fileInput.addEventListener('input', () => {
	  if (fileInput.files?.length) {
	    imgInput.value = '';         // 이미지 선택 비우기 (이미지 모드 종료)
	  }
	  renderAttachOverlay();
	});
	fileInput.addEventListener('change', () => {
	  if (fileInput.files?.length) {
	    imgInput.value = '';
	  }
	  renderAttachOverlay();
	});

	// "모두 제거" 버튼: 현재 선택만 초기화
	clearAttachBtn.addEventListener('click', () => {
	  imgInput.value = '';
	  fileInput.value = '';
	  renderAttachOverlay();
	});

})



document.addEventListener('click', function(e){
	// 모달 바깥쪽 클릭시 모달창 닫기
	if(!e.target.closest('#chat-modal')&&!e.target.closest('#chatRooms')){
		closeChatModal();
	}
})

function cleanInputDatas(){
	// 첨부 input 요소 비우기
	const imgInputEl  = document.getElementById('attach-input-img');
	const fileInputEl = document.getElementById('attach-input-file');
	const previewBarEl = document.getElementById('attach-preview-bar');
	const previewListEl = document.getElementById('attachPreviewList');
	const messageTextarea = document.getElementById('chatMessageInput');

	if (imgInputEl) imgInputEl.value = '';
	if (fileInputEl) fileInputEl.value = '';
	if (previewBarEl) previewBarEl.style.display = 'none';
	if (previewListEl) previewListEl.innerHTML = '';
	if (messageTextarea) messageTextarea.value = '';

}
// 모달 닫기
function closeChatModal(){
	cleanInputDatas();
	// 채팅방 목록 비우기
	document.getElementById('chatRoomList').innerHTML = "";
	// 채팅창 영역 비우기
	const emptyRoomMsg = `
		<p class="chat-room-no-selected">목록에서 채팅방을 선택해주세요</p>
	`;
	document.getElementById('chat-container').innerHTML = emptyRoomMsg;
	document.getElementById('chat-modal').style.display = 'none';
	document.getElementById('chat-input').style.display = 'none';
	document.querySelector('.chat-room-meta').style.display = 'none';
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
	if(!memId || memId=='anonymousUser') {
		sessionStorage.setItem("redirectUrl", location.href);
		location.href = "/login";
	} else {
		axios.post("/admin/las/chatVisitLog.do");
		await printChatRoomList();
		subscribeToUnreadDetail();
		document.getElementById('chat-modal').style.display = 'flex';
	}
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

	if (!chatRoomList || chatRoomList.length == 0) {
		const emptyRoomMsg = `
			<p class="chat-room-no-selected">
			입장한 채팅방이 없습니다<br/>
			<a href="/prg/std/stdGroupList.do">스터디그룹 보러가기</a>
			</p>
		`;
		list.innerHTML = emptyRoomMsg;
		return;
	}
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
        unreadDetailSubscription = stompClient.subscribe(`/sub/chat/unread/detail/${memId}`, (message) => {
			const data = JSON.parse(message.body);

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
	cleanInputDatas();

    const crId = el.dataset.crId;
	document.getElementById('exitBtn').dataset.crId = crId;
	const chatTitle = el.querySelector('.chat-room-title').textContent;
	document.getElementById('chat-title').textContent=chatTitle;
	// 채팅방 제목 띄워주기
	document.querySelector('.chat-room-meta').style.display='flex';

	// active 활성화된 채팅방 있으면 지우기.
	const activeRoom = document.querySelectorAll('.chat-room-entry.active');
	if(activeRoom || activeRoom.length > 0){
		activeRoom.forEach(room =>{
			room.classList.remove('active');
		})
	}
	// 클릭된 div active 활성화
	el.classList.add('active');

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

	const chatInput = document.getElementById('chat-input');

	fetch(`/api/chat/message/list?crId=${crId}`)
	    .then(resp => resp.json())
	    .then(data => {
			chatInput.style.display = 'flex';
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

	stompClient.debug = () => {};	// 콘솔 출력안되게 덮어쓰기
    stompClient.connect({}, (frame) => {
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
		stompClient.subscribe(`/sub/chat/unread/summary/${memId}`, (message) => {
			const data = JSON.parse(message.body);
		    const { unreadCnt } = JSON.parse(message.body);
		    updateFloatingBadge(unreadCnt);
		});
    });
}


// 메시지 전송
function sendMessage(roomId, content, fileObj) {
	content = content.replace(/\n/g, '<br/>');
	console.log(fileObj);

	if(fileObj && fileObj.files && fileObj.files.length>0){
		const msg = new FormData();
		msg.append('crId', roomId);
		msg.append('message', content);
		msg.append('memId', memId);
		msg.append('messageType', fileObj.messageType);

		for(let i=0; i<fileObj.files.length; i++){
			msg.append('files', fileObj.files[i]);
		}

		fetch(`/chat/message/upload`,{
			method : 'POST',
			headers : {},
			body : msg
		})
		.then(resp =>{
			if(!resp.ok) throw new Error('업로드 메시지 전송 실패')
				// 전송완료후 비우기
			cleanInputDatas();
		})
		.catch(err =>{
			console.error("파일채팅중 err : ", err);
		})
	}else{
	    const msg = {
	        crId: roomId,
	        message: content,
	        memId: memId, // 전역에서 선언된 로그인된 사용자 ID
	    };
	    stompClient.send("/pub/chat/message", {}, JSON.stringify(msg));
	}
}

// 첨부파일 있는 경우 사이즈를 표시해주기 위한 함수
function formatBytes(size) {
	if (size == null || size === 0) return '';
	const k = 1024, sizes = ['B','KB','MB','GB','TB'];
	let idx = 0;
	while(size > k){
		if(idx == 4) break;
		idx++;
		size /= k;
	}
	return `${size.toFixed(1)}${sizes[idx]}`
}

// 첨부파일에 대응하도록 파일메시지 만들어주기. appendMessage에서 호출됨
function buildFileItemsHTML(fileGroupId, files){
  return (files || []).map((f, idx) => {
    const seq   = f.fileSeq;
    const name  = f.fileOrgName;
    const size  = f.fileSize;
    const sizeLabel = size != null ? formatBytes(+size) : '';
    const ext   = f.fileExt;
    const href  = `/files/download?fileGroupId=${fileGroupId}&seq=${seq}`;

    return `
      <div class="file-item" data-ext="${ext}">
        <div class="file-icon">${ext}</div>
        <div class="file-meta">
          <div class="file-name" title="${escapeHtml(name)}">${escapeHtml(name)}</div>
          ${sizeLabel ? `<div class="file-size">${sizeLabel}</div>` : ''}
        </div>
        <a class="file-download-btn" href="${href}" download>다운로드</a>
      </div>
    `;
  }).join('');
}

// 파일이름에 특수기호 들어가버린경우 치환
function escapeHtml(s='') {
  return String(s)
    .replaceAll('&','&amp;')
    .replaceAll('<','&lt;')
    .replaceAll('>','&gt;')
    .replaceAll('"','&quot;')
    .replaceAll("'",'&#039;');
}

// 메시지 출력
function appendMessage(msgVO) {
	console.log(msgVO);
    const container = document.getElementById('chat-container');
    const isMine = msgVO.memId == memId;

	const timeObj = new Date(msgVO.sentAt);
	const timeStr = `${(""+timeObj.getFullYear()).slice(-2)}. ${("0"+(timeObj.getMonth()+1)).slice(-2)}. ${("0"+(timeObj.getDate())).slice(-2)}. ${("0"+(timeObj.getHours())).slice(-2)}:${("0"+(timeObj.getMinutes())).slice(-2)}`;

	// 입장/퇴장 시스템 메시지 분기
	if (msgVO.messageType == 'enter' || msgVO.messageType == 'exit') {
	    const text = msgVO.messageType == 'enter'
	        ? `${msgVO.memNickname}님이 채팅방에 입장했습니다.`
	        : `${msgVO.memNickname}님이 채팅방에서 나갔습니다.`;

	    const systemHTML = `
	      <div class="message-box system">
	        <div class="system-message">${text}</div>
			<div class="chat-time system-time">${timeStr}</div>
	      </div>
	    `;
	    container.innerHTML += systemHTML;
	    container.scrollTop = container.scrollHeight;
	    return;  // 여기서 끝내고 일반 메시지 렌더링은 건너뜀
	}

	if(msgVO.messageType == 'FILE'){
		const files = msgVO.fileDetailList;
		const filesHTML = buildFileItemsHTML(msgVO.fileGroupId, files);

		const chatHTML = `
		  <div class="message-box ${isMine ? 'mine' : 'other'}">
		    <div class="chat-meta">
		      ${isMine ? `<span class="chat-nickname">${msgVO.memNickname ?? ''}</span>` : '' }
		      <div class="profile-wrapper chat-profile">
		        <img class="profile-img" src="${msgVO.fileProfileStr ? msgVO.fileProfileStr : '/images/defaultProfileImg.png'}" />
		        <img class="badge-img" src="${msgVO.fileBadgeStr ? msgVO.fileBadgeStr : '/images/defaultBorderImg.png'}" />
		        ${msgVO.fileSubStr ? `<img class="effect-img sparkle" src="${msgVO.fileSubStr}"/>` : ''}
		      </div>
		      ${isMine ? '' : `<span class="chat-nickname">${msgVO.memNickname ?? ''}</span>` }
		    </div>

		    <div class="chat-message ${isMine ? 'mine' : 'other'}">
		      ${msgVO.message ? `<div class="text-part" style="margin-bottom:6px;">${msgVO.message}</div>` : ''}
		      <div class="file-bubble-list">
		        ${filesHTML}
		      </div>
		    </div>

		    <div class="chat-time">${timeStr}</div>
		  </div>`;
		container.innerHTML += chatHTML;
		container.scrollTop = container.scrollHeight;
		return;
	}

    const chatHTML = `
	<div class="message-box ${isMine ? 'mine' : 'other'}">
		<div class="chat-meta">
			${isMine ? `<span class="chat-nickname">${msgVO.memNickname}</span>` : '' }
			<div class="profile-wrapper chat-profile">
				<img class="profile-img" src="${msgVO.fileProfileStr ? msgVO.fileProfileStr : '/images/defaultProfileImg.png'}" />
				<img class="badge-img" src="${msgVO.fileBadgeStr ? msgVO.fileBadgeStr : '/images/defaultBorderImg.png'}" />
				${msgVO.fileSubStr ? `<img class="effect-img sparkle" src="${msgVO.fileSubStr}"/>` : ''}
			</div>
			${isMine ? '' : `<span class="chat-nickname">${msgVO.memNickname}</span>` }
		</div>
		<div class="chat-message ${isMine ? 'mine' : 'other'}">
			${msgVO.message}
		</div>
		<div class="chat-time">
		${timeStr}
		</div>
	</div>
					  `;
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

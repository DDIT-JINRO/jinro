<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅 모달</title>

<!-- 스타일 -->
<style>
.modal {
  display: none;
  position: fixed;
  z-index: 999;
  left: 0; top: 0;
  width: 100%; height: 100%;
  background-color: rgba(0,0,0,0.3);
}
.modal-content {
  background-color: white;
  width: 400px;
  max-width: 90%;
  height: 600px;
  margin: 50px auto;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.2);
}
.modal-header {
  background: #f9f9f9;
  padding: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
}
.modal-header .close {
  font-size: 20px;
  cursor: pointer;
}
.chat-body {
  flex: 1;
  background: #e0e0e0;
  padding: 12px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 12px;
}
.chat-footer {
  background: #f1f1f1;
  padding: 8px;
}
.chat-footer input {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 20px;
  outline: none;
}
.chat-message {
  display: flex;
  flex-direction: column;
  max-width: 75%;
  padding: 6px 10px;
  border-radius: 10px;
}
.chat-message .text {
  font-size: 14px;
  line-height: 1.4;
}
.chat-message .time {
  font-size: 10px;
  text-align: right;
  margin-top: 4px;
  opacity: 0.6;
}
.chat-message.received {
  align-self: flex-start;
  background: white;
}
.chat-message.sent {
  align-self: flex-end;
  background: #ffe564;
}
</style>

<!-- Axios CDN -->
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>

<!-- 모달 열기 버튼 -->
<button onclick="openChatModal()">채팅 열기</button>

<!-- 채팅 모달 -->
<div id="chatModal" class="modal" onclick="outsideClick(event)">
  <div class="modal-content">
    <div class="modal-header">
      <span class="title">AI 챗봇</span>
      <span class="close" onclick="closeChatModal()">&times;</span>
    </div>
    <div class="chat-body" id="chatBody">
      <!-- 채팅 메시지 출력 영역 -->
    </div>
    <div class="chat-footer">
      <input type="text" id="chatInput" placeholder="메시지를 입력하세요..."
             onkeydown="if(event.key==='Enter') sendMessage()" />
    </div>
  </div>
</div>

<!-- 스크립트 -->
<script>
function openChatModal() {
  const modal = document.getElementById("chatModal");
  modal.style.display = "block";
  setTimeout(() => document.getElementById("chatInput").focus(), 100);
}

function closeChatModal() {
  document.getElementById("chatModal").style.display = "none";
}

function outsideClick(event) {
  if (event.target.classList.contains("modal")) {
    closeChatModal();
  }
}

document.addEventListener("keydown", function(e) {
  if (e.key === "Escape") closeChatModal();
});

function sendMessage() {
  const input = document.getElementById("chatInput");
  const message = input.value.trim();
  if (!message) return;

  addMessage(message, 'sent');
  input.value = "";

  // POST 요청 보내기
  axios.post('/ai/chatbot', { message: message })
    .then(response => {
      const reply = response.data;
      addMessage(reply, 'received');
    })
    .catch(error => {
      console.error("챗봇 응답 오류:", error);
      addMessage("죄송합니다. 상담 도중 문제가 발생했어요.", 'received');
    });
}

function addMessage(text, type) {
  const chatBody = document.getElementById("chatBody");
  const messageDiv = document.createElement("div");
  messageDiv.className = "chat-message " + type;

  const textDiv = document.createElement("div");
  textDiv.className = "text";
  textDiv.textContent = text;

  const timeDiv = document.createElement("div");
  timeDiv.className = "time";
  const now = new Date();
  timeDiv.textContent = now.getHours().toString().padStart(2, '0') + ":" + now.getMinutes().toString().padStart(2, '0');

  messageDiv.appendChild(textDiv);
  messageDiv.appendChild(timeDiv);

  chatBody.appendChild(messageDiv);
  chatBody.scrollTop = chatBody.scrollHeight;
}
</script>

</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>상담 채팅 (하드코딩 테스트)</title>
  <link rel="stylesheet" href="/css/cns/counselChat.css"/>
  <style>
    /* 아바타 이미지가 없어도 이니셜로 보이게 하는 보완 스타일 */
    .avatar-fallback, .avatar-lg-fallback{
      display:inline-flex;align-items:center;justify-content:center;
      background:#dfe6f3;color:#3b4a66;font-weight:800;user-select:none;
    }
    .avatar-fallback{width:36px;height:36px;border-radius:50%}
    .avatar-lg-fallback{width:48px;height:48px;border-radius:50%}
  </style>
</head>
<body class="counsel-chat-body"
      data-cr-id="999" data-counsel-id="777" data-my-id="123" data-my-role="USER">
  <div class="counsel-chat-app">
    <!-- 좌측: 채팅 -->
    <section class="chat-pane">
      <header class="chat-header">
        <div class="peer">
          <!-- 이미지 없으니 이니셜로 대체 -->
          <div class="avatar-fallback">KY</div>
          <div class="peer-meta">
            <div class="peer-name" id="peerName">김상담</div>
            <div class="peer-sub" id="peerMeta">상담사 · 커리어코칭팀</div>
          </div>
        </div>
        <div class="header-actions">
          <button type="button" class="icon-btn" id="btnRoleToggle" title="역할 전환">역할</button>
          <button type="button" class="icon-btn" title="파일 전송" id="btnFile"><span>📎</span></button>
          <button type="button" class="icon-btn" title="이모지" id="btnEmoji"><span>😊</span></button>
          <button type="button" class="icon-btn" title="팝업 닫기" onclick="window.close();"><span>✖</span></button>
        </div>
      </header>

      <main id="chatScroll" class="chat-scroll">
        <div class="sys-msg">상담 채팅방이 열렸습니다. 원활한 상담을 위해 예의를 지켜주세요.</div>

        <!-- 하드코딩 메시지 샘플 -->
        <div class="msg-row other">
          <div class="bubble-avatar" style="display:none"></div>
          <div class="bubble">
            <div class="bubble-name">Kyung</div>
            <div class="bubble-text">안녕하세요!</div>
            <div class="bubble-meta"><span class="time">10:02</span></div>
          </div>
        </div>

        <div class="msg-row mine">
          <div class="bubble">
            <div class="bubble-text">안녕하세요 상담사 Kim입니다. 😊</div>
            <div class="bubble-meta"><span class="time">10:03</span></div>
          </div>
        </div>

        <div class="msg-row mine">
          <div class="bubble">
            <div class="bubble-text">무엇을 도와드릴까요?</div>
            <div class="bubble-meta"><span class="time">10:03</span></div>
          </div>
        </div>

        <div class="msg-row other">
          <div class="bubble">
            <div class="bubble-name">Kyung</div>
            <div class="bubble-text">제품 문의드리려 합니다.</div>
            <div class="bubble-meta"><span class="time">10:04</span></div>
          </div>
        </div>
      </main>

      <footer class="chat-input">
        <div class="input-wrap">
          <textarea id="chatMessage" rows="1" placeholder="메시지를 입력하세요. Shift+Enter 줄바꿈"></textarea>
          <div class="input-actions">
            <label class="file-label" title="파일 선택">
              <input type="file" id="chatFile" hidden />
              <span>📎</span>
            </label>
            <button id="btnSend" class="send-btn" type="button">전송</button>
          </div>
        </div>
      </footer>
    </section>

    <!-- 우측: 정보 패널 (하드코딩 데이터) -->
    <aside class="side-pane" id="sidePane">
      <!-- 공통 상단 프로필 카드 -->
      <div class="profile-card">
        <div class="avatar-lg-fallback">KY</div>
        <div class="p-meta">
          <div class="p-name" id="cardName">김상담</div>
          <div class="p-sub" id="cardSub">커리어코칭팀</div>
        </div>
      </div>

      <!-- 회원 화면(기본): 상담사 정보 -->
      <section class="info-block" data-view="user">
        <div class="blk-title">상담사 정보</div>
        <dl class="kv">
          <dt>아이디</dt><dd>counselor01</dd>
          <dt>전화</dt><dd>010-1234-5678</dd>
          <dt>이메일</dt><dd>counselor@example.com</dd>
          <dt>전문분야</dt><dd>진로 설계, 이직 코칭, 면접</dd>
          <dt>지역</dt><dd>서울</dd>
        </dl>
      </section>

      <!-- 상담사 화면: 회원 기본정보 + 상담 요청 요약 + 시스템 정보 -->
      <section class="info-block" data-view="counselor" style="display:none">
        <div class="blk-title">회원 기본정보</div>
        <dl class="kv">
          <dt>아이디</dt><dd>kyung_92</dd>
          <dt>이름</dt><dd>경*</dd>
          <dt>전화</dt><dd>010-9876-5432</dd>
          <dt>이메일</dt><dd>kyung@example.com</dd>
          <dt>지역</dt><dd>대전</dd>
        </dl>
      </section>

      <section class="info-block" data-view="counselor" style="display:none">
        <div class="blk-title">상담 요청 요약</div>
        <div class="desc-box">
제품 선택과 커리어 방향성에 대한 조언이 필요합니다.
현재 직무에 대한 불확실성과 인터뷰 대비 전략을 상담받고 싶어요.
        </div>
      </section>

      <section class="info-block" data-view="counselor" style="display:none">
        <div class="blk-title">시스템 정보</div>
        <dl class="kv">
          <dt>OS</dt><dd>Windows 10</dd>
          <dt>브라우저</dt><dd>Chrome 138</dd>
          <dt>언어</dt><dd>ko-KR</dd>
          <dt>위치</dt><dd>KR</dd>
        </dl>
      </section>
    </aside>
  </div>

  <script src="/js/include/cns/counselChat.js"></script>
</body>
</html>

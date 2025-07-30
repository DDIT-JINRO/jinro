<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/prg/std/stdGroupDetail2.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">프로그램</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/prg/ctt/cttList.do">공모전</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/prg/act/vol/volList.do">대외활동</a></div>
		<div class="channel-sub-section-itemIn"><a href="/prg/std/stdGroupList.do">스터디그룹</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
  		<div class="public-wrapper-main">
  			<br/>
  			<!-- 게시글 박스 -->
			<div class="detail-wrapper">
			  <!-- 1) 제목 + 프로필 + 메타 -->
			  <div class="post-header">
			    <h1 class="post-title">${stdBoardVO.boardTitle}</h1>
			    <div class="author-meta">
			      <div class="user-profile">
			        <img src="${stdBoardVO.fileProfile}" alt="프로필" class="profile-image"/>
			      </div>
			      <span class="author-nickname">${stdBoardVO.memNickname}</span>
			    </div>
			    <div class="post-meta">
			      <span class="meta-item">작성일: <fmt:formatDate value="${stdBoardVO.boardCreatedAt}" pattern="yyyy-MM-dd"/></span>
			      <span class="meta-item">조회수: ${stdBoardVO.boardCnt}</span>
			    </div>
			  </div>

			  <!-- 2) 핵심 정보 그리드 -->
			  <dl class="info-grid">
			    <div class="info-item">
			      <dt>지역</dt>
			      <dd>${stdBoardVO.region}</dd>
			    </div>
			    <div class="info-item">
			      <dt>성별</dt>
			      <dd>${stdBoardVO.gender == 'all' ? '전체' : (stdBoardVO.gender=='men'?'남자만':'여자만')}</dd>
			    </div>
			    <div class="info-item">
			      <dt>관심분야</dt>
			      <dd>${interestMap[stdBoardVO.interest]}</dd>  <!-- 서버에서 한글명 치환해 두면 편해요 -->
			    </div>
			    <div class="info-item">
			      <dt>채팅방 제목</dt>
			      <dd>${stdBoardVO.chatRoomVO.crTitle}</dd>
			    </div>
			    <div class="info-item">
			      <dt>입장제한 인원수</dt>
			      <dd>${stdBoardVO.chatRoomVO.crMaxCnt} 명</dd>
			    </div>
			    <div class="info-item">
			      <dt>현재 입장 인원수</dt>
			      <dd>${stdBoardVO.curJoinCnt} 명</dd>
			    </div>
			  </dl>

			  <!-- 3) 본문 -->
			  <div class="group-description">
			    <h2 class="desc-title">게시글 내용</h2>
			    <p>${stdBoardVO.parsedContent}</p>
			  </div>
			</div>

						<!-- 댓글 입력창 -->
			<form action="/prg/std/createStdReply.do" method="post" class="comment-form">
			  <input type="hidden" name="boardId" value="${stdBoardVO.boardId}" />
			  <textarea id="replyContent" name="replyContent" maxlength="300" placeholder="댓글을 입력하세요."></textarea>
			  <div class="comment-footer">
			    <span class="char-count" id="char-count">0 / 300</span>
			    <button type="submit" class="btn-submit">등록</button>
			  </div>
			</form>


			<!-- 댓글 리스트 -->
			<div class="comment-section">
			  <c:forEach var="reply" items="${stdBoardVO.stdReplyVOList}">
			  ${reply }
			  	<!-- 댓글 프로필 영역 -->
			  	<div class="reply-box">
				<span class="etcBtn">…</span>
				<div class="reply-profile">
				  <div class="user-profile">
				    <img class="badge-frame" src="<c:out value="${not empty reply.fileBadge ? reply.fileBadge : '/images/defaultBorderImg.png' }"/>" alt="badge"/>

				    <img class="profile-image" src="<c:out value="${not empty reply.fileProfile ? reply.fileProfile : '/upload/2025/07/30/4b137b23_product_6.jpg' }"/>" alt="profile"/>
				  </div>
				  <div class="writer-info">
				    <div class="reply-nickname">${reply.memNickname}</div>
				    <div class="reply-date"><fmt:formatDate value="${reply.replyCreatedAt}"/></div>
				  </div>
				</div>
				  <div class="reply-content">${reply.replyContent }</div>
				  <div><button>답글</button></div>
				  </div>
				  <!-- 대댓글 (childReplyVOList) -->
				  <c:forEach var="child" items="${reply.childReplyVOList}">
				    <div class="reply-box reply-child">
				      <div class="reply-profile">
				        <div class="user-profile">
				          <img class="badge-frame" src="<c:out value="${not empty child.fileBadge ? child.fileBadge : '/images/defaultBorderImg.png' }"/>" />
				          <img class="profile-image" src="<c:out value="${not empty child.fileProfile ? child.fileProfile : '/images/defaultProfileImg.png' }"/>" />
				        </div>
				        <div class="writer-info">
				          <div class="reply-nickname">${child.memNickname}</div>
				          <div class="reply-date"><fmt:formatDate value="${child.replyCreatedAt}" /></div>
				        </div>
				      </div>
				      <div class="reply-content">${child.replyContent}</div>
				    </div>
				  </c:forEach>
			  </c:forEach>
			</div>

			<!-- 목록 버튼 -->
			<div class="bottom-button">
			  <a href="/prg/std/stdGroupList.do" class="btn-back">목록</a>
			</div>
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script>
const crId = "${stdBoardVO.chatRoomVO.crId}";
</script>
<script src="/js/prg/std/stdGroupDetail.js"></script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/comm/peer/teen/teenDetail.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">커뮤니티</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn">
			<a href="/comm/peer/teen/teenList.do">또래 게시판</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/comm/path/pathList.do">진로/진학 게시판</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<!-- 		<div class="tab-container" id="tabs"> -->
		<!-- 		    <div class="tab ">대학 검색</div> -->
		<!-- 		    <div class="tab active">학과 정보</div> -->
		<!-- 		    <div class="tab">입시 정보</div> -->
		<!-- 		    <div class="tab">입시 정보</div> -->
		<!--   		</div> -->
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<!-- 게시글 박스 -->
			<div class="detail-wrapper">
				<div class="boardEtcBtn" id="boardEtcBtn">...</div>
				<div class="boardEtcContainer" data-board-id="${boardVO.boardId }">

					<c:choose>
						<c:when test="${memId == boardVO.memId }">

							<div class="boardEtcActionBtn" id="boardModifyBtn">
								<span>수정</span>
							</div>
							<hr />
							<div class="boardEtcActionBtn" id="boardDeleteBtn">
								<span>삭제</span>
							</div>
						</c:when>
						<c:otherwise>
							<div class="boardEtcActionBtn" id="boardReportBtn">
								<span>신고</span>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<!-- 1) 제목 + 프로필 + 메타 -->
				<div class="post-header">
					<h1 class="post-title">${boardVO.boardTitle}</h1>
					<div class="author-meta">
						<div class="profile-wrapper user-profile">
							<img class="profile-img" src="<c:out value="${not empty memVO.profileFilePath ? memVO.profileFilePath : '/images/defaultProfileImg.png' }"/>" alt="프로필" /> <img class="badge-img" src="<c:out value="${not empty memVO.badgeFilePath ? memVO.badgeFilePath : '/images/defaultBorderImg.png' }"/>" alt="테두리" />
							<c:if test="${memVO.subFilePath != null }">
								<img class="effect-img sparkle" src="${memVO.subFilePath }" alt="테두리" />
							</c:if>
						</div>
						<span class="author-nickname">${memVO.memNickname}</span>
					</div>
					<div class="post-meta">
						<span class="meta-item">
							작성일:
							<fmt:formatDate value="${boardVO.boardCreatedAt}" pattern="yyyy. MM. dd" />
						</span>
						<span class="meta-item">조회수: ${boardVO.boardCnt}</span>
					</div>
				</div>

				<!-- 2) 핵심 정보 그리드 -->
				<div>${boardVO.boardContent}</div>
			</div>
		</div>
		<!-- 여기까지 게시글 끝 -->

		<!-- 댓글 입력창 -->
		<form action="/prg/std/createStdReply.do" method="post" class="comment-form">
			<input type="hidden" name="boardId" value="${boardVO.boardId}" />
			<textarea id="replyContent" name="replyContent" maxlength="300" placeholder="댓글을 입력하세요."></textarea>
			<div class="comment-footer">
				<span class="char-count" id="char-count">0 / 300</span>
				<button type="submit" class="btn-submit">등록</button>
			</div>
		</form>

		<!-- 댓글 리스트 -->
		<div class="comment-section">
			<c:forEach var="reply" items="${replyVO}">
				<div class="reply-box" id="reply-${boardVO.boardId}-${reply.replyId }" data-reply-mem="${reply.memId }">
					<span class="etcBtn">…</span>
					<div class="etc-container">
						<c:choose>
							<c:when test="${reply.memId == memId }">
								<div class="etc-act-btn">수정</div>
								<hr />
								<div class="etc-act-btn">삭제</div>
							</c:when>
							<c:otherwise>
								<div class="etc-act-btn">신고</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="reply-profile">
						<div class="profile-wrapper user-profile">
							<img class="profile-img" src="<c:out value="${not empty reply.fileProfileStr ? reply.fileProfileStr : '/images/defaultProfileImg.png' }"/>" alt="profile" /> <img class="badge-img" src="<c:out value="${not empty reply.fileBadgeStr ? reply.fileBadgeStr : '/images/defaultBorderImg.png' }"/>" alt="badge" />
							<c:if test="${reply.fileSubStr != null }">
								<img class="effect-img sparkle" src="${reply.fileSubStr }" alt="테두리" />
							</c:if>
						</div>
						<div class="writer-info">
							<div class="reply-nickname">${reply.memNickname}</div>
							<div class="reply-date">
								<fmt:formatDate pattern="yyyy. MM. dd.  HH:mm" value="${reply.replyCreatedAt}" />
							</div>
						</div>
					</div>
					<div class="reply-content">${reply.replyContent }</div>
					<div>
						<button class="reply-child-btn" id="reply-${reply.replyId }">답글</button>
						<!-- 						토글시킬 답글버튼 id:reply-댓글번호 -->
						<span class="child-count">
							<c:if test="${reply.childCount > 0 }"> ${reply.childCount }</c:if>
						</span>
					</div>
				</div>
				<div class="reply-child-container" data-parent-id="${reply.replyId }">
					<c:forEach var="child" items="${reply.childReplyVOList}">
						<div class="reply-box reply-child" data-reply-mem="${child.memId}" id="reply-${child.boardId}-${child.replyId }">
							<span class="etcBtn">…</span>
							<div class="etc-container">
								<c:choose>
									<c:when test="${child.memId == memId }">
										<div class="etc-act-btn">수정</div>
										<hr />
										<div class="etc-act-btn">삭제</div>
									</c:when>
									<c:otherwise>
										<div class="etc-act-btn">신고</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="reply-profile">
								<div class="profile-wrapper user-profile">
									<img class="profile-img" src="<c:out value="${not empty child.fileProfileStr ? child.fileProfileStr : '/images/defaultProfileImg.png' }"/>" /> <img class="badge-img" src="<c:out value="${not empty child.fileBadgeStr ? child.fileBadgeStr : '/images/defaultBorderImg.png' }"/>" />
									<c:if test="${reply.fileSubStr != null }">
										<img class="effect-img sparkle" src="${reply.fileSubStr }" alt="테두리" />
									</c:if>
								</div>
								<div class="writer-info">
									<div class="reply-nickname">${child.memNickname}</div>
									<div class="reply-date">
										<fmt:formatDate value="${child.replyCreatedAt}" />
									</div>
								</div>
							</div>
							<div class="reply-content">${child.replyContent}</div>
						</div>
					</c:forEach>
					<form action="/prg/std/createStdReply.do" method="post" class="comment-form child-form">
						<input type="hidden" name="boardId" value="${stdBoardVO.boardId}" />
						<input type="hidden" name="replyParentId" value="${reply.replyId }" />
						<textarea name="replyContent" maxlength="300" placeholder="댓글을 입력하세요."></textarea>
						<div class="comment-footer">
							<span class="char-count">0 / 300</span>
							<button type="submit" class="btn-submit">등록</button>
						</div>
						<br />
						<div class="closeReplyBtn">
							<span>답글접기 ▲</span>
						</div>
					</form>
				</div>
		</div>
		</c:forEach>
	</div>

	<!-- 목록 버튼 -->
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script src="/js/comm/peer/teen/teenDetail.js"></script>
</html>
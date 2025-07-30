<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/prg/std/stdGroupDetail.css">
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
			<div class="post-box">
			  <div class="post-header">
			    <div class="post-id-badge">
			    </div>
			    <div class="post-title">${stdBoardVO.boardTitle}</div>
			    <div class="post-meta">
			    	<div class="user-profile">
					  <img class="badge-frame" src="<c:out value="${not empty stdBoardVO.fileBadge ? stdBoardVO.fileBadge : '/images/defaultBorderImg.png' }"/>" alt="badge frame"/>
					  <img class="profile-image" src="<c:out value="${not empty stdBoardVO.fileProfile ? stdBoardVO.fileProfile : '/images/defaultProfileImg.png' }"/>" alt="user profile"/>
					</div>
			      	<span class="writer">${stdBoardVO.memNickname}</span>
			      	<span class="created"><fmt:formatDate value="${stdBoardVO.boardCreatedAt}"/></span>
			    </div>
			  </div>

			  <div class="post-content">
			  	<div class="std-group-info-box">
				  <h2 class="info-title">모집 정보</h2>
				  <ul class="info-list">
				    <li><strong>지역:</strong> ${stdBoardVO.region }</li>
				    <c:choose>
				    	<c:when test="${stdBoardVO.gender =='all' }">
				    		<li><strong>성별:</strong> 성별무관 </li>
				    	</c:when>
				    	<c:when test="${stdBoardVO.gender =='men' }">
				    		<li><strong>성별:</strong> 남자만</li>
				    	</c:when>
				    	<c:when test="${stdBoardVO.gender =='women' }">
				    		<li><strong>성별:</strong> 여자만 </li>
				    	</c:when>
				    </c:choose>
				    <li><strong>관심 분야:</strong> ${interestMap[stdBoardVO.interest] }</li>
				    <li><strong>모집 인원:</strong> ${stdBoardVO.maxPeople }명</li>
				    <li><strong>참여 인원:</strong> ${stdBoardVO.curJoinCnt }명</li>
				  </ul>

				    <div class="enter-btn-wrapper">
				    	<c:choose>
					    	<c:when test="${isEntered }">
								<button id="enterChatBtn" class="btn-enter-chat entered">
							      💬 이미 참여 중
							    </button>
					    	</c:when>
					    	<c:when test="${stdBoardVO.maxPeople <= stdBoardVO.curJoinCnt}">
 								<button id="enterChatBtn" class="btn-enter-chat disabled">
							      ❌ 입장 불가
							    </button>
					    	</c:when>
					    	<c:otherwise>
							    <button id="enterChatBtn" class="btn-enter-chat">
							      💬 채팅방 입장
							    </button>
					    	</c:otherwise>
					    </c:choose>
					</div>

				  <div class="group-description">
				    <h3 class="desc-title">소개글</h3>
				    <p>${stdBoardVO.parsedContent }</p>
				  </div>
				</div>
			  </div>
			</div>

			<!-- 댓글 리스트 -->
			<div class="comment-section">
			  <c:forEach var="reply" items="${stdBoardVO.stdReplyVOList}">
			  	<!-- 댓글 프로필 영역 -->
			  	<div class="reply-box">
				<div class="reply-profile">
				  <div class="user-profile">
				    <img class="badge-frame" src="<c:out value="${not empty reply.fileBadge ? reply.fileBadge : '/images/defaultBorderImg.png' }"/>" alt="badge"/>

				    <img class="profile-image" src="<c:out value="${not empty reply.fileProfile ? reply.fileProfile : '/images/defaultProfileImg.png' }"/>" alt="profile"/>
				  </div>
				  <div class="writer-info">
				    <div class="reply-nickname">${reply.memNickname}</div>
				    <div class="reply-date"><fmt:formatDate value="${reply.replyCreatedAt}"/></div>
				  </div>
				</div>
				  <div class="reply-content">${reply.replyContent }</div>
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

			<!-- 댓글 입력창 -->
			<form action="/prg/std/createStdReply.do" method="post" class="comment-form">
			  <input type="hidden" name="boardId" value="${stdBoardVO.boardId}" />
			  <textarea id="replyContent" name="replyContent" maxlength="300" placeholder="댓글을 입력하세요."></textarea>
			  <div class="comment-footer">
			    <span class="char-count" id="char-count">0 / 300</span>
			    <button type="submit" class="btn-submit">등록</button>
			  </div>
			</form>

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
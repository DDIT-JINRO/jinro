<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/comm/peer/teen/teenList.css">
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
		<div class="tab-container" id="tabs">
			<a class="tab" href="/comm/peer/teen/teenList.do">청소년 게시판</a>
			<a class="tab active" href="/comm/peer/youth/youthList.do">청년 게시판</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
		<div class="teenListTop">
		<p class="textmessage">또래 게시판은 연령 기준에 따라 이용이 제한될 수 있습니다.</p>
		<button id=btnWrite>글작성</button>
		</div>
			<form method="get" action="/comm/peer/youth/youthList.do">
				<div class="com-default-search">
					<div class="com-select-wrapper">
						<select name="status" class="com-status-filter">
							<option value="3">전체</option>
							<option value="1">제목</option>
							<option value="2">내용</option>
						</select>
						<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                			<path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
            			</svg>
					</div>
					<input type="search" class="search-input" name="keyword" placeholder="청소년 게시판 내에서 검색">
					<button class="com-search-btn" type="submit">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
                			<path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
           				</svg>
					</button>
				</div>
			</form>
			<div class="group-card-top">
				<div class="group-no">번호</div>
				<div class="group-title">제목</div>
				<div class="group-meta-top">
					<div class="likeCnt">좋아요</div>
					<div class="writer">작성자</div>
					<div class="writeAt">작성일</div>
					<div class="cnt">조회수</div>
				</div>
			</div>
			<c:forEach var="commBoardVO" varStatus="stat" items="${articlePage.content}">
				<div class="teenList-list">
					<div class="group-card" data-tbd-id="${commBoardVO.boardId}">
						<div class="group-info">
							<div class="group-no">${stat.count }</div>
							<div class="group-title-list">${commBoardVO.boardTitle}</div>
						</div>
						<div class="group-meta">
							<div id="likeCnt">${commBoardVO.boardLikeCnt}</div>
							<div id="writer">${commBoardVO.memNickname}</div>
							<div id="writeAt">
								<fmt:formatDate value="${commBoardVO.boardUpdatedAt}" pattern="yyyy. MM. dd" />
							</div>
							<div id="cnt">${commBoardVO.boardCnt}</div>
						</div>
					</div>
				</div>
			</c:forEach>
			<ul class="pagination">
				<li>
					<a href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${param.keyword}&status=${param.status}" class="
							<c:if test='${articlePage.startPage < 6}'>
								disabled
							</c:if>"> ← Previous </a>
				</li>
				<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
					<li>
						<a href="${articlePage.url}?currentPage=${pNo}&keyword=${param.keyword}&status=${param.status}" class="page-num
								<c:if test='${pNo == articlePage.currentPage}'>
									active
								</c:if>"> ${pNo} </a>
					</li>
				</c:forEach>

				<li>
					<a href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${param.keyword}&status=${param.status}" class="
							<c:if test='${articlePage.endPage >= articlePage.totalPages}'>
								disabled
							</c:if>"> Next → </a>
				</li>
			</ul>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script src="/js/comm/peer/youth/youthList.js">
	// 스크립트 작성 해주시면 됩니다.
</script>
</html>
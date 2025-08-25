<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/comm/peer/teen/teenList.css">
<link rel="stylesheet" href="/css/cdp/rsm/rsmb/resumeBoardList.css">
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

<div class="breadcrumb-container-space">
	<nav class="breadcrumb-container" aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item">
				<a href="/">
					<i class="fa-solid fa-house"></i> 홈
				</a>
			</li>
			<li class="breadcrumb-item">
				<a href="/comm/peer/teen/teenList.do">커뮤니티</a>
			</li>
			<li class="breadcrumb-item active">
				<a href="/comm/peer/teen/teenList.do">또래 게시판</a>
			</li>
		</ol>
	</nav>
</div>

<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab" href="/comm/peer/teen/teenList.do">청소년 게시판</a>
			<a class="tab active" href="/comm/peer/youth/youthList.do">청년 게시판</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<form method="get" action="/comm/peer/youth/youthList.do" class="search-filter__form">
				<div class="search-filter__bar">
					<div class="search-filter__select-wrapper">
						<select name="status" class="search-filter__select">
							<option value="3">전체</option>
							<option value="1">제목</option>
							<option value="2">내용</option>
						</select>
						<svg class="search-filter__select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                			<path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
            			</svg>
					</div>
					
					<div class="search-filter__input-wrapper">
						<input type="search" class="search-filter__input" name="keyword" placeholder="청년 게시판 내에서 검색" value="${param.keyword}">
						<button class="search-filter__button" type="submit">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
	                			<path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
	           				</svg>
						</button>
					</div>
				</div>
			</form>
			<p class="content-list__total-count">또래 게시판은 연령 기준에 따라 이용이 제한될 수 있습니다.</p>
			<div class="content-list">
				<div class="content-list__header">
					<span class="content-list__col content-list__col--no">번호</span>
					<span class="content-list__col content-list__col--title">제목</span>
					<span class="content-list__col content-list__col--agency">좋아요</span>
					<span class="content-list__col content-list__col--agency">작성자</span>
					<span class="content-list__col content-list__col--date">작성일</span>
					<span class="content-list__col content-list__col--agency">조회수</span>
				</div>
				
				<c:forEach var="commBoardVO" varStatus="stat" items="${articlePage.content}">
					<div class="content-list__item" data-tbd-id="${commBoardVO.boardId}">
						<div class="content-list__col content-list__col--no" data-label="번호">
							<span class="badge--number">${commBoardVO.boardId}</span>
						</div>
						<div class="content-list__col content-list__col--title" data-label="제목">
							<h3 class="content-list__title">${commBoardVO.boardTitle}</h3>
						</div>
						<div class="content-list__col content-list__col--agency" data-label="좋아요">${commBoardVO.boardLikeCnt}</div>
						<div class="content-list__col content-list__col--agency" data-label="작성자">${commBoardVO.memNickname}</div>
						<div class="content-list__col content-list__col--date" data-label="작성일">
							<fmt:formatDate value="${commBoardVO.boardUpdatedAt}" pattern="yyyy.MM.dd" />
						</div>
						<div class="content-list__col content-list__col--agency" data-label="조회수">${commBoardVO.boardCnt}</div>
					</div>
				</c:forEach>
				<c:if test="${empty articlePage.content}">
					<p class="content-list__no-results">게시글이 없습니다.</p>
				</c:if>
			</div>
			
			<div class="action-bar">
				<button class="action-bar__button" id="btnWrite">글작성</button>
			</div>
			
			<div class="pagination">
				<c:url var="prevUrl" value="${articlePage.url}">
					<c:param name="currentPage" value="${articlePage.startPage - 5}" />
					<c:if test="${not empty param.keyword}">
						<c:param name="keyword" value="${param.keyword}" />
					</c:if>
					<c:if test="${not empty param.status}">
						<c:param name="status" value="${param.status}" />
					</c:if>
				</c:url>
				<a href="${prevUrl}" class="pagination__link ${articlePage.startPage < 6 ? 'pagination__link--disabled' : ''}"> ← Previous </a>

				<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
					<c:url var="pageUrl" value="${articlePage.url}">
						<c:param name="currentPage" value="${pNo}" />
						<c:if test="${not empty param.keyword}">
							<c:param name="keyword" value="${param.keyword}" />
						</c:if>
						<c:if test="${not empty param.status}">
							<c:param name="status" value="${param.status}" />
						</c:if>
					</c:url>
					<a href="${pageUrl}" class="pagination__link ${pNo == articlePage.currentPage ? 'pagination__link--active' : ''}"> ${pNo} </a>
				</c:forEach>

				<c:url var="nextUrl" value="${articlePage.url}">
					<c:param name="currentPage" value="${articlePage.startPage + 5}" />
					<c:if test="${not empty param.keyword}">
						<c:param name="keyword" value="${param.keyword}" />
					</c:if>
					<c:if test="${not empty param.status}">
						<c:param name="status" value="${param.status}" />
					</c:if>
				</c:url>
				<a href="${nextUrl}" class="pagination__link ${articlePage.endPage >= articlePage.totalPages ? 'pagination__link--disabled' : ''}"> Next → </a>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script src="/js/comm/peer/youth/youthList.js">
	// 스크립트 작성 해주시면 됩니다.
</script>
</html>
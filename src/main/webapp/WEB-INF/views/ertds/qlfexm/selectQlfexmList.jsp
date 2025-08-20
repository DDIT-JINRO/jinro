<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/qlfexm/selectQlfexmList.css">
<section class="channel">
	<div class="channel-title">
		<div class="channel-title-text">진학 정보</div>
	</div>
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="tab-container" id="tabs">
			<h3 class="page-title-bar__title">검정고시</h3>
		</div>

		<div class="public-wrapper-main">
			<form method="get" action="/ertds/qlfexm/selectQlfexmList.do" class="search-filter__form">
				<div class="search-filter__bar">
					<div class="search-filter__input-wrapper">
						<input class="search-filter__input" type="search" name="keyword" placeholder="검정고시 내에서 검색" value="${param.keyword}">
						<button class="search-filter__button" type="submit">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
								<path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" /></svg>
						</button>
					</div>
				</div>
			</form>
			<p class="content-list__total-count">총 ${getTotal}건</p>

			<div class="content-list">
				<div class="content-list__header">
					<span class="content-list__col content-list__col--no">번호</span>
					<span class="content-list__col content-list__col--title">제목</span>
					<span class="content-list__col content-list__col--agency">교육기관</span>
					<span class="content-list__col content-list__col--date">작성일</span>
				</div>

				<c:forEach var="item" items="${articlePage.content}">
					<div class="content-list__item" data-exam-id="${item.examId}">
						<div class="content-list__col content-list__col--no" data-label="번호">
							<span class="badge--number">${item.examId}</span>
						</div>
						<div class="content-list__col content-list__col--title" data-label="제목">
							<h3 class="content-list__title">${item.examTitle}</h3>
						</div>
						<div class="content-list__col content-list__col--agency" data-label="교육기관">${item.examAreaCode}</div>
						<div class="content-list__col content-list__col--date" data-label="작성일">
							<fmt:formatDate value="${item.examNotiDate}" pattern="yyyy.MM.dd" />
						</div>
					</div>
				</c:forEach>
				<c:if test="${empty articlePage.content}">
					<p class="content-list__no-results">검정고시 정보가 없습니다.</p>
				</c:if>
			</div>

			<div class="pagination">
				<c:url var="prevUrl" value="${articlePage.url}">
					<c:param name="currentPage" value="${articlePage.startPage - 5}" />
					<c:if test="${not empty param.keyword}">
						<c:param name="keyword" value="${param.keyword}" />
					</c:if>
				</c:url>
				<a href="${prevUrl}" class="pagination__link ${articlePage.startPage < 6 ? 'pagination__link--disabled' : ''}"> ← Previous </a>

				<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
					<c:url var="pageUrl" value="${articlePage.url}">
						<c:param name="currentPage" value="${pNo}" />
						<c:if test="${not empty param.keyword}">
							<c:param name="keyword" value="${param.keyword}" />
						</c:if>
					</c:url>
					<a href="${pageUrl}" class="pagination__link ${pNo == articlePage.currentPage ? 'pagination__link--active' : ''}"> ${pNo} </a>
				</c:forEach>

				<c:url var="nextUrl" value="${articlePage.url}">
					<c:param name="currentPage" value="${articlePage.startPage + 5}" />
					<c:if test="${not empty param.keyword}">
						<c:param name="keyword" value="${param.keyword}" />
					</c:if>
				</c:url>
				<a href="${nextUrl}" class="pagination__link ${articlePage.endPage >= articlePage.totalPages ? 'pagination__link--disabled' : ''}"> Next → </a>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script src="/js/ertds/qlfexm/selectQlfexmList.js"></script>
</body>
</html>

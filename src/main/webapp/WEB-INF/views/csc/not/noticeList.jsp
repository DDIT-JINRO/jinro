<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<!-- 스타일 여기 적어주시면 가능 -->
<link rel="stylesheet" href="/css/csc/not/notice.css">

<section class="channel">
	<!-- 여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">고객센터</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn">
			<a href="/csc/not/noticeList.do">공지사항</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/csc/faq/faqList.do">FAQ</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/csc/inq/inqryList.do">1:1문의</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="tab-container" id="tabs">
			<h3 class="page-title-bar__title">공지사항</h3>
		</div>

		<div class="public-wrapper-main">
			<form method="get" action="/csc/not/noticeList.do" class="search-filter__form">
				<div class="search-filter__bar">
					<div class="search-filter__input-wrapper">
						<input class="search-filter__input" type="search" name="keyword" placeholder="공지사항 내에서 검색" value="${param.keyword}">
						<button class="search-filter__button" type="submit">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
								<path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" /></svg>
						</button>
					</div>
				</div>
			</form>
			
			<div class="content-list">
				<div class="content-list__header">
					<span class="content-list__col content-list__col--no">번호</span>
					<span class="content-list__col content-list__col--title">제목</span>
					<span class="content-list__col content-list__col--count">조회수</span>
					<span class="content-list__col content-list__col--date">작성일</span>
				</div>

				<c:forEach var="notice" items="${getList}">
					<div class="content-list__item" data-notice-id="${notice.noticeId}">
						<div class="content-list__col content-list__col--no" data-label="번호">
							<span class="badge--number">${notice.noticeId}</span>
						</div>
						<div class="content-list__col content-list__col--title" data-label="제목">
							<h3 class="content-list__title">${notice.noticeTitle}</h3>
						</div>
						<div class="content-list__col content-list__col--count" data-label="조회수">${notice.noticeCnt}</div>
						<div class="content-list__col content-list__col--date" data-label="작성일">
							<fmt:formatDate value="${notice.noticeCreatedAt}" pattern="yyyy. M. d." />
						</div>
					</div>
				</c:forEach>
				<c:if test="${empty getList}">
					<p class="content-list__no-results">공지사항이 없습니다.</p>
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
<!-- js 파일 -->
<script src="/js/csc/not/noticeList.js"></script>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>

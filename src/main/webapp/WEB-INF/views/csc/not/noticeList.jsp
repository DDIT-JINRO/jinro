<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<form method="get" action="/csc/not/noticeList.do">
				<div class="com-default-search">
					<input type="search" name="keyword" placeholder="공지사항 내에서 검색">
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
					<div id="cnt">조회수</div>
					<div id="writeAt">작성일</div>
				</div>
			</div>
			<c:forEach var="notice"  varStatus="stat" items="${getList}">
				<div class="teenList-list">
					<div class="group-card" data-tbd-id="${notice.noticeId}">
						<div class="group-info">
							<div class="group-no">${notice.noticeId}</div>
							<div class="group-title-list">
								<a href="/csc/not/noticeDetail.do?noticeId=${notice.noticeId}">${notice.noticeTitle}</a>
							</div>
						</div>
						<div class="group-meta">
							<div id="cnt">${notice.noticeCnt}</div>
							<div id="writeAt">
								<fmt:formatDate value="${notice.noticeCreatedAt}" pattern="yyyy. MM. dd" />
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
				<ul class="pagination">
					<!-- Previous -->
					<li><a
						href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${param.keyword}&gubun=${param.gubun}"
						class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>">
							← Previous </a></li>
			
					<!-- Page Numbers -->
					<c:forEach var="pNo" begin="${articlePage.startPage}"
						end="${articlePage.endPage}">
						<li><a href="${articlePage.url}?currentPage=${pNo}&keyword=${param.keyword}&gubun=${param.gubun}"
							class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>">
								${pNo} </a></li>
					</c:forEach>
			
					<!-- Next -->
					<li><a
						href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${param.keyword}&gubun=${param.gubun}"
						class="<c:if test='${articlePage.endPage >= articlePage.totalPages}'>disabled</c:if>">
							Next → </a></li>
				</ul>
		</div>
	</div>
</div>
<!-- js 파일 -->
<script src="/js/csc/not/noticeList.js"></script>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>

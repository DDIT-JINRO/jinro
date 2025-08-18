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

		<div class="public-wrapper-main">
			<form method="get" action="/ertds/qlfexm/selectQlfexmList.do">
				<div class="com-default-search">
					<input type="search" name="keyword" placeholder="검정고시 내에서 검색">
					<button class="com-search-btn" type="submit">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
				                <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
				        </svg>
					</button>
				</div>
			</form>
			<p id="getAllNotice">총 ${getTotal}건</p>
			<div class="result-list-wrapper">
				<div class="list-header">
					<div class="header-item" style="padding-left: 20px;">번호</div>
					<div class="header-item">제목</div>
					<div class="header-item">교육기관</div>
					<div class="header-item">작성일</div>

				</div>
				<div class="data-list">
					<c:forEach var="item" items="${articlePage.content}">
						<div class="data-item" data-exam-id="${item.examId}">
							<div class="data-no">${item.examId}</div>
							<div>${item.examTitle}</div>
							<div style="padding-left: 20px; text-align: center;">${item.examAreaCode}</div>
							<div style="text-align: center;">
								<fmt:formatDate value="${item.examNotiDate}" pattern="yyyy.MM.dd" />
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="card-footer clearfix">
				<ul class="pagination">
					<li>
						<a href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${param.keyword}&gubun=${param.gubun}" class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>"> ← Previous </a>
					</li>

					<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
						<li>
							<a href="${articlePage.url}?currentPage=${pNo}&keyword=${param.keyword}&gubun=${param.gubun}" class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>"> ${pNo} </a>
						</li>
					</c:forEach>

					<li>
						<a href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${param.keyword}&gubun=${param.gubun}" class="<c:if test='${articlePage.endPage >= articlePage.totalPages}'>disabled</c:if>"> Next → </a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script src="/js/ertds/qlfexm/selectQlfexmList.js"></script>
</body>
</html>

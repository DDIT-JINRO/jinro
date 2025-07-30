<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<!-- 스타일 여기 적어주시면 가능 -->
<link rel="stylesheet" href="/css/csc/notice.css">

<section class="channel">
	<!-- 여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진로 탐색</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn">
			<a href="/csc/noticeList.do">공지사항</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/csc/faqList.do">FAQ</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/csc/inqryList.do">1:1문의</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">

		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<!-- 검색 기능 -->
			<form class="search-box" action="/csc/not/noticeList.do" method="get">

				<input type="text" name="keyword" placeholder="검색어를 입력하세요..." value="${param.keyword}">
				<button type="submit">검색</button>
			</form>
		<p id="getAllNotice">총 ${getAllNotice}건</p>
			<table>
				<thead>
					<tr>
						<th >번호</th>
						<th>제목</th>
						<th>조회수</th>
						<!--  작성일 MM/DD/HH/MM/ 형식으로 -->
						<th>작성일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="notice" items="${getList}">
						<tr>
							<td class="notice-no"> ${notice.noticeId}</td>
							<td style="text-align: left;">
								<a href="/csc/not/noticeDetail.do?no=${notice.noticeId}">${notice.noticeTitle}</a></td>
							<td>${notice.noticeCnt}</td>
							<td><fmt:formatDate value="${notice.noticeCreatedAt}" pattern="MM.dd" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="card-footer clearfix">
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
</div>
<!-- js 파일 -->
<script src="/js/csc/noticeList.js"></script>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>

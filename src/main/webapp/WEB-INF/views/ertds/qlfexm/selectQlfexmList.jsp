<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/qlfexm/selectQlfexmList.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진학 정보</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a></div>
		<div class="channel-sub-section-itemIn"><a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">

		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<!-- 검색 기능 -->
			<form method="get" action="/ertds/qlfexm/selectQlfexmList.do">
				<div class="com-default-search">
					<input type="search" name="keyword" placeholder="공지사항 내에서 검색">
					<button class="com-search-btn" type="submit">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
				                <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
				        </svg>
					</button>
				</div>
			</form>
			<p id="getAllNotice">총 ${getTotal}건</p>
			<table>
				<colgroup>
					<col style="width: 18%;">
					<col style="width: 42%;">
					<col style="width: 20%;">
					<col style="width: 20%;">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>교육기관</th>
						<!--  작성일 MM/DD/HH/MM/ 형식으로 -->
						<th>작성일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${getList}">
						<tr>
							<td><div class="notice-no"> ${item.examId}</div></td>
							<td style="text-align: left;">
								<a href="/ertds/qlfexm/selectQlfexmDetail.do?examId=${item.examId}">${item.examTitle}</a></td>
							<td>${item.examAreaCode}</td>
							<td><fmt:formatDate value="${item.examNotiDate}" pattern="yyyy.MM.dd" /></td>
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
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
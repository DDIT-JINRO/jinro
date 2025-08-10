<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/empt/ivfb/interviewFeedback.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">취업 정보</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/empt/ema/employmentAdvertisement.do">채용공고</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/empt/enp/enterprisePosting.do">기업정보</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/empt/ivfb/interViewFeedback.do">면접후기</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/empt/cte/careerTechnicalEducation.do">직업교육</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="teenListTop">
				<button id=btnWrite>면접 후기 공유하기</button>
			</div>
			<form method="get" action="${articlePage.url}">
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
					<input type="search" class="search-input" name="keyword" placeholder="면접 후기 게시판 내에서 검색">
					<button class="com-search-btn" type="submit">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
                			<path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
           				</svg>
					</button>
				</div>
			</form>
			<div class="group-card-top">
				<div class="group-title">기업명</div>
				<div class="group-meta-top">
					<div id="writer">작성자</div>
					<div id="writeAt">작성일</div>
					<div id="rate">평점</div>
				</div>
			</div>
			<div class="teenList-list">
				<div class="group-card" data-tbd-id="">
					<div class="group-info">
						<div class="group-title-list">기업명</div>
					</div>
					<div class="group-meta">
						<div id="writer">작성자</div>
						<div id="writeAt">0000. 00. 00.</div>
						<div id="cnt">0</div>
					</div>
				</div>
			</div>

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
<script type="text/javascript" src="/js/empt/ivfb/interviewFeedback.js"></script>
</body>
</html>
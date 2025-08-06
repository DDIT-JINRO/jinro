<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/empt/ema/employmentAdvertisement.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">취업 정보</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/empt/ema/employmentAdvertisement.do">채용공고</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/empt/enp/enterprisePosting.do">기업정보</a></div>
		<div class="channel-sub-section-item"><a href="/empt/ivfb/interViewFeedback.do">면접후기</a></div>
		<div class="channel-sub-section-item"><a href="/empt/cte/careerTechnicalEducation.do">직업교육</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
  		<div class="public-wrapper-main">
			<form method="get" action="/empt/ema/employmentAdvertisement.do">
				<div class="com-default-search">
					<div class="com-select-wrapper">
						<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg"
							viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd"
								d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z"
								clip-rule="evenodd" />
            </svg>
					</div>
					<input type="search" name="keyword" placeholder="제목 or 기업명 검색">
					<button class="com-search-btn" type="submit">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
							fill="currentColor" width="20" height="20">
                <path fill-rule="evenodd"
								d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z"
								clip-rule="evenodd" />
            </svg>
					</button>
				</div>

				<div class="com-accordion-filter">
					<button type="button" class="com-accordion-header"
						id="com-accordion-toggle">
						<span>필터</span> <span class="com-arrow-icon">▲</span>
					</button>
					<div class="com-accordion-panel" id="com-accordion-panel">
						<div class="com-filter-section">
							<label class="com-filter-title">직무</label>
							<div class="com-filter-options">
								<c:forEach var="hireClass" items="${CodeVOHireClassList}">
									<label class="com-filter-item"> <input type="checkbox"
										name="hireClassCodeNames" value="${hireClass.ccId}"> <span>${hireClass.ccName}</span>
									</label>
								</c:forEach>
							</div>

							<label class="com-filter-title">지역</label>
							<div class="com-filter-options">
								<c:forEach var="region" items="${CodeVORegionList}">
									<label class="com-filter-item">
										<input type="checkbox" name="regions" value="${region.ccId}">
										<span>${region.ccEtc}</span>
									</label>
								</c:forEach>
							</div>

							<label class="com-filter-title">채용유형</label>
							<div class="com-filter-options">
								<c:forEach var="hireType" items="${CodeVOHireTypeList}">
									<label class="com-filter-item">
										<input type="checkbox" name="hireTypeNames" value="${hireType.ccId}">
										<span>${hireType.ccName}</span>
									</label>
								</c:forEach>
							</div>
						</div>
						<div class="com-filter-section">
							<div class="com-button-container">
								<label class="com-filter-title">선택된 필터</label>
								<button type="button" class="com-filter-reset-btn">초기화</button>
							</div>
							<div class="com-selected-filters"></div>
						</div>
						<button type="submit" class="com-submit-search-btn">검색</button>
					</div>
				</div>
			</form>
						<p>총 ${articlePage.total}건</p>

			<div class="hire-list">
				<div class="accordion-header">
					<div style="flex: 1;">공고명</div>
					<div style="flex: 1;">기업명</div>
					<div style="flex: 1;">고용형태</div>
					<div style="width: 80px;">북마크</div>
					<div style="width: 20px;"></div>
					<%-- 펼치기/접기 아이콘 자리 --%>
				</div>

				<c:forEach var="hire" items="${articlePage.content}"
					varStatus="status">
					<div class="accordion-item">
						<div class="accordion-header">
							<div class="hire-info-item" style="flex: 1;">
								${hire.hireTitle}
							</div>
							<div class="hire-info-item" style="flex: 1;">${hire.cpName}</div>
							<div class="hire-info-item" style="flex: 1;">${hire.hireTypename}</div>
							<div class="hire-info-item" style="width: 80px;">
								<div class="item-action">
									<c:set var="isBookmarked" value="false" />

									<c:forEach var="bookmark" items="${bookMarkVOList}">
										<c:if test="${hire.hireId eq bookmark.bmTargetId}">
											<c:set var="isBookmarked" value="true" />
										</c:if>
									</c:forEach>

									<button class="bookmark-btn ${isBookmarked ? 'active' : ''}"
										data-category-id="G03003"
										data-target-id="${fn:escapeXml(hire.hireId)}">
										<span class="icon-active"> <img
											src="/images/bookmark-btn-active.png" alt="활성 북마크">
										</span> <span class="icon-inactive"> <img
											src="/images/bookmark-btn-inactive.png" alt="비활성 북마크">
										</span>
									</button>
								</div>
							</div>
							<div class="hire-info-item" style="width: 20px;">
								<span class="toggle-icon">+</span>
							</div>
						</div>
						<div class="accordion-content">
							<div class="hire-description-section">
								<h4>채용 내용</h4>
								<p>${hire.hireDescription}</p>
							</div>
							<div class="hire-url-section">
								<h4>채용 홈페이지</h4>
								<a href="${hire.hireUrl}" target="_blank"
											class="homepage-link">홈페이지로 이동하기</a>
							</div>
							<div class="hire-date-section">
								<h4>채용 기간</h4>
								<div class="date-container">
									<label>공고 시작일 : <fmt:formatDate
											value="${hire.hireStartDate}" pattern="yyyy. MM. dd" />
									</label> <label> 공고 마감일 : <span
										class="deadline-text
							                <c:choose>
							                    <c:when test="${hire.dday >= 0 and hire.dday <= 3}">deadline-imminent</c:when>
							                    <c:when test="${hire.dday < 0}">deadline-passed</c:when>
							                </c:choose>">
											<fmt:formatDate value="${hire.hireEndDate}"
												pattern="yyyy. MM. dd" /> <%-- D-day 표시 --%> <c:choose>
												<c:when test="${hire.dday >= 0}"> (D-${hire.dday})</c:when>
												<c:when test="${hire.dday < 0}"> (마감됨)</c:when>
											</c:choose>
									</span>
									</label>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

			<!-- 페이징 -->
			<div class="card-footer clearfix">
				<ul class="pagination">
					<!-- Previous -->
					<li><a
						href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${articlePage.keyword}
						<c:forEach var='hireClassCodeNames' items='${paramValues.hireClassCodeNames}'>&hireClassCodeNames=${hireClassCodeNames}</c:forEach>
						<c:forEach var='regions' items='${paramValues.regions}'>&regions=${regions}</c:forEach>
						<c:forEach var='hireTypeNames' items='${paramValues.hireTypeNames}'>&hireTypeNames=${hireTypeNames}</c:forEach>"
						class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>">
							← Previous </a></li>

					<!-- Page Numbers -->
					<c:forEach var="pNo" begin="${articlePage.startPage}"
						end="${articlePage.endPage}">
						<li><a
							href="${articlePage.url}?currentPage=${pNo}&keyword=${articlePage.keyword}
							<c:forEach var='hireClassCodeNames' items='${paramValues.hireClassCodeNames}'>&hireClassCodeNames=${hireClassCodeNames}</c:forEach>
							<c:forEach var='regions' items='${paramValues.regions}'>&regions=${regions}</c:forEach>
							<c:forEach var='hireTypeNames' items='${paramValues.hireTypeNames}'>&hireTypeNames=${hireTypeNames}</c:forEach>"
								class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>">
								${pNo} </a></li>
					</c:forEach>

					<!-- Next -->
					<li><a
						href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${articlePage.keyword}
						<c:forEach var='hireClassCodeNames' items='${paramValues.hireClassCodeNames}'>&hireClassCodeNames=${hireClassCodeNames}</c:forEach>
						<c:forEach var='regions' items='${paramValues.regions}'>&regions=${regions}</c:forEach>
						<c:forEach var='hireTypeNames' items='${paramValues.hireTypeNames}'>&hireTypeNames=${hireTypeNames}</c:forEach>"		
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
<script type="text/javascript" src="/js/empt/ema/employmentAdvertisement.js"></script>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/empt/enp/enterprisePosting.css">
<style>
</style>
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
		<div class="channel-sub-section-itemIn">
			<a href="/empt/enp/enterprisePosting.do">기업정보</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/empt/ivfb/interViewFeedback.do">면접후기</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/empt/cte/careerTechnicalEducation.do">직업교육</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="public-wrapper-main">
			<form method="get" action="/empt/enp/enterprisePosting.do">
				<div class="com-default-search">
					<div class="com-select-wrapper">
						<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg"
							viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd"
								d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z"
								clip-rule="evenodd" />
            </svg>
					</div>
					<input type="search" name="keyword" placeholder="기업명으로 검색">
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
							<label class="com-filter-title">기업 규모</label>
							<div class="com-filter-options">
								<c:forEach var="scale" items="${codeVOCompanyScaleList}">
									<label class="com-filter-item"> <input type="checkbox"
										name="scaleId" value="${scale.ccId}"> <span>${scale.ccName}</span>
									</label>
								</c:forEach>
							</div>

							<label class="com-filter-title">지역</label>
							<div class="com-filter-options">
								<c:forEach var="region" items="${CodeVORegionList}">
									<label class="com-filter-item"> <%-- regionId는 CompanyVO의 필터링할 값입니다. value는 실제 데이터베이스 코드 값으로 매핑되어야 합니다. --%>
										<input type="checkbox" name="regionId" value="${region.ccId}">
										<span>${region.ccEtc}</span>
									</label>
								</c:forEach>
							</div>

							<label class="com-filter-title">채용여부</label>
							<div class="com-filter-options">
								<label class="com-filter-item"> <input type="checkbox"
									name="hiringStatus" value="Y"><span>채용 중</span>
								</label> <label class="com-filter-item"> <input type="checkbox"
									name="hiringStatus" value="N"><span>채용 없음</span>
								</label>
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

			<div class="company-list">
				<div class="accordion-header">
					<div style="flex: 1;">기업이미지</div>
					<div style="flex: 1;">기업명</div>
					<div style="flex: 1;">기업규모</div>
					<div style="flex: 1;">홈페이지</div>
					<div style="width: 80px;">북마크</div>
					<div style="width: 20px;"></div>
					<%-- 펼치기/접기 아이콘 자리 --%>
				</div>

				<c:forEach var="company" items="${articlePage.content}"
					varStatus="status">
					<div class="accordion-item">
						<div class="accordion-header">
							<div class="company-info-item" style="flex: 1;">
								<img src="${company.cpImgUrl}" alt="기업 이미지"
									class="company-image">
							</div>
							<div class="company-info-item" style="flex: 1;">${company.cpName}</div>
							<div class="company-info-item" style="flex: 1;">${company.ccName}</div>
							<div class="company-info-item" style="flex: 1;">
								<c:choose>
									<c:when test="${not empty company.cpWebsite}">
										<a href="${company.cpWebsite}" target="_blank"
											class="homepage-link">홈페이지</a>
									</c:when>
									<c:otherwise>
										<span class="no-homepage">홈페이지 없음</span>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="company-info-item" style="width: 80px;">
								<div class="item-action">
									<c:set var="isBookmarked" value="false" />

									<c:forEach var="bookmark" items="${bookMarkVOList}">
										<c:if test="${company.cpId eq bookmark.bmTargetId}">
											<c:set var="isBookmarked" value="true" />
										</c:if>
									</c:forEach>

									<button class="bookmark-btn ${isBookmarked ? 'active' : ''}"
										data-category-id="G03002"
										data-target-id="${fn:escapeXml(company.cpId)}">
										<span class="icon-active"> <img
											src="/images/bookmark-btn-active.png" alt="활성 북마크">
										</span> <span class="icon-inactive"> <img
											src="/images/bookmark-btn-inactive.png" alt="비활성 북마크">
										</span>
									</button>
								</div>
							</div>
							<div class="company-info-item" style="width: 20px;">
								<span class="toggle-icon">+</span>
							</div>
						</div>
						<div class="accordion-content">
							<div class="company-description-section">
								<h4>기업 설명</h4>
								<p>${company.cpDescription}</p>
							</div>
							<div class="company-address-section">
								<h4>기업 주소</h4>
								<p>${company.cpRegion}</p>
							</div>
							<div class="company-hiring-status-section">
								<h4>현재 채용 여부</h4>
								<c:choose>
									<c:when test="${company.cpHiringStatus eq 'Y'}">
										<p>
											<a
												href="/empt/ema/employmentAdvertisement.do?keyword=${fn:escapeXml(company.cpName)}"
												class="hiring-link"> 채용 중 (채용공고 바로가기) </a>
										</p>
									</c:when>
									<c:otherwise>
										<p>채용 없음</p>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="company-review-section">
								<h4>기업 면접 후기</h4>

								<c:set var="isReview" value="false" />

								<c:forEach var="interviewReview"
									items="${interviewReviewVOList}">
									<c:if test="${company.cpId eq interviewReview.targetId}">
										<div class="review-item">
											<div class="review-meta">
												<span><strong>
														${interviewReview.memNickname}</strong></span>
												<div class="rating-and-date">
													<span><strong class="review-rating-icon">★</strong>
														${interviewReview.irRating}</span>
												</div>
											</div>
											<p class="review-content">${interviewReview.irContent}</p>
											<p class="review-date">
												<fmt:formatDate value="${interviewReview.irCreatedAt}"
													pattern="yyyy. MM. dd" />
											</p>
										</div>
										<c:set var="isReview" value="true" />
									</c:if>
								</c:forEach>

								<c:if test="${!isReview}">
									<p class="no-review-message">등록된 면접 후기가 없습니다.</p>
								</c:if>
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
						<c:forEach var='scaleId' items='${paramValues.scaleId}'>&scaleId=${scaleId}</c:forEach>
						<c:forEach var='regionId' items='${paramValues.regionId}'>&regionId=${regionId}</c:forEach>
						&hiringStatus=${param.hiringStatus}"
						class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>">
							← Previous </a></li>

					<!-- Page Numbers -->
					<c:forEach var="pNo" begin="${articlePage.startPage}"
						end="${articlePage.endPage}">
						<li><a
							href="${articlePage.url}?currentPage=${pNo}&keyword=${articlePage.keyword}
							<c:forEach var='scaleId' items='${paramValues.scaleId}'>&scaleId=${scaleId}</c:forEach>
							<c:forEach var='regionId' items='${paramValues.regionId}'>&regionId=${regionId}</c:forEach>
							&hiringStatus=${param.hiringStatus}"
							class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>">
								${pNo} </a></li>
					</c:forEach>

					<!-- Next -->
					<li><a
						href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${articlePage.keyword}
						<c:forEach var='scaleId' items='${paramValues.scaleId}'>&scaleId=${scaleId}</c:forEach>
						<c:forEach var='regionId' items='${paramValues.regionId}'>&regionId=${regionId}</c:forEach>
						&hiringStatus=${param.hiringStatus}"
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
<script type="text/javascript" src="/js/empt/enp/enterprisePosting.js"></script>
<script>
	
</script>
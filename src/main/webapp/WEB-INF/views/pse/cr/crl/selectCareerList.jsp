<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/pse/cr/crl/selectCareerList.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel" data-error-message="${errorMessage}">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진로 탐색</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/pse/cat/careerAptitudeTest.do">진로 심리검사</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/pse/cr/crl/selectCareerList.do">직업백과</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab active" href="/pse/cr/crl/selectCareerList.do">직업 목록</a>
			<a class="tab" href="/pse/cr/crr/selectCareerRcmList.do">추천 직업</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="activity-container">
				<form method="GET" action="/pse/cr/crl/selectCareerList.do">
					<div class="com-default-search">
						<div class="com-select-wrapper">
							<select name="status" class="com-status-filter">
								<option value="all">전체</option>
								<option value="title">제목</option>
								<option value="content">내용</option>
							</select>
							<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                <path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
                            </svg>
						</div>
						<input type="search" name="keyword" placeholder="내 북마크에서 검색" value="${param.keyword}">
						<button class="com-search-btn" type="submit">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
                                <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
                            </svg>
						</button>
					</div>

					<div class="com-accordion-filter">
						<button type="button" class="com-accordion-header" id="com-accordion-toggle">
							<span>필터</span>
							<span class="com-arrow-icon">▲</span>
						</button>
						<div class="com-accordion-panel" id="com-accordion-panel">
							<div class="com-accordion-content">
								<div class="com-filter-section">
									<label class="com-filter-title">직업 대분류</label>
									<div class="com-filter-options">
										<c:forEach var="jobLcl" items="${jobLclCode}">
											<c:set var="isChecked" value="${false}" />

											<c:forEach var="submittedLcl" items="${paramValues.jobLcls}">
												<c:if test="${jobLcl.key eq submittedLcl}">
													<c:set var="isChecked" value="${true}" />
												</c:if>
											</c:forEach>

											<label class="com-filter-item">
												<input id="${jobLcl.key}" type="checkbox" name="jobLcls" value="${jobLcl.key}" ${isChecked ? 'checked' : ''} />
												<span>${jobLcl.value}</span>
											</label>
										</c:forEach>
									</div>
								</div>
								<div class="com-filter-section">
									<label class="com-filter-title">연봉</label>
									<div class="com-filter-options">
										<c:forEach var="salOption" items="<%=new String[] {\"sal1\",\"sal2\",\"sal3\",\"sal4\" }%>">
											<c:set var="salLabels" value="${{'sal1':'3000천만원 미만', 'sal2':'3천만원 이상 5천만원 미만', 'sal3':'5천만원 이상 1억원 미만', 'sal4':'1억원 이상'}}" />

											<c:set var="isSalChecked" value="${false}" />

											<c:forEach var="submittedSal" items="${paramValues.jobSals}">
												<c:if test="${salOption eq submittedSal}">
													<c:set var="isSalChecked" value="${true}" />
												</c:if>
											</c:forEach>

											<label class="com-filter-item">
												<input id="${salOption}" type="checkbox" name="jobSals" value="${salOption}" ${isSalChecked ? 'checked' : ''} />
												<span>${salLabels[salOption]}</span>
											</label>
										</c:forEach>
									</div>
								</div>
								<div class="com-filter-section">
									<div class="com-button-container">
										<label class="com-filter-title">선택된 필터</label>
										<button type="button" class="com-filter-reset-btn">초기화</button>
									</div>
									<div class="com-selected-filters">
										<c:forEach var="submittedLcl" items="${paramValues.jobLcls}">
											<span class="com-selected-filter" data-group="jobLcls" data-value="${submittedLcl}">
												직업 대분류 > ${jobLclCode[submittedLcl]}
												<button type="button" class="com-remove-filter">×</button>
											</span>
										</c:forEach>

										<c:forEach var="submittedSal" items="${paramValues.jobSals}">
											<c:set var="salLabels" value="${{'sal1':'3000천만원 미만', 'sal2':'3천만원 이상 5천만원 미만', 'sal3':'5천만원 이상 1억원 미만', 'sal4':'1억원 이상'}}" />
											<span class="com-selected-filter" data-group="jobSals" data-value="${submittedSal}">
												연봉 > ${salLabels[submittedSal]}
												<button type="button" class="com-remove-filter">×</button>
											</span>
										</c:forEach>
									</div>
								</div>
								<button type="submit" class="com-submit-search-btn">검색</button>
							</div>
						</div>
					</div>
				</form>
				<c:choose>
					<c:when test="${empty articlePage.content || articlePage.content == null }">
						<p class="no-content-message">해당 직업 목록이 없습니다.</p>
					</c:when>
					<c:otherwise>
						<div class="jobs-list">
							<c:forEach var="jobs" items="${articlePage.content}">
								<div class="jobs-item" id="${jobs.jobCode}" data-job-id="${jobs.jobCode}">
									<div class="item-content job-info">
										<div class="item-header">
											<h3 class="item-title">
												${jobs.jobName}
											</h3>
										</div>
										<p class="item-snippet">${jobs.jobMainDuty}</p>
									</div>
									<div class="item-content job-detail-wrapper">
										<div class="job-detail">
											<div class="job-img-wrapper">
												<img src="/images/jobAverageImg.png" alt="연봉 이미지">
											</div>
											<div>
												<div class="item-header">
													<h2 class="item-title item-job-detail-title">평균 연봉</h2>
												</div>
												<p class="item-snippet item-job-snippet">${jobs.averageSal}</p>
											</div>
										</div>
										<div class="job-detail">
											<div class="job-img-wrapper">
												<img src="/images/jobProspectImg.png" alt="전망 이미지">
											</div>
											<div>
												<div class="item-header">
													<h2 class="item-title item-job-detail-title">미래 전망</h2>
												</div>
												<p class="item-snippet item-job-snippet">${jobs.prospect}</p>
											</div>
										</div>
										<div class="job-detail">
											<div class="job-img-wrapper">
												<img src="/images/jobSatisImg.png" alt="만족도 이미지">
											</div>
											<div>
												<div class="item-header">
													<h2 class="item-title item-job-detail-title">만족도</h2>
												</div>
												<p class="item-snippet item-job-snippet">${jobs.jobSatis}</p>
											</div>
										</div>
									</div>
									<div class="item-action">
										<button class="bookmark-btn ${jobs.isBookmark == job.jobTargetId ? '' : 'active' }" data-category-id="G03004" data-target-id="${jobs.jobTargetId}">
											<span class="icon-active">
												<img src="/images/bookmark-btn-active.png" alt="활성 북마크" width="30" height="30">
											</span>

											<span class="icon-inactive">
												<img src="/images/bookmark-btn-inactive.png" alt="비활성 북마크" width="30" height="30">
											</span>
										</button>
										<label class="select-btn">
											<input type="checkbox" id="compare-btn${jobs.jobCode}" name="jobLcls" value="${jobs.jobCode}"
												data-job-name="${jobs.jobName}" data-job-sal="${jobs.averageSal}" data-job-prospect="${jobs.prospect}" data-job-satis="${jobs.jobSatis}"/>
											<span>
												비교
												<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" width="15" height="15">
                                				<path fill-rule="evenodd" d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.052-.143z" clip-rule="evenodd" />
                            				</svg>
											</span>
										</label>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>

				<ul class="pagination">
					<li>
						<c:url var="prevUrl" value="${articlePage.url}">
							<c:param name="currentPage" value="${articlePage.startPage - 5}" />
							<c:param name="keyword" value="${param.keyword}" />
							<c:param name="status" value="${param.status}" />
							<c:forEach var="lcl" items="${paramValues.jobLcls}">
								<c:param name="jobLcls" value="${lcl}" />
							</c:forEach>
							<c:forEach var="sal" items="${paramValues.jobSals}">
								<c:param name="jobSals" value="${sal}" />
							</c:forEach>
						</c:url>
						<a href="${prevUrl}" class="${articlePage.startPage < 6 ? 'disabled' : ''}"> ← Previous </a>
					</li>

					<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
						<li>
							<c:url var="pageUrl" value="${articlePage.url}">
								<c:param name="currentPage" value="${pNo}" />
								<c:param name="keyword" value="${param.keyword}" />
								<c:param name="status" value="${param.status}" />
								<c:forEach var="lcl" items="${paramValues.jobLcls}">
									<c:param name="jobLcls" value="${lcl}" />
								</c:forEach>
								<c:forEach var="sal" items="${paramValues.jobSals}">
									<c:param name="jobSals" value="${sal}" />
								</c:forEach>
							</c:url>
							<a href="${pageUrl}" class="page-num ${pNo == articlePage.currentPage ? 'active' : ''}"> ${pNo} </a>
						</li>
					</c:forEach>

					<li>
						<c:url var="nextUrl" value="${articlePage.url}">
							<c:param name="currentPage" value="${articlePage.startPage + 5}" />
							<c:param name="keyword" value="${param.keyword}" />
							<c:param name="status" value="${param.status}" />
							<c:forEach var="lcl" items="${paramValues.jobLcls}">
								<c:param name="jobLcls" value="${lcl}" />
							</c:forEach>
							<c:forEach var="sal" items="${paramValues.jobSals}">
								<c:param name="jobSals" value="${sal}" />
							</c:forEach>
						</c:url>
						<a href="${nextUrl}" class="${articlePage.endPage >= articlePage.totalPages ? 'disabled' : ''}"> Next → </a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>

<div class="job-compare-popup">
    <header class="popup-header">
        <div class="popup-title-group">
            <img src="/images/jobCompareImg.png" alt="직업 비교 아이콘" class="popup-logo">
            <h2 class="popup-title">직업 비교</h2>
        </div>
        <button type="button" class="btn-close-popup" aria-label="닫기">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#1E1E1E" width="28" height="28">
                <path fill-rule="evenodd" d="M5.47 5.47a.75.75 0 0 1 1.06 0L12 10.94l5.47-5.47a.75.75 0 1 1 1.06 1.06L13.06 12l5.47 5.47a.75.75 0 1 1-1.06 1.06L12 13.06l-5.47 5.47a.75.75 0 0 1-1.06-1.06L10.94 12 5.47 6.53a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
            </svg>
        </button>
    </header>

    <div class="popup-content">
        <div class="compare-list">
        </div>
    </div>

    <footer class="popup-footer">
        <button type="button" class="btn-clear-all">모두 삭제</button>
        <button type="button" class="btn-view-results">직업 비교하기</button>
    </footer>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script src="/js/pse/cr/crl/selectCareerList.js"></script>
</html>
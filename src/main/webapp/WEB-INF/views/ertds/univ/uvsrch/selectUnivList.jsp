<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/univ/uvsrch/univList.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진학 정보</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn">
			<a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab active" href="/ertds/univ/uvsrch/selectUnivList.do">대학 검색</a> <a class="tab" href="/ertds/univ/dpsrch/selectDeptList.do">학과 정보</a>
		</div>
	</div>
</div>
<div>
	<div class="public-wrapper">
		<div class="public-wrapper-main">
			<form method="get" action="/ertds/univ/uvsrch/selectUnivList.do">
				<div class="com-default-search">
					<div class="com-select-wrapper">
						<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
            </svg>
					</div>
					<input type="search" name="keyword" placeholder="대학명으로 검색" value="${param.keyword}">
					<button class="com-search-btn" type="button">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
                <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
            </svg>
					</button>
				</div>
				<div class="com-accordion-filter">
					<button type="button" class="com-accordion-header" id="com-accordion-toggle">
						<span>필터</span> <span class="com-arrow-icon">▲</span>
					</button>
					<div class="com-accordion-panel" id="com-accordion-panel">
						<div class="com-filter-section">
							<label class="com-filter-title">대학 지역</label>
							<div class="com-filter-options">
								<c:forEach var="region" items="${codeVORegionList}">
									<c:set var="isRegionChecked" value="${false}" />
									
									<c:forEach var="submittedRegion" items="${paramValues.regionIds}">
										<c:if test="${region.ccId eq submittedRegion}">
											<c:set var="isRegionChecked" value="${true}" />
										</c:if>
									</c:forEach>
									
									<label class="com-filter-item"> 
										<input type="checkbox" name="regionIds" value="${region.ccId}" ${isRegionChecked ? 'checked' : ''}> 
										<span>${region.ccEtc}</span>
									</label>
								</c:forEach>
							</div>
							<label class="com-filter-title">대학 유형</label>
							<div class="com-filter-options">
								<c:forEach var="type" items="${codeVOUniversityTypeList}">
									<c:set var="isTypeChecked" value="${false}" />
									
									<c:forEach var="submittedType" items="${paramValues.typeIds}">
										<c:if test="${type.ccId eq submittedType}">
											<c:set var="isTypeChecked" value="${true}" />
										</c:if>
									</c:forEach>
									
									<label class="com-filter-item"> 
										<input type="checkbox" name="typeIds" value="${type.ccId}" ${isTypeChecked ? 'checked' : ''}> 
										<span>${type.ccName}</span>
									</label>
								</c:forEach>
							</div>
							<label class="com-filter-title">설립 유형</label>
							<div class="com-filter-options">
								<c:forEach var="gubun" items="${codeVOUniversityGubunList}">
									<c:set var="isGubunChecked" value="${false}" />
									
									<c:forEach var="submittedGubun" items="${paramValues.gubunIds}">
										<c:if test="${gubun.ccId eq submittedGubun}">
											<c:set var="isGubunChecked" value="${true}" />
										</c:if>
									</c:forEach>
									
									<label class="com-filter-item"> 
										<input type="checkbox" name="gubunIds" value="${gubun.ccId}" ${isGubunChecked ? 'checked' : ''}> 
										<span>${gubun.ccName}</span>
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
								<!-- 선택된 지역 필터 표시 -->
								<c:forEach var="submittedRegion" items="${paramValues.regionIds}">
									<c:forEach var="region" items="${codeVORegionList}">
										<c:if test="${region.ccId eq submittedRegion}">
											<span class="com-selected-filter" data-group="regionCategory" data-value="${submittedRegion}">
												대학 지역 > ${region.ccEtc}
												<button type="button" class="com-remove-filter">×</button>
											</span>
										</c:if>
									</c:forEach>
								</c:forEach>
								
								<!-- 선택된 유형 필터 표시 -->
								<c:forEach var="submittedType" items="${paramValues.typeIds}">
									<c:forEach var="type" items="${codeVOUniversityTypeList}">
										<c:if test="${type.ccId eq submittedType}">
											<span class="com-selected-filter" data-group="typeCategory" data-value="${submittedType}">
												대학 유형 > ${type.ccName}
												<button type="button" class="com-remove-filter">×</button>
											</span>
										</c:if>
									</c:forEach>
								</c:forEach>
								
								<!-- 선택된 설립 유형 필터 표시 -->
								<c:forEach var="submittedGubun" items="${paramValues.gubunIds}">
									<c:forEach var="gubun" items="${codeVOUniversityGubunList}">
										<c:if test="${gubun.ccId eq submittedGubun}">
											<span class="com-selected-filter" data-group="gubunCategory" data-value="${submittedGubun}">
												설립 유형 > ${gubun.ccName}
												<button type="button" class="com-remove-filter">×</button>
											</span>
										</c:if>
									</c:forEach>
								</c:forEach>
							</div>
						</div>
						<button type="submit" class="com-submit-search-btn">검색</button>
					</div>
				</div>
			</form>

			<div class="company-list">
				<div class="accordion-header">
					<div style="flex: 1.8;">대학명</div>
					<div style="flex: 0.8;">지역</div>
					<div style="flex: 0.6;">대학유형</div>
					<div style="flex: 0.5;">설립유형</div>
					<div style="flex: 0.5;">설치학과</div>
					<div style="width: 80px;">북마크</div>
					<%-- 펼치기/접기 아이콘 자리 --%>
				</div>

				<c:forEach var="university" items="${articlePage.content}" varStatus="status">
					<div class="univ-item" data-univ-id="${university.univId}">
						<div class="accordion-header">
							<div class="company-info-item" style="flex: 1.8;">${university.univName}</div>
							<div class="company-info-item" style="flex: 0.8;">${university.univRegion}</div>
							<div class="company-info-item" style="flex: 0.6;">${university.univType}</div>
							<div class="company-info-item" style="flex: 0.5;">${university.univGubun}</div>
							<div class="company-info-item" style="flex: 0.5;">${university.deptCount}</div>
							<div class="company-info-item" style="width: 80px;">
								<div class="item-action">
									<c:set var="isBookmarked" value="false" />

									<c:forEach var="bookmark" items="${bookMarkVOList}">
										<c:if test="${university.univId eq bookmark.bmTargetId}">
											<c:set var="isBookmarked" value="true" />
										</c:if>
									</c:forEach>

									<button class="bookmark-btn ${isBookmarked ? 'active' : ''}" data-category-id="G03001" data-target-id="${fn:escapeXml(university.univId)}">
										<span class="icon-active"> <img src="/images/bookmark-btn-active.png" alt="활성 북마크">
										</span> <span class="icon-inactive"> <img src="/images/bookmark-btn-inactive.png" alt="비활성 북마크">
										</span>
									</button>
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
					<li><a href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${articlePage.keyword}
						<c:forEach var='regionIds' items='${paramValues.regionIds}'>&regionIds=${regionIds}</c:forEach>
						<c:forEach var='typeIds' items='${paramValues.typeIds}'>&typeIds=${typeIds}</c:forEach>
						<c:forEach var='gubunIds' items='${paramValues.gubunIds}'>&gubunIds=${gubunIds}</c:forEach>
						&hiringStatus=${param.hiringStatus}" class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>"> ← Previous </a></li>

					<!-- Page Numbers -->
					<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
						<li><a href="${articlePage.url}?currentPage=${pNo}&keyword=${articlePage.keyword}
							<c:forEach var='regionIds' items='${paramValues.regionIds}'>&regionIds=${regionIds}</c:forEach>
							<c:forEach var='typeIds' items='${paramValues.typeIds}'>&typeIds=${typeIds}</c:forEach>
							<c:forEach var='gubunIds' items='${paramValues.gubunIds}'>&gubunIds=${gubunIds}</c:forEach>
							&hiringStatus=${param.hiringStatus}" class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>"> ${pNo} </a></li>
					</c:forEach>

					<!-- Next -->
					<li><a href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${articlePage.keyword}
						<c:forEach var='regionIds' items='${paramValues.regionIds}'>&regionIds=${regionIds}</c:forEach>
						<c:forEach var='typeIds' items='${paramValues.typeIds}'>&typeIds=${typeIds}</c:forEach>
						<c:forEach var='gubunIds' items='${paramValues.gubunIds}'>&gubunIds=${gubunIds}</c:forEach>
						&hiringStatus=${param.hiringStatus}" class="<c:if test='${articlePage.endPage >= articlePage.totalPages}'>disabled</c:if>"> Next → </a></li>
				</ul>
			</div>

		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script type="text/javascript" src="/js/ertds/univ/uvsrch/univList.js"></script>
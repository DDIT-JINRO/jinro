<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/univ/dpsrch/deptList.css">
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진학 정보</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a></div>
		<div class="channel-sub-section-item"><a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab" href="/ertds/univ/uvsrch/selectUnivList.do">대학 검색</a>
		    <a class="tab active" href="/ertds/univ/dpsrch/selectDeptList.do">학과 정보</a>
		</div>
	</div>
</div>
<div>
	<div class="public-wrapper">
		<div class="public-wrapper-main">
			<form method="get" action="/ertds/univ/dpsrch/selectDeptList.do">
				<div class="com-default-search">
					<div class="com-select-wrapper">
						<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg"
							viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd"
								d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z"
								clip-rule="evenodd" />
            </svg>
					</div>
					<input type="search" name="keyword" placeholder="학과명으로 검색">
					<button class="com-search-btn" type="button">
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
							<label class="com-filter-title">계열</label>
							<div class="com-filter-options">
								<c:forEach var="lClass" items="${lClass}">
									<label class="com-filter-item">
										<input type="checkbox" name="lClassIds" value="${lClass.ccId}">
										<span>${lClass.ccName}</span>
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

			<div class="company-list">
				<div class="accordion-header">
					<div style="flex: 1.8;">학과명</div>
					<div style="flex: 0.8;">학과계열</div>
					<div style="flex: 0.8;">입학경쟁률</div>
					<div style="flex: 0.8;">취업률</div>
					<div style="flex: 0.8;">첫월급 평균</div>
					<div style="width: 80px;">북마크</div>
					<div style="width: 80px;">비교</div>
					<%-- 펼치기/접기 아이콘 자리 --%>
				</div>

				<c:forEach var="univDept" items="${articlePage.content}" varStatus="status">
					<div class="univDept-item" data-univdept-id="${univDept.uddId}">
						<div class="accordion-header">
							<div class="company-info-item" style="flex: 1.8;">${univDept.uddMClass}</div>
							<div class="company-info-item" style="flex: 0.8;">${univDept.uddLClass}</div>
							<div class="company-info-item" style="flex: 0.8;">${univDept.admissionRate}</div>
							<div class="company-info-item" style="flex: 0.8;">${univDept.empRate} %</div>
							<div class="company-info-item" style="flex: 0.8;">${univDept.avgSalary}만원</div>
							<div class="company-info-item" style="width: 80px;">
								<div class="item-action">
									<c:set var="isBookmarked" value="false" />

									<c:forEach var="bookmark" items="${bookMarkVOList}">
										<c:if test="${univDept.uddId eq bookmark.bmTargetId}">
											<c:set var="isBookmarked" value="true" />
										</c:if>
									</c:forEach>

									<button class="bookmark-btn ${isBookmarked ? 'active' : ''}"
										data-category-id="G03006"
										data-target-id="${fn:escapeXml(univDept.uddId)}">
										<span class="icon-active"> <img
											src="/images/bookmark-btn-active.png" alt="활성 북마크">
										</span> <span class="icon-inactive"> <img
											src="/images/bookmark-btn-inactive.png" alt="비활성 북마크">
										</span>
									</button>
								</div>
							</div>
							<div class="company-info-item" style="width: 80px;">
								<label class="select-btn">
									<input type="checkbox" id="compare-btn${univDept.uddId}" name="jobLcls" value="${univDept.uddId}"
										data-dept-name="${univDept.uddMClass}" data-dept-sal="${univDept.avgSalary}" data-dept-emp="${univDept.empRate}" data-dept-admission="${univDept.admissionRate}"/>
									<span>
										비교
										<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" width="15" height="15">
	                             				<path fill-rule="evenodd" d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.052-.143z" clip-rule="evenodd" />
	                         				</svg>
									</span>
								</label>
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
						<c:forEach var='lClassIds' items='${paramValues.lClassIds}'>&lClassIds=${lClassIds}</c:forEach>"	
						class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>">
							← Previous </a></li>

					<!-- Page Numbers -->
					<c:forEach var="pNo" begin="${articlePage.startPage}"
						end="${articlePage.endPage}">
						<li><a
							href="${articlePage.url}?currentPage=${pNo}&keyword=${articlePage.keyword}
							<c:forEach var='lClassIds' items='${paramValues.lClassIds}'>&lClassIds=${lClassIds}</c:forEach>"
							class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>">
								${pNo} </a></li>
					</c:forEach>

					<!-- Next -->
					<li><a
						href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${articlePage.keyword}
						<c:forEach var='lClassIds' items='${paramValues.lClassIds}'>&lClassIds=${lClassIds}</c:forEach>"				
						class="<c:if test='${articlePage.endPage >= articlePage.totalPages}'>disabled</c:if>">
							Next → </a></li>
				</ul>
			</div>

		</div>
	</div>
</div>

<div class="dept-compare-popup">
    <header class="popup-header">
        <div class="popup-title-group">
            <img src="" alt="직업 비교 아이콘" class="popup-logo">
            <h2 class="popup-title">학과 비교</h2>
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
        <button type="button" class="btn-view-results">학과 비교하기</button>
    </footer>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script type="text/javascript" src="/js/ertds/univ/dpsrch/deptList.js"></script>
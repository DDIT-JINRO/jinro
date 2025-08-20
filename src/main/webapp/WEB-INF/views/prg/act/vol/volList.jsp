<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/prg/act/vol/volList.css">
<section class="channel">
	<div class="channel-title">
		<div class="channel-title-text">프로그램</div>
	</div>
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/prg/ctt/cttList.do">공모전</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/prg/act/vol/volList.do">대외활동</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/prg/std/stdGroupList.do">스터디그룹</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="tab-container" id="tabs">
			<a class="tab active" href="/prg/act/vol/volList.do">봉사활동</a>
			<a class="tab" href="/prg/act/cr/crList.do">인턴십</a>
			<a class="tab" href="/prg/act/sup/supList.do">서포터즈</a>
		</div>

		<div class="public-wrapper-main">
			<div class="filter-section">
				<form method="get" action="/prg/act/vol/volList.do">
					<div class="com-default-search">
						<div class="com-select-wrapper">
							<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
			                <path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
			            </svg>
						</div>
						<input type="search" name="keyword" placeholder="검색어를 입력하세요">
						<button class="com-search-btn" type="button">
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
									<label class="com-filter-title">모집 상태</label>
									<div class="com-filter-options">
										<label class="com-filter-item">
											<input type="checkbox" name="contestStatusFilter" value="proceeding" data-label="모집 상태" data-name="진행중" <c:if test="${fn:contains(checkedFilters.contestStatusFilter, 'proceeding')}">checked</c:if> />
											<span>진행중</span>
										</label>
										<label class="com-filter-item">
											<input type="checkbox" name="contestStatusFilter" value="finished" data-label="모집 상태" data-name="마감" <c:if test="${fn:contains(checkedFilters.contestStatusFilter, 'finished')}">checked</c:if> />
											<span>마감</span>
										</label>
									</div>
								</div>

								<div class="com-filter-section">
									<label class="com-filter-title">모집 대상</label>
									<div class="com-filter-options">
										<c:forEach var="cTarget" items="${contestTargetList}">
											<label class="com-filter-item">
												<input type="checkbox" name="contestTargetFilter" value="${cTarget.ccId}" data-label="모집 대상" data-name="${cTarget.ccName}" <c:if test="${fn:contains(checkedFilters.contestTargetFilter, cTarget.ccId)}">checked</c:if> />
												<span>${cTarget.ccName}</span>
											</label>
										</c:forEach>
									</div>
								</div>
								<div class="com-filter-section">
									<label class="com-filter-title">정렬 순서</label>
									<div class="com-filter-options">
										<label class="com-filter-item">
											<input type="radio" name="sortOrder" value="deadline" ${checkedFilters.sortOrder == 'deadlin' or empty checkedFilters.sortOrder ? 'checked' : ''} />
											<span>마감일 임박순</span>
										</label>
										<label class="com-filter-item">
											<input type="radio" name="sortOrder" value="latest" ${checkedFilters.sortOrder == 'latest' ? 'checked' : ''} />
											<span>최신 등록순</span>
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
					</div>
				</form>
			</div>
			<p>총 ${articlePage.total}건</p>

			<div class="list-container">
				<c:if test="${not empty articlePage.content}">
					<c:forEach var="contest" items="${articlePage.content}">
						<a href="/prg/act/vol/volDetail.do?volId=${contest.contestId}" class="contest-card">
							<div class="card-image-box">
								<img src="/files/download?fileGroupId=${contest.fileGroupId}&seq=1" alt="포스터 이미지" class="contest-image">
							</div>
							<div class="card-content">
								<h3 class="contest-title">${contest.contestTitle}</h3>
								<div class="contest-description">
									<ul class="card-info-list">
										<li>
											<span class="info-label">주최</span>
											<span class="info-value">${contest.contestHost}</span>
										</li>
										<li>
											<span class="info-label">접수기간</span>
											<span class="info-value">
												<fmt:formatDate value="${contest.contestStartDate}" pattern="yyyy.MM.dd" />
												~
												<fmt:formatDate value="${contest.contestEndDate}" pattern="yyyy.MM.dd" />
											</span>
										</li>
										<li class="d-day-item" data-end-date="<fmt:formatDate value='${contest.contestEndDate}' pattern='yyyy-MM-dd' />">
											<span class="info-label">마감까지</span>
											<span class="info-value d-day-text"> </span>
										</li>
										<li>
											<span class="info-label">모집상태</span>
											<span class="info-value">
												<fmt:formatDate value="${contest.contestEndDate}" pattern="yyyyMMdd" var="endDay" />
												<fmt:formatDate value="${now}" pattern="yyyyMMdd" var="today" />
												<c:choose>
													<c:when test="${endDay < today}">
														<span class="status-tag finished">마감</span>
													</c:when>
													<c:otherwise>
														<span class="status-tag proceeding">진행중</span>
													</c:otherwise>
												</c:choose>
											</span>
										</li>
									</ul>
								</div>
								<div class="contest-meta">
									<span class="meta-item"> ${contest.contestGubunName} | ${contest.contestTypeName} | ${contest.contestTargetName} </span>
									<br />
									<span class="meta-item">조회수 ${contest.contestRecruitCount}</span>
									<span class="meta-item">
										<fmt:formatDate value="${contest.contestCreatedAt}" pattern="yyyy. MM. dd" />
									</span>
								</div>
							</div>
						</a>
					</c:forEach>
				</c:if>
				<c:if test="${empty articlePage.content}">
					<div style="text-align: center; padding: 50px;">검색 결과가 없습니다.</div>
				</c:if>
			</div>
		</div>

		<div class="card-footer clearfix">
			<ul class="pagination">
				<li>
					<c:url value="/prg/act/vol/volList.do" var="prevUrl">
						<c:param name="currentPage" value="${articlePage.startPage - 5}" />
						<c:param name="keyword" value="${checkedFilters.keyword}" />
						<c:param name="sortOrder" value="${checkedFilters.sortOrder}" />
						<c:forEach var="filter" items="${checkedFilters.contestGubunFilter}">
							<c:param name="contestGubunFilter" value="${filter}" />
						</c:forEach>
						<c:forEach var="filter" items="${checkedFilters.contestTargetFilter}">
							<c:param name="contestTargetFilter" value="${filter}" />
						</c:forEach>
						<c:forEach var="filter" items="${checkedFilters.contestStatusFilter}">
							<c:param name="contestStatusFilter" value="${filter}" />
						</c:forEach>
					</c:url>
					<a href="${prevUrl}" class="${articlePage.startPage < 6 ? 'disabled' : ''}">← Previous</a>
				</li>

				<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
					<li>
						<c:url value="/prg/act/vol/volList.do" var="pageUrl">
							<c:param name="currentPage" value="${pNo}" />
							<c:param name="keyword" value="${checkedFilters.keyword}" />
							<c:param name="sortOrder" value="${checkedFilters.sortOrder}" />
							<c:forEach var="filter" items="${checkedFilters.contestGubunFilter}">
								<c:param name="contestGubunFilter" value="${filter}" />
							</c:forEach>
							<c:forEach var="filter" items="${checkedFilters.contestTargetFilter}">
								<c:param name="contestTargetFilter" value="${filter}" />
							</c:forEach>
							<c:forEach var="filter" items="${checkedFilters.contestStatusFilter}">
								<c:param name="contestStatusFilter" value="${filter}" />
							</c:forEach>
						</c:url>
						<a href="${pageUrl}" class="${pNo == articlePage.currentPage ? 'active' : ''}">${pNo}</a>
					</li>
				</c:forEach>

				<li>
					<c:url value="/prg/act/vol/volList.do" var="nextUrl">
						<c:param name="currentPage" value="${articlePage.startPage + 5}" />
						<c:param name="keyword" value="${checkedFilters.keyword}" />
						<c:param name="sortOrder" value="${checkedFilters.sortOrder}" />
						<c:forEach var="filter" items="${checkedFilters.contestGubunFilter}">
							<c:param name="contestGubunFilter" value="${filter}" />
						</c:forEach>
						<c:forEach var="filter" items="${checkedFilters.contestTargetFilter}">
							<c:param name="contestTargetFilter" value="${filter}" />
						</c:forEach>
						<c:forEach var="filter" items="${checkedFilters.contestStatusFilter}">
							<c:param name="contestStatusFilter" value="${filter}" />
						</c:forEach>
					</c:url>
					<a href="${nextUrl}" class="${articlePage.endPage >= articlePage.totalPages ? 'disabled' : ''}">Next →</a>
				</li>
			</ul>
		</div>

	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script src="/js/prg/act/vol/volList.js"></script>
</body>
</html>
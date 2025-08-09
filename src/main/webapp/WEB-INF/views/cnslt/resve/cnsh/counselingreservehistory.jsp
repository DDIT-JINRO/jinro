<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/mpg/mat/csh/selectCounselingHistoryList.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">상담</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/cnslt/resve/crsv/reservation.do">상담 예약</a></div>
		<div class="channel-sub-section-item"><a href="/cnslt/rvw/cnsReview.do">상담 후기</a></div>		
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab " href="/cnslt/resve/crsv/reservation.do">상담 예약</a>
		    <a class="tab active" href="/cnslt/resve/cnsh/counselingReserveHistory.do">상담 내역</a>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  						<div class="activity-container">
				<form method="GET" action="${articlePage.url}">
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
						<input type="search" name="keyword" placeholder="내 상담내역에서 검색" value="${param.keyword}">
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
							<div class="com-filter-row">
								<div class="com-filter-section">
									<label class="com-filter-title">상담 신청 상태</label>
									<div class="com-filter-options">
										<c:forEach var="status" items="${counselStatus}">
											<label class="com-filter-item">
												<input type="radio" name="counselStatus" value="${status.key}" ${status.key == param.counselStatus ? 'checked' : '' }>
												<span>${status.value}</span>
											</label>
										</c:forEach>
									</div>
								</div>
								<div class="com-filter-section">
									<label class="com-filter-title">상담 분류</label>
									<div class="com-filter-options">
										<c:forEach var="category" items="${counselCategory}">
											<label class="com-filter-item">
												<input type="radio" name="counselCategory" value="${category.key}" ${category.key == param.counselCategory ? 'checked' : '' }>
												<span>${category.value}</span>
											</label>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="com-filter-row">
								<div class="com-filter-section">
									<label class="com-filter-title">상담 방법</label>
									<div class="com-filter-options">
										<c:forEach var="method" items="${counselMethod}">
											<label class="com-filter-item">
												<input type="radio" name="counselMethod" value="${method.key}" ${method.key == param.counselMethod ? 'checked' : '' }>
												<span>${method.value}</span>
											</label>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="com-filter-section">
								<div class="com-button-container">
									<label class="com-filter-title">선택된 필터</label>
									<button type="button" class="com-filter-reset-btn">초기화</button>
								</div>
								<div class="com-selected-filters">
									<c:if test="${!empty counselStatus[param.counselStatus]}">
										<span class="com-selected-filter" data-filter="상담 신청 상태 > ${counselStatus[param.counselStatus]}" data-group="counselStatus">
											상담 신청 상태 > ${counselStatus[param.counselStatus]}
											<button type="button" class="com-remove-filter">×</button>
										</span>
									</c:if>
									<c:if test="${!empty counselCategory[param.counselCategory]}">
										<span class="com-selected-filter" data-filter="상담 분류 > ${counselCategory[param.counselCategory]}" data-group="counselCategory">
											상담 분류 > ${counselCategory[param.counselCategory]}
											<button type="button" class="com-remove-filter">×</button>
										</span>
									</c:if>
									<c:if test="${!empty counselMethod[param.counselMethod]}">
										<span class="com-selected-filter" data-filter="상담 방법 > ${counselCategory[param.counselMethod]}" data-group="counselMethod">
											상담 방법 > ${counselMethod[param.counselMethod]}
											<button type="button" class="com-remove-filter">×</button>
										</span>
									</c:if>
								</div>
							</div>
							<button type="submit" class="com-submit-search-btn">검색</button>
						</div>
					</div>
				</form>
				<c:choose>
					<c:when test="${empty articlePage.content || articlePage.content == null }">
						<p class="no-content-message">현재 상담 내역이 없습니다.</p>
					</c:when>
					<c:otherwise>
					<div class="counsel-list">
						<c:forEach var="content" items="${articlePage.content}">
							<div class="counsel-item">
								<div class="item-content">
									<div class="item-header">
										<span class="category-tag">${counselCategory[content.counselCategory]}</span>
										<span class="category-tag">${counselStatus[content.counselStatus]}</span>
										<span class="category-tag">${counselMethod[content.counselMethod]}</span>
									</div>
									<h3 class="item-title">
										${content.counselTitle}
									</h3>
									<p class="item-snippet">${content.counselDescription}</p>
									<div class="item-meta">
										<span>상담사</span>
										<span>${content.counselName}</span>
										<span class="divider">·</span>
										<span>신청일</span>
										<span><fmt:formatDate value="${content.counselCreatedAt}" pattern="yyyy. MM. dd"/> </span>
										<span class="divider">·</span>
										<span>예약일</span>
										<span><fmt:formatDate value="${content.counselCreatedAt}" pattern="yyyy. MM. dd"/><fmt:formatDate value="${content.counselReqDatetime}" pattern=" (HH시)"/></span>
									</div>
								</div>
								<div class="item-content">
									<c:choose>
								    	<c:when test="${content.counselStatus == 'S04001'}">
										     <span class="btn btn-primary">대기중</span>
									 	</c:when>
								    	<c:when test="${content.counselStatus == 'S04002'}">
										     <span class="btn btn-danger">취소됨</span>
									 	</c:when>
										<c:when test="${content.counselStatus == 'S04003'}">
											<a href="#" onclick="openCounselingPopup('${content.counselUrlUser}'); return false;" class="btn btn-primary counselStart">상담시작</a>
										</c:when>
										<c:when test="${content.counselReviewd == 'N' && content.counselStatus == 'S04004'}">
											<a href="/cnslt/rvw/cnsReview.do" class="btn btn-primary">후기 작성하기</a>
										</c:when>
									</c:choose>
								</div>
							</div>
						</c:forEach>
					</div>
					</c:otherwise>
				</c:choose>
				
				<ul class="pagination">
					<li>
						<a href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${param.keyword}&status=${param.status}" class="
							<c:if test='${articlePage.startPage < 6}'>
								disabled
							</c:if>"> ← Previous
						</a>
					</li>
					
					<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
						<li>
							<a href="${articlePage.url}?currentPage=${pNo}&keyword=${param.keyword}&status=${param.status}" class="page-num 
								<c:if test='${pNo == articlePage.currentPage}'>
									active
								</c:if>"> ${pNo}
							</a>
						</li>
					</c:forEach>

					<li>
						<a href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${param.keyword}&status=${param.status}" class="
							<c:if test='${articlePage.endPage >= articlePage.totalPages}'>
								disabled
							</c:if>"> Next →
						</a>
					</li>
				</ul>
			</div>
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script src="/js/mpg/mat/csh/selectCounselingHistoryList.js"></script>
<script src="/js/cnslt/resve/cnsh/counselingreservehistory.js"></script>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
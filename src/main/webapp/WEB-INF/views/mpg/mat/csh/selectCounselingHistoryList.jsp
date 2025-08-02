<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/mpg/mat/csh/selectCounselingHistoryList.css">
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">마이페이지</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/mpg/mif/inq/selectMyInquiryView.do">내 정보</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/mpg/mat/bmk/selectBookMarkList.do">나의 활동</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/mpg/pay/selectPaymentView.do">결제 구독내역</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab" href="/mpg/mat/bmk/selectBookMarkList.do">북마크</a>
			<a class="tab active" href="/mpg/mat/csh/selectCounselingHistoryList.do">상담 내역</a>
			<a class="tab" href="/mpg/mat/reh/selectResumeHistoryList.do">이력서</a>
			<a class="tab" href="/mpg/mat/sih/selectSelfIntroHistoryList.do">자기소개서</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="activity-container">
				<form method="GET" action="${articlePage.url }">
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
						<input type="search" name="keyword" placeholder="북마크 내에서 검색">
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
						<p class="no-content-message">현재 북마크가 없습니다.</p>
					</c:when>
					<c:otherwise>
					<div class="counsel-list">
						<c:forEach var="content" items="${articlePage.content}">
							<div class="counsel-item">
								<div class="item-content">
									<div class="item-header">
										<span class="category-tag counsel-category">${content.counselCategory}</span>
										<span class="category-tag counsel-status">${content.counselStatus}</span>
										<span class="category-tag counsel-method">${content.counselMethod}</span>
										<h3 class="item-title">
											<a href="#">${content.counselTitle}</a>
										</h3>
									</div>
									<p class="item-snippet">${content.counselDescription}</p>
									<div class="item-meta">
										<span>상담사</span>
										<span>${content.counselName}</span>
										<span class="divider">·</span>
										<span>신청일</span>
										<span><fmt:formatDate value="${content.counselCreatedAt}" pattern="yyyy년 MM월 dd일"/> </span>
										<span class="divider">·</span>
										<span>예약일</span>
										<span><fmt:formatDate value="${content.counselCreatedAt}" pattern="yyyy년 MM월 dd일"/><fmt:formatDate value="${content.counselReqDate}" pattern="HH시"/></span>
									</div>
								</div>
								<div class="item-content">
									<a href="#" class="btn btn-primary">후기 작성하러 가기</a>
								</div>
							</div>
						</c:forEach>
					</div>
					</c:otherwise>
				</c:choose>

				<ul class="pagination">
					<li>
						<a href="/sint/qestnlst?currentPage=-4&amp;keyword=" class="disabled">← Previous</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=1&amp;keyword=" class="page-num active">1</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=2&amp;keyword=" class="page-num">2</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=3&amp;keyword=" class="page-num">3</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=4&amp;keyword=" class="page-num">4</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=5&amp;keyword=" class="page-num">5</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=6&amp;keyword=" class="">Next →</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script src="/js/mpg/mat/csh/selectCounselingHistoryList.js"></script>
</html>
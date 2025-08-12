<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cnslt/rvw/cnsReview.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">상담</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/cnslt/resve/crsv/reservation.do">상담 예약</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/cnslt/rvw/cnsReview.do">상담 후기</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="teenListTop">
				<button id=btnWrite>상담 후기 공유하기</button>
			</div>
			<form method="get" action="${articlePage.url}">
				<div class="com-default-search">
					<div class="com-select-wrapper">
						<select name="status" class="com-status-filter">
							<option value="all">전체</option>
							<option value="counselName">상담사</option>
							<option value="content">후기 내용</option>
							<option value="writer">작성자</option>
						</select>
						<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                			<path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
            			</svg>
					</div>
					<input type="search" class="search-input" name="keyword" placeholder="상담 후기 게시판 내에서 검색" value="${param.keyword}">
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
								<label class="com-filter-title">상담 방법</label>
								<div class="com-filter-options">
									<c:forEach var="method" items="${counselMethod}">
										<c:if test="${method.key ne 'G08004'}">
											<c:set var="isChecked" value="${false}" />

											<c:forEach var="filterParams" items="${paramValues.counselMethods}">
												<c:if test="${method.key eq filterParams}">
													<c:set var="isChecked" value="${true}" />
												</c:if>
											</c:forEach>

											<label class="com-filter-item">
												<input id="${method.key}" type="checkbox" name="counselMethods" value="${method.key}" ${isChecked ? 'checked' : ''} />
												<span>${method.value}</span>
											</label>
										</c:if>
									</c:forEach>
								</div>
							</div>
							<div class="com-filter-section">
								<label class="com-filter-title">상담 분류</label>
								<div class="com-filter-options">
									<c:forEach var="category" items="${counselCategory}">
										<c:set var="isChecked" value="${false}" />

										<c:forEach var="filterParams" items="${paramValues.counselCategorys}">
											<c:if test="${category.key eq filterParams}">
												<c:set var="isChecked" value="${true}" />
											</c:if>
										</c:forEach>

										<label class="com-filter-item">
											<input id="${category.key}" type="checkbox" name="counselCategorys" value="${category.key}" ${isChecked ? 'checked' : ''} />
											<span>${category.value}</span>
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
									<c:forEach var="filterParams" items="${paramValues.counselMethods}">
										<span class="com-selected-filter" data-group="jobLcls" data-value="${param}">
											상담 방법 > ${counselMethod[filterParams]}
											<button type="button" class="com-remove-filter">×</button>
										</span>
									</c:forEach>

									<c:forEach var="filterParams" items="${paramValues.counselCategorys}">
										<span class="com-selected-filter" data-group="jobSals" data-value="${param}">
											상담 분야 > ${counselCategory[filterParams]}
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

			<div class="group-card-content">
				<div class="content-header">
					<div class="card-header-left">
						<div class="company-name">상담사</div>
						<div class="card-meta">
							<div class="meta-item">상담방법</div>
							<div class="meta-item">상담분야</div>
							<div class="meta-item author">작성자</div>
							<div class="meta-item">작성일</div>
							<div class="rating-header">평점</div>
							<div class="meta-item">공개여부</div>
							<div class="card-toggle"></div>
						</div>
					</div>
				</div>
				<c:forEach var="content" items="${articlePage.content}">
					<div class="interview-card">
						<div class="card-header">
							<div class="card-header-left">
								<div class="company-name">${content.counselName}</div>
								<div class="card-meta">
									<div class="meta-item">
										<span>${content.counselMethod}</span>
									</div>
									<div class="meta-item">
										<span>${content.counselCategory}</span>
									</div>

									<div class="meta-item author">
										<c:choose>
											<c:when test="${pageContext.request.userPrincipal.principal == content.memId}">
												<span class="my-post-badge">내 글</span>
											</c:when>
											<c:otherwise>
							                 	${content.memNickname}
											</c:otherwise>
										</c:choose>
									</div>
									<div class="meta-item">
										<svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor">
						                    <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM2 2a1 1 0 0 0-1 1v1h14V3a1 1 0 0 0-1-1H2zm13 3H1v9a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V5z" />
						                </svg>
										<fmt:formatDate value="${content.crCreatedAt}" pattern="yyyy. MM. dd" />
									</div>
									<div class="rating">
										<span class="stars">
											<c:forEach begin="1" end="5" var="i">
												<c:choose>
													<c:when test="${i <= content.crRate}">★</c:when>
													<c:otherwise>☆</c:otherwise>
												</c:choose>
											</c:forEach>
										</span>
										<span>${content.crRate}.0</span>
									</div>

									<div class="meta-item">
										<span>${content.crPublic == 'Y' ? '공개' : '비공개'}</span>
									</div>

									<div class="card-toggle">
										<svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
					                        <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z" />
					                    </svg>
									</div>
								</div>
							</div>
						</div>
						<div class="card-content">
							<div class="card-body">
								<div class="detail-grid">
									<div class="detail-item feedback-content">
										<div class="detail-label">후기 내용</div>
										<c:if test="${content.crPublic == 'Y' || pageContext.request.userPrincipal.principal == content.memId}">
											<div class="feedback-text">${content.crContent}</div>
										</c:if>
										<c:if test="${content.crPublic == 'N' && pageContext.request.userPrincipal.principal != content.memId}">
											<div class="feedback-text">비공개 후기내용입니다.</div>
										</c:if>
									</div>
								</div>
								<c:if test="${pageContext.request.userPrincipal.principal == content.memId}">
									<div class="card-actions">
										<button type="button" class="action-btn edit-btn" data-mem-id="${content.memId}" data-cr-id="${content.crId}">
											<svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor">
				                                <path d="M11.013 1.427a1.75 1.75 0 0 1 2.474 0l1.086 1.086a1.75 1.75 0 0 1 0 2.474l-8.61 8.61c-.21.21-.47.364-.756.445l-3.251.93a.75.75 0 0 1-.927-.928l.929-3.25c.081-.286.235-.547.445-.758l8.61-8.61Zm.176 4.823L9.75 4.81l-6.286 6.287a.253.253 0 0 0-.064.108l-.558 1.953 1.953-.558a.253.253 0 0 0 .108-.064Zm1.238-3.763a.25.25 0 0 0-.354 0L10.811 3.75l1.439 1.44 1.263-1.263a.25.25 0 0 0 0-.354Z" />
				                            </svg>
											수정
										</button>
										<button type="button" class="action-btn delete-btn" data-cr-id="${content.crId}">
											<svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor">
				                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z" />
				                                <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z" />
				                            </svg>
											삭제
										</button>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>
				<c:if test="${empty articlePage.content}">
					<p class="no-content-message">상담 후기가 없습니다.</p>
				</c:if>
			</div>

			<ul class="pagination">
				<li>
					<a href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${param.keyword}&status=${param.status}" class="${articlePage.startPage < 6 ? 'disabled' : ''}"> ← Previous </a>
				</li>
				<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
					<li>
						<a href="${articlePage.url}?currentPage=${pNo}&keyword=${param.keyword}&status=${param.status}" class="page-num ${pNo == articlePage.currentPage ? 'active' : ''}"> ${pNo} </a>
					</li>
				</c:forEach>
				<li>
					<a href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${param.keyword}&status=${param.status}" class="${articlePage.endPage >= articlePage.totalPages ? 'disabled' : '' }"> Next → </a>
				</li>
			</ul>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript" src="/js/cnslt/rvw/cnsReview.js"></script>
</body>
</html>
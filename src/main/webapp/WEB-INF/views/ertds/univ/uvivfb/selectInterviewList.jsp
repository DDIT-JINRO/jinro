<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/univ/uvivfb/selectInterviewList.css">
<section class="channel">
	<div class="channel-title">
		<div class="channel-title-text">진학 정보</div>
	</div>
	<div class="channel-sub-sections">
		<div class="channel-sub-section-itemIn">
			<a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a>
		</div>
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
		<div class="tab-container" id="tabs">
			<a class="tab" href="/ertds/univ/uvsrch/selectUnivList.do">대학 검색</a>
			<a class="tab" href="/ertds/univ/dpsrch/selectDeptList.do">학과 정보</a>
			<a class="tab active" href="/ertds/univ/uvivfb/selectInterviewList.do">면접 후기</a>
		</div>
		<div class="public-wrapper-main">
			<div class="teenListTop">
				<button id=btnWrite>면접 후기 공유하기</button>
			</div>
			<form method="get" action="${articlePage.url}">
				<div class="com-default-search">
					<div class="com-select-wrapper">
						<select name="status" class="com-status-filter">
							<option value="all">전체</option>
							<option value="targetName">대학명</option>
							<option value="content">후기 내용</option>
							<option value="writer">작성자</option>
						</select>
						<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                			<path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
            			</svg>
					</div>
					<input type="search" class="search-input" name="keyword" placeholder="면접 후기 게시판 내에서 검색" value="${param.keyword}">
					<button class="com-search-btn" type="submit">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
                			<path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
           				</svg>
					</button>
				</div>
			</form>
			<div class="group-card-content">
				<div class="content-header">
					<div class="card-header-left">
						<div class="company-name">대학명</div>
						<div class="card-meta">
							<div class="meta-item author">작성자</div>
							<div class="meta-item">작성일</div>
							<div class="rating-header">평점</div>
							<div class="card-toggle"></div>
						</div>
					</div>
				</div>
				<c:forEach var="content" items="${articlePage.content}">
					<div class="interview-card">
						<div class="card-header">
							<div class="card-header-left">
								<div class="company-name">${content.targetName}</div>
								<div class="card-meta">
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
										<fmt:formatDate value="${content.irModAt}" pattern="yyyy. MM. dd" />
									</div>
									<div class="rating">
										<span class="stars">
											<c:forEach begin="1" end="5" var="i">
												<c:choose>
													<c:when test="${i <= content.irRating}">★</c:when>
													<c:otherwise>☆</c:otherwise>
												</c:choose>
											</c:forEach>
										</span>
										<span>${content.irRating}.0</span>
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
									<div class="detail-item">
										<div class="detail-label">작성자</div>
										<div class="detail-value">${content.memNickname}</div>
									</div>
									<div class="detail-item">
										<div class="detail-label">면접 대학</div>
										<div class="detail-value">${content.targetName}</div>
									</div>
									<div class="detail-item">
										<div class="detail-label">면접 학과</div>
										<div class="detail-value">${content.irApplication}</div>
									</div>
									<div class="detail-item">
										<div class="detail-label">면접일</div>
										<div class="detail-value">
											<fmt:formatDate value="${content.irInterviewAt}" pattern="yyyy. MM. dd" />
										</div>
									</div>
									<div class="detail-item">
										<div class="detail-label">작성일</div>
										<div class="detail-value">
											<fmt:formatDate value="${content.irCreatedAt}" pattern="yyyy. MM. dd HH:mm" />
										</div>
									</div>
									<div class="detail-item">
										<div class="detail-label">수정일</div>
										<div class="detail-value">
											<fmt:formatDate value="${content.irModAt}" pattern="yyyy. MM. dd HH:mm" />
										</div>
									</div>
									<div class="detail-item">
										<div class="detail-label">평점</div>
										<div class="detail-value">
											<span class="stars" style="color: #ffd700;">
												<c:forEach begin="1" end="5" var="i">
													<c:choose>
														<c:when test="${i <= content.irRating}">★</c:when>
														<c:otherwise>☆</c:otherwise>
													</c:choose>
												</c:forEach>
											</span>
											<span style="margin-left: 8px;">${content.irRating}.0 / 5.0</span>
										</div>
									</div>
									<div class="detail-item feedback-content">
										<div class="detail-label">후기 내용</div>
										<div class="feedback-text">${content.irContent}</div>
									</div>
								</div>
								<c:if test="${pageContext.request.userPrincipal.principal == content.memId}">
									<div class="card-actions">
										<button type="button" class="action-btn edit-btn" data-mem-id="${content.memId}" data-ir-id="${content.irId}">
											<svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor">
				                                <path d="M11.013 1.427a1.75 1.75 0 0 1 2.474 0l1.086 1.086a1.75 1.75 0 0 1 0 2.474l-8.61 8.61c-.21.21-.47.364-.756.445l-3.251.93a.75.75 0 0 1-.927-.928l.929-3.25c.081-.286.235-.547.445-.758l8.61-8.61Zm.176 4.823L9.75 4.81l-6.286 6.287a.253.253 0 0 0-.064.108l-.558 1.953 1.953-.558a.253.253 0 0 0 .108-.064Zm1.238-3.763a.25.25 0 0 0-.354 0L10.811 3.75l1.439 1.44 1.263-1.263a.25.25 0 0 0 0-.354Z" />
				                            </svg>
											수정
										</button>
										<button type="button" class="action-btn delete-btn" data-ir-id="${content.irId}">
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
					<p class="no-content-message">면접 후기가 없습니다.</p>
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
<div></div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript" src="/js/ertds/univ/uvivfb/selectInterviewList.js"></script>
</body>
</html>
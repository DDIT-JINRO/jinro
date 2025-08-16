<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/csc/inq/inqList.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">고객센터</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/csc/not/noticeList.do">공지사항</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/csc/faq/faqList.do">FAQ</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/csc/inq/inqryList.do">1:1문의</a>
		</div>
	</div>
</section>
<div>
	<div>
		<div class="public-wrapper">
			<div class="public-wrapper-main">
				<div class="inq-wrapper">
					<form method="get" action="/csc/inq/inqryList.do">
						<div class="com-default-search">
							<div class="com-select-wrapper"></div>
							<input type="search" name="com-search-keyword"
								placeholder="1:1문의내에서 검색">
							<button class="com-search-btn" type="submit">
								<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
									fill="currentColor" width="20" height="20">
				                <path fill-rule="evenodd"
										d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z"
										clip-rule="evenodd" />
				            </svg>
							</button>
						</div>
						
					</form>
					<div class="inq-list">
						<input type="hidden" value="${memId}" id="getMemId">
						<c:forEach var="item" items="${inqList}">
							<div class="inq-item" data-is-public="${item.contactIsPublic}" data-author-id="${item.memId}">
								<button class="inq-question">
								<div class="inq-icon-title">
									<div class="inq-title-left">
										<span class="inq-icon">Q</span>
										<span class="inq-title-text">${item.contactTitle}</span>
									</div>
									<div class="inq-title-right">
										<c:if test="${item.contactIsPublic == 'Y'}">
											<span>공개</span>
										</c:if>
										<c:if test="${item.contactIsPublic == 'N'}">
											<span>비공개</span>
										</c:if>
										<span>${item.memName}</span>
										<c:if test="${item.contactReply != null}">
											<span>답변 완료</span>
										</c:if>
										<c:if test="${item.contactReply == null}">
											<span>답변 대기</span>
										</c:if>
										<span style="margin-top: 4px">
											<fmt:formatDate value="${item.contactAt}" pattern="yyyy.MM.dd" />
										</span>
									</div>
								</div>
									<span class="arrow">▼</span>
								</button>

								<!-- inq 본문 -->
								<div class="inq-content">
									<span style="font: 16px; font-weight: bold;">문의내용 : </span></br>
									${item.contactContent}
									<!-- inq 답변 -->
									<c:if test="${item.contactReply != null}">
										<div class="separator-line"></div>
										<span style="font: 16px; font-weight: bold;">답변내용 : </span>
										</br>
										${item.contactReply}
									</c:if>
								</div>

							</div>
						</c:forEach>
					</div>
					<div class="group-write-btn-wrapper">
						<button class="btn-write-group" id="btnWrite">✏️ 글 작성하기</button>
					</div>
					<div class="card-footer clearfix">
						<ul class="pagination">
							<!-- Previous -->
							<li><a
								href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${param.keyword}&gubun=${param.gubun}"
								class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>">
									← Previous </a></li>
					
							<!-- Page Numbers -->
							<c:forEach var="pNo" begin="${articlePage.startPage}"
								end="${articlePage.endPage}">
								<li><a href="${articlePage.url}?currentPage=${pNo}&keyword=${param.keyword}&gubun=${param.gubun}"
									class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>">
										${pNo} </a></li>
							</c:forEach>
					
							<!-- Next -->
							<li><a
								href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${param.keyword}&gubun=${param.gubun}"
								class="<c:if test='${articlePage.endPage >= articlePage.totalPages}'>disabled</c:if>">
									Next → </a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<script type="text/javascript" src="/js/csc/inq/inqList.js"></script>
	</body>
	</html>
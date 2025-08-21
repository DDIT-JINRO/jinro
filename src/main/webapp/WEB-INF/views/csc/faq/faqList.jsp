<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/csc/faq/faqList.css">
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
		<div class="channel-sub-section-itemIn">
			<a href="/csc/faq/faqList.do">FAQ</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/csc/inq/inqryList.do">1:1문의</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="tab-container" id="tabs">
			<h3 class="page-title-bar__title">FAQ</h3>
		</div>
		
		<div class="public-wrapper-main">
			<div class="faq-wrapper">
				<form method="get" action="/csc/faq/faqList.do">
					<div class="com-default-search">
						<input type="search" name="keyword" placeholder="FAQ 내에서 검색">
						<button class="com-search-btn" type="submit">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
				                <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
				            </svg>
						</button>
					</div>
				</form>
				<div class="faq-list">
					<c:forEach var="item" items="${faqList}">
						<div class="faq-item">
							<button class="faq-question">
								<div class="faq-icon-title">
									<span class="faq-icon">Q</span>
									<span class="faq-title-text">${item.faqTitle}</span>
								</div>
								<span class="arrow">▼</span>
							</button>

							<!-- FAQ 본문 -->
							<div class="faq-answer">
								${item.faqContent}
								<!-- FAQ 첨부파일 -->
								<c:if test="${not empty item.getFileList}">
									<div class="faq-file">
										<span>📥 다운로드 | </span>
										<c:forEach var="file" items="${item.getFileList}">
											<div class="file-item" onclick="filedownload(${file.fileGroupId}, ${file.fileSeq}, '${file.fileOrgName}')">${file.fileOrgName}</div>
										</c:forEach>
									</div>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript" src="/js/csc/faq/faqList.js"></script>
</body>
</html>
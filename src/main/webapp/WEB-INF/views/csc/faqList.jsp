<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/csc/faqList.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">고객센터</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/csc/not/noticeList.do">공지사항</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/csc/faq/faqList.do">FAQ</a></div>
		<div class="channel-sub-section-item"><a href="/csc/inq/inqryList.do">1:1문의</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
  		<div class="public-wrapper-main">
			<div class="faq-wrapper">
				<form class="faq-search-box" action="/csc/faq/faqList.do" method="get">
					<div class="faq-search-input-wrapper">
						<input type="text" class="faq-search-input" name="keyword" placeholder="검색어를 입력하세요..." value="${param.keyword}">
					</div>
					<button type="submit" class="faq-search-btn">검색</button>
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
<script type="text/javascript" src="/js/csc/faqList.js"></script>
</body>
</html>
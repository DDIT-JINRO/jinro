<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" src="/js/csc/noticeDetail.js" defer></script>
<link rel="stylesheet" href="/css/csc/noticeDetail.css">
<!-- 스타일 여기 적어주시면 가능 -->

<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진로 탐색</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn">
			<a href="/csc/noticeList.do">공지사항</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/csc/faqList.do">FAQ</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/csc/inqryList.do">1:1문의</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="detail-box">
				<div class="detail-title">
					<span class="num"> <fmt:formatNumber
							value="${noticeDetail.noticeId}" pattern="000" />
					</span> &nbsp; ${noticeDetail.noticeTitle}
				</div>
				<div class="detail-meta">
					번호: ${noticeDetail.noticeId} | 조회수: ${noticeDetail.noticeCnt} |
					작성일:
					<fmt:formatDate value="${noticeDetail.noticeCreatedAt}"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</div>
				<hr class="detail-divider" />
				<div class="detail-content">${noticeDetail.noticeContent}</div>
				<!-- 첨부 파일 -->
				<c:if test="${not empty noticeDetail.getFileList}">
					<div class="attachment-box">
						<div class="attachment-label">📥 다운로드 &nbsp;|</div>
						<div class="attachment-file-list">
							<c:forEach var="file" items="${noticeDetail.getFileList}">
								<div class="file-item" onclick="filedownload(${file.fileGroupId}, ${file.fileSeq}, '${file.fileOrgName}')">
								    ${file.fileOrgName}
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
			</div>
			<div class="goList">
				<a href="/csc/noticeList.do">목 록</a>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	</body>
	</html>

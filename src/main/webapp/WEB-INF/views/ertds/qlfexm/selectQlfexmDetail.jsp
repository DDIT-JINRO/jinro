<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/qlfexm/selectQlfexmDetail.css">
<section class="channel">
	<div class="channel-title">
		<div class="channel-title-text">진학 정보</div>
	</div>
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a>
		</div>
	</div>
</section>

<div class="public-wrapper">
	<div class="tab-container" id="tabs">
		<h3 class="page-title-bar__title">검정고시</h3>
	</div>

	<div class="public-wrapper-main">
		<div class="detail-box">
			<div class="detail-title">
				<span class="num">
					<fmt:formatNumber value="${qualficationExamVO.examId}" pattern="000" />
				</span>
				&nbsp;
				<span style="font-size: 20px; font-weight: bold;">${qualficationExamVO.examTitle}</span>
			</div>
			<div class="detail-meta">
				번호: ${qualficationExamVO.examId} | 교육기관: ${qualficationExamVO.examAreaCode} | 작성일:
				<fmt:formatDate value="${qualficationExamVO.examNotiDate}" pattern="yyyy-MM-dd" />
			</div>
			<hr class="detail-divider" />
			<div class="detail-content">${qualficationExamVO.examContent}</div>
		</div>
		<div class="goList">
			<a href="/ertds/qlfexm/selectQlfexmList.do">목 록</a>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>

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
		<div class="detail__header-wrapper">
			<div class="detail__header">
				<span class="detail__badge">
					<fmt:formatNumber value="${qualficationExamVO.examId}" pattern="000" />
				</span>
				<h1 class="detail__title">${qualficationExamVO.examTitle}</h1>
			</div>
			<div class="detail__meta">
				<span class="detail__meta-item">번호: ${qualficationExamVO.examId}</span>
				<span class="detail__meta-item">교육기관: ${qualficationExamVO.examAreaCode}</span>
				<span class="detail__meta-item">
					작성일:
					<fmt:formatDate value="${qualficationExamVO.examNotiDate}" pattern="yyyy-MM-dd" />
				</span>
				<span class="detail-header__meta-item detail-header__meta-item--source">[ 출처 : 국가평생교육진흥원 검정고시지원센터 ]</span>
			</div>
		</div>
			<hr class="detail__divider" />
			
		<div class="detail__content">
			<div class="exam-notice notice-content">${qualficationExamVO.examContent}</div>
		</div>
		
		<div class="detail__back-to-list">
			<a href="/ertds/qlfexm/selectQlfexmList.do" class="detail__action-button">목 록</a>
		</div>
	</div>

</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>

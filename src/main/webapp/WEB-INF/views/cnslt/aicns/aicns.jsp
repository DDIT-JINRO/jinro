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
		<div class="channel-sub-section-item">
			<a href="/cnslt/rvw/cnsReview.do">상담 후기</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/cnslt/aicns/aicns.do">AI 상담</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">

		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript" src="/js/cnslt/rvw/cnsReview.js"></script>
</body>
</html>
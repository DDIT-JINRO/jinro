<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
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
		<div class="channel-sub-section-item"><a href="/csc/faq/faqList.do">FAQ</a></div>
		<div class="channel-sub-section-itemIn"><a href="/csc/inq/inqryList.do">1:1문의</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">

		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			<h3>
  			1:1문의 LIST<br>
  			DETAIL은 아코디언으로
  			</h3>
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<link rel="stylesheet" href="">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진로 탐색</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="#">진로 심리검사</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="#">직업백과</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<!-- 공지사항 리스트 이동 -->
		    <div class="tab "><a href="/noticeList"  style="text-decoration: none; color: inherit;">공지사항</a></div>
		    <div class="tab active"><a href="/FAQList"  style="text-decoration: none; color: inherit;">FAQ</a></div>
		    <div class="tab"><a href="/INQRYList"  style="text-decoration: none; color: inherit;">1:1문의</a></div>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			<h3>
  			FAQ LIST<br>
  			DETAIL은 아코디언으로
  			</h3>
  		</div>
	</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
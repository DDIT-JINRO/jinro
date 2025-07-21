<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">커뮤니티</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/comm/peer/teen/teenList.do">또래 게시판</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/comm/path/pathList.do">진로/진학 게시판</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
<!-- 		<div class="tab-container" id="tabs"> -->
<!-- 		    <div class="tab ">대학 검색</div> -->
<!-- 		    <div class="tab active">학과 정보</div> -->
<!-- 		    <div class="tab">입시 정보</div> -->
<!-- 		    <div class="tab">입시 정보</div> -->
<!--   		</div> -->
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			청년 상세 페이지
  			${boardId } 번 게시글
  			<br/>
  			<br/><br/><br/><br/>
  			<a href="/comm/peer/youth/youthList.do">청년 목록</a>
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
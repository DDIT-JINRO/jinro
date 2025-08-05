<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진학 정보</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a></div>
		<div class="channel-sub-section-item"><a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<!--
			<div class="tab-container" id="tabs">
	 		    <div class="tab active ">고등학교 검색</div>
				<div class="tab">학과 정보</div>
	  		</div>
  		-->
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			여기가 작성해야할 공간입니다. 고등학교 상세
  			</br></br></br></br>
  			<a href="/ertds/hgschl/selectHgschList.do">목록</a>
  			</br></br></br></br>
  			</br></br></br></br>
  			</br></br></br></br>
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진로 탐색</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item"><a href="/pse/cat/careerAptitudeTest.do">진로 심리검사</a></div>
		<div class="channel-sub-section-itemIn"><a href="/pse/cr/crl/selectCareerList.do">직업백과</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <div class="tab active"><a href="/pse/cr/crl/selectCareerList.do">직업 목록</a></div>
		    <div class="tab"><a href="/pse/cr/crr/selectCareerRcmList.do">추천 직업</a></div>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			이곳은 직업 리스트입니다.
  			<a href="/pse/cr/crl/selectCareerDetail.do">상세 화면 가기</a>
  			<a href="/pse/cr/cco/careerComparisonView.do">비교 화면 가기</a>
  			</br></br></br></br>
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
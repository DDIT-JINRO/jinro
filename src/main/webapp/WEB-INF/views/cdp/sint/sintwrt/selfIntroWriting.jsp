<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">경력관리</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item"><a href="/rsm/rsm">이력서</a></div>
		<div class="channel-sub-section-itemIn"><a href="/sint/qestnlst">자기소개서</a></div>
		<div class="channel-sub-section-item"><a href="/imtintrvw/bsintrvw">모의면접</a></div>
		<div class="channel-sub-section-item"><a href="/aifdbck/rsm">AI 피드백</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <div class="tab"><a href="/sint/qestnlst">질문 리스트</a></div>
		    <div class="tab"><a href="/sint/sintlst">자기소개서 리스트</a></div>
		    <div class="tab active"><a href="/sint/sintwrt">자기소개서 작성</a></div>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			자기소개서 작성 
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
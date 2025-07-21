<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">프로그램</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/prg/ctt/cttList.do">공모전</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/prg/act/vol/volList.do">대외활동</a></div>
		<div class="channel-sub-section-item"><a href="/prg/std/stdGroupList.do">스터디그룹</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab" href="/prg/act/vol/volList.do">봉사활동</a>
		    <a class="tab" href="/prg/act/cr/crList.do">직업체험</a>
		    <a class="tab active" href="/prg/act/sup/supList.do">서포터즈</a>
  		</div>
  		<div class="public-wrapper-main">
  			서포터즈 목록
  			<br/>
			<a href="/prg/act/sup/supDetail.do?supId=1">서포터즈 상세 1번글</a>
			<br/>
			<a href="/prg/act/sup/supDetail.do?supId=2">서포터즈 상세 2번글</a>
			<br/>
			<a href="/prg/act/sup/supDetail.do?supId=3">서포터즈 상세 3번글</a>
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
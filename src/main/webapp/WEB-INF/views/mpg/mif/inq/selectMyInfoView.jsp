<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">마이페이지</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-itemIn"><a href="/mypage/mpg/mif/inq/selectMyInfoView.do">내 정보</a></div>
		<div class="channel-sub-section-item"><a href="#">나의 활동</a></div>
		<div class="channel-sub-section-item"><a href="#">결제 구독내역</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <div class="tab active"><a href="/mypage/mpg/mif/inq/selectMyInfoView.do">조회 및 수정</a></div>
		    <div class="tab"><a href="/mypage/mpg/mif/pswdchg/selectPasswordChangeView.do">비밀번호 변경</a></div>
		    <div class="tab"><a href="/mypage/mpg/mif/whdwl/selectWithdrawalView.do">회원 탈퇴</a></div>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			이곳은 마이페이지 입니다.
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
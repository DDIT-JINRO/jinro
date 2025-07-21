<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/main.css" />
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function () {
	  const banner = document.querySelector('.main-loadmap-banner');
	  banner.classList.add('animate-in');
});
</script>
<div class="mainContainer">
	<section class="main-loadmap-banner">
		<div class="banner-inner">
			<div class="banner-left">
				<img alt="" src="/images/logo.png">
				<div class="main-title-banner">미래를 여는 길</div>
				<p>
					진로와 진학, 취업을 위한 종합 정보 제공<br /> 미래를 설계하는 데 필요한 모든 것
				</p>
				<button class="view-button">로드맵 바로가기</button>
			</div>
			<div class="banner-right floating-icons">
				<img src="/images/main/loadMapIcon-board.png" class="float-icon1 delay1" />
				<img src="/images/main/loadMapIcon-charactor.png" class="float-icon-charactor delay2" />
				<img src="/images/main/loadMapIcon-book.png" class="float-icon1 delay3" />
				<img src="/images/main/loadMapIcon-book2.png" class="float-icon2 delay4" />
				<img src="/images/main/loadMapIcon-chart.png" class="float-icon2 delay5" />
				<img src="/images/main/loadMapIcon-cogwheel.png" class="float-icon3 delay6" />
				<img src="/images/main/loadMapIcon-lamp.png" class="float-icon3 delay7" />
				<img src="/images/main/loadMapIcon-reading_glasses.png" class="float-icon1 delay8" />
			</div>
		</div>
	</section>
	<section class="main-event-banner">
		<div class="center-jr">
			<img alt="" src="/images/logo.png">
		</div>
	</section>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/main.css" />
<%@ include file="/WEB-INF/views/include/header.jsp"%>
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
			<p class="main-event-title">Career Path</p>
		</div>
		<div class="center-jr">
			<p class="main-event-sub">우리는</p>
			<p class="main-event-sub-bold">&nbsp진로와 진학,</p>
			<p class="main-event-sub-bold">&nbsp취업</p>
			<p class="main-event-sub">의 모든 과정을 지원합니다. 자신의 꿈을 이룰 수 있도록 다양한 정보를 제공합니다</p>
		</div>
		<div class="main-slider">
			<div class="main-slides">
				<a href="#">
					<img src="images/main/배너테스트1.png" alt="img1" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트2.png" alt="img2" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트3.png" alt="img3" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트4.png" alt="img4" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트5.png" alt="img5" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트6.png" alt="img6" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트1.png" alt="img1" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트2.png" alt="img2" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트3.png" alt="img3" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트4.png" alt="img4" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트5.png" alt="img5" class="main-event-bannerImg" />
				</a>
				<a href="#">
					<img src="images/main/배너테스트6.png" alt="img6" class="main-event-bannerImg" />
				</a>
			</div>
		</div>
	</section>
	<section class="main-contents-section">
		<div class="main-contents-flex">
			<div class="main-contents-card">
				<p class="main-contents-title">직업 이상형 월드컵</p>
				<div class="content-box">
					<div class="fixed-image">
						<img src="/images/main/charactor1.png" alt="캐릭터" class="charactor-img1" /> <img alt="" src="/images/main/charactor4.png" class="charactor-img3">
					</div>
					<div class="text-content">
						<p>
							총 64개의 직업들 중<br />자신이 선호하는 직업이 무엇인지,
						</p>
						<p>
							다른 사람들의 선호 직업은<br />무엇인지 확인할 수 있습니다
						</p>
						<button class="text-content-btn">직업 이상형 월드컵</button>
					</div>
				</div>
			</div>
			<div class="main-contens-card">
				<p class="main-contents-title">진로 심리 검사</p>
				<div class="content-box">
					<div class="fixed-image">
						<img src="/images/main/charactorChalsak.png" alt="캐릭터" class="charactor-img1Sec" /> <img src="/images/main/charactor3.png" alt="캐릭터" class="charactor-img2" />
					</div>
					<div class="text-content">
						<p>
							여러 검사들을 통해<br /> 나의 직업 성향과 특성을 이해하고,
						</p>
						<p>
							자신이 선호하는 직업이 무엇인지,<br /> 자신에게 적합한 진로를 탐색해 보세요
						</p>
						<button class="text-content-btn">진로 심리 검사</button>
					</div>
				</div>
			</div>
		</div>
	</section>


	<section class="main-youtube-section">
		<a href="/main/youtubeJsp">유튜브 API 확인 </a>
		<a href="/pse/test">크롤링 확인</a>
		<div class="section-header">
			<p id="typing-js" class="typing-js"></p>
		</div>
		<div class="card-list">
			<div class="main-card">
				<iframe class="youtube-iframe" width="300" height="215" src="https://www.youtube.com/embed/U5GU0uoKEUs" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen=""> </iframe>
			</div>
			<div class="main-card"></div>
			<div class="main-card"></div>
			<div class="main-card"></div>
		</div>
	</section>
	<section class="main-community-section">
		<div class="main-communituy-section-header">
			<p>다른 사용자들과 진로 정보와 고민을 나눠보세요.</p>
		</div>
	</section>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script src="/js/content/main.js"></script>

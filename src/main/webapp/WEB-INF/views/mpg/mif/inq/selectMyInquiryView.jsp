<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/mpg/mif/inq/selectMyInquiryView.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">마이페이지</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-itemIn">
			<a href="/mpg/mif/inq/selectMyInquiryView.do">내 정보</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/mpg/mat/bmk/selectBookMarkList.do">나의 활동</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/mpg/pay/selectPaymentView.do">결제 구독내역</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab active" href="/mpg/mif/inq/selectMyInquiryView.do">조회 및 수정</a>
			<a class="tab" href="/mpg/mif/pswdchg/selectPasswordChangeView.do">비밀번호 변경</a>
			<a class="tab" href="/mpg/mif/whdwl/selectWithdrawalView.do">회원 탈퇴</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="profile-layout">
				<div class="profile-row">
					<div class="profile-card profile-photo-card">
						<h3 class="card-title">프로필 사진</h3>
						<div class="profile-photo-area">
							<img src="/images/defaultProfileImg.png" alt="프로필 사진" class="profile-img">
							<div class="profile-photo-buttons">
								<button class="btn btn-primary">사진 변경</button>
								<button class="btn btn-primary">테두리 변경</button>
							</div>
						</div>
					</div>

					<div class="profile-card my-info-card">
						<div class="card-header">
							<div class="card-title-wrapper">
								<h3 class="card-title">나의 정보</h3>
							</div>
							<div class="social-login">
								<img src="/images/kakao-img.png" alt="카카오톡"> <img src="/images/naver-img.png" alt="네이버">
							</div>
						</div>
						<div class="info-grid">
							<div class="info-item">
								<label>이름</label>
								<input value="가나다" />
							</div>
							<div class="info-item">
								<label>닉네임</label>
								<input value="닉네임" />
							</div>
							<div class="info-item">
								<label>이메일</label>
								<input value="testuser@gmail.com" />
							</div>
							<div class="info-item">
								<label>성별</label>
								<span>여</span>
							</div>
							<div class="info-item">
								<label>연락처</label>
								<span>000-0000-0000</span>
								<button class="btn btn-auth">번호 변경</button>
							</div>
							<div class="info-item">
								<label>생년월일</label>
								<span>0000년 00월 00일</span>
							</div>
							<div class="info-item">
								<label>포인트</label>
								<span>00,000포인트</span>
							</div>
							<div class="info-item profile-update">
								<button class="btn btn-secondary disable">수정</button>
							</div>
						</div>
					</div>
				</div>

				<div class="profile-row">
					<div class="profile-card my-interest-card">
						<div class="card-header">
							<h3 class="card-title">나의 관심 분야</h3>
							<button class="btn btn-primary">수정</button>
						</div>
						<div class="tags-container">
							<span class="tag">관심 키워드</span>
							<span class="tag">관심 키워드</span>
							<span class="tag">관심 키워드</span>
						</div>
					</div>

					<div class="profile-card my-subscription-card">
						<div class="card-header">
							<h3 class="card-title">나의 구독 상태</h3>
							<div class="subscription-header-right">
								<span class="days-remaining">구독 만료까지 15일 남았어요!</span>
							</div>
						</div>
						<div class="subscription-status-box">
							<p class="plan-name">BASIC</p>
							<p class="plan-desc">베이직 등급일 수 있습니다.</p>
						</div>
					</div>
				</div>

				<div class="profile-card tiers-info-card">
					<div class="card-header">
						<h3 class="card-title">커리어패스 등급 소개</h3>
					</div>
					<div class="tiers-table">
						<div class="tiers-row is-header">
							<div class="tier-cell feature-title"></div>
							<div class="tier-cell">
								💎&nbsp;DIAMOND
								<br>
								<small>5,000,000 P</small>
							</div>
							<div class="tier-cell">
								🏆&nbsp;PLATINUM
								<br>
								<small>2,500,000 P</small>
							</div>
							<div class="tier-cell">
								🥇&nbsp;GOLD
								<br>
								<small>500,000 P</small>
							</div>
							<div class="tier-cell">
								🥈&nbsp;SILVER
								<br>
								<small>100,000 P</small>
							</div>
							<div class="tier-cell">
								🥉&nbsp;BRONZE
								<br>
								<small>50,000 P</small>
							</div>
							<div class="tier-cell">
								🌱&nbsp;SEED
								<br>
								<small>0 P</small>
							</div>
						</div>
						<div class="tiers-row">
							<div class="tier-cell feature-title">무제한 이용권</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">✔</div>
						</div>
						<div class="tiers-row">
							<div class="tier-cell feature-title">전용 게시판</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
						</div>
						<div class="tiers-row">
							<div class="tier-cell feature-title">전용 서비스</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
						</div>
						<div class="tiers-row">
							<div class="tier-cell feature-title">광고 제거</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
						</div>
						<div class="tiers-row">
							<div class="tier-cell feature-title">전용 이벤트</div>
							<div class="tier-cell">✔</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
							<div class="tier-cell">-</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
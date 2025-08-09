<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/mpg/mif/inq/selectMyInquiryView.css">

<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel" data-success-message="${successMessage}" data-error-message="${errorMessage}">
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
							<div class="profile-wrapper">
								<img class="profile-img" src="<c:out value="${!empty member.profileFilePath ? member.profileFilePath : '/images/defaultProfileImg.png' }"/>" alt="프로필이미지" />
								
								<c:if test="${!empty member.badgeFilePath}">
									<img class="badge-img" src="${member.badgeFilePath}" alt="테두리이미지"/>
								</c:if>
								<c:if test="${!empty member.subFilePath}">
								    <img class="effect-img sparkle" src="${member.subFilePath}" alt="효과이미지"/>
								</c:if>
							</div>
							
							<div class="profile-photo-buttons">
								<input type="file" id="change-photo-input" style="display: none;" accept="image/jpeg, image/png" />
								<button type="button" class="btn btn-primary" id="change-photo-btn">사진 변경</button>
							</div>
						</div>
					</div>

					<div class="profile-card my-info-card">
						<div class="card-header">
							<div class="card-title-wrapper">
								<h3 class="card-title">나의 정보</h3>
							</div>
							<div class="info-item profile-update">
								<button type="button" id="info-update-btn" class="btn btn-primary disable" disabled="disabled">수정</button>
							</div>
						</div>
						<form action="updateMyInquiryDetail.do" method="POST">
							<div class="info-grid">
								<div class="info-item">
									<label>이름</label>
									<input value="${member.memName}" name="memName" data-init-value="${member.memName}" />
								</div>
								<div class="info-item">
									<label>닉네임</label>
									<input value="${member.memNickname}" name="memNickname" data-init-value="${member.memNickname}" />
								</div>
								<div class="info-item">
									<label>이메일</label>
									<span>${member.memEmail}</span>
								</div>
								<div class="info-item">
									<label>성별</label>
									<c:if test="${member.memGen eq 'G11001'}">
										<span>남</span>
									</c:if>
									<c:if test="${member.memGen eq 'G11002'}">
										<span>여</span>
									</c:if>
								</div>
								<div class="info-item">
									<label>연락처</label>
									<span>${member.memPhoneNumber}</span>
									<button type="button" class="btn btn-auth">번호 변경</button>
								</div>
								<div class="info-item">
									<label>생년월일</label>
									<span>
										<fmt:formatDate pattern="yyyy년 MM월 dd일" value="${member.memBirth}" />
									</span>
								</div>
								<div class="info-item">
									<label>포인트</label>
									<span>${member.memPoint} 포인트</span>
								</div>
								<div class="info-item">
									<label>학생 여부</label>
									<c:choose>
										<c:when test="${member.veriStatus == 'S05001'}">
											<span>신청 중</span>
											<small> <fmt:formatDate pattern="(yyyy년 MM월 dd일)" value="${member.veriCreatedAt}" />
											</small>
										</c:when>
										<c:when test="${member.veriStatus == 'S05002'}">
											<span>인증</span>
										</c:when>
										<c:when test="${member.veriStatus == 'S05003'}">
											<span>반려</span>
											<button type="button" id="reject-reason-btn" class="btn btn-secondary">반려 이유</button>
										</c:when>
										<c:otherwise>
											<span>미인증</span>
											<button type="button" id="auth-student" class="btn btn-auth">학생 인증</button>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</form>
					</div>
				</div>

				<div class="profile-row">
					<div class="profile-card my-interest-card">
						<div class="card-header">
							<h3 class="card-title">나의 관심 분야</h3>
							<button class="btn btn-primary" id="interests-update-btn">수정</button>
						</div>
						<div class="tags-container">
							<c:if test="${empty member.interests}">
							    <span>선택한 관심 분야가 없습니다.</span>
							</c:if>
							<c:forEach var="userKeyword" items="${member.interests}">
								<span class="tag">${userKeyword.ccName}</span>
							</c:forEach>
						</div>
					</div>

					<div class="profile-card my-subscription-card">
						<c:if test="${member.remainingDays == 0}">
							<div class="card-header">
								<h3 class="card-title">나의 구독 상태</h3>
								<div class="subscription-header-right">
									<button type="button" class="btn btn-primary" id="move-sub-btn">구독하러 가기</button>
								</div>
							</div>
							<p class="plan-desc">구독 중인 상품이 없습니다.</p>
						</c:if>
						<c:if test="${member.remainingDays != 0}">
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
						</c:if>
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

<div class="modal-overlay" id="password-modal-overlay">
	<div class="modal-content">
		<button class="modal-close-btn" type="button">&times;</button>
		<h3>비밀번호 확인</h3>
		<p>회원 정보를 안전하게 보호하기 위해 비밀번호를 다시 한번 입력해 주세요.</p>
		<div class="modal-form">
			<input type="password" id="password-check-input" placeholder="비밀번호를 입력하세요">
			<span class="modal-error-msg" id="modal-error-msg"></span>
			<button class="btn btn-primary" id="password-confirm-btn" type="button">인증</button>
		</div>
	</div>
</div>

<div class="modal-overlay" id="interest-modal-overlay">
	<div class="modal-content interests-modal">
		<button class="modal-close-btn" type="button">&times;</button>
		<h3>관심사 수정</h3>
		<p>수정 할 관심 분야를 선택하세요.</p>
		<div class="modal-form keyword-modal">
			<form method="post" action="insertInterestList.do">
				<div class="com-filter-section">
					<div class="com-filter-options">
						<c:forEach items="${interetsKeywordList}" var="allKeyword">
							<c:set var="isChecked" value="false" />
							<c:forEach var="userKeyword" items="${member.interests}">
								<c:if test="${allKeyword.ccId == userKeyword.ccId }">
									<c:set var="isChecked" value="true" />
								</c:if>
							</c:forEach>

							<label class="com-filter-item">
								<input type="checkbox" name="filter-keyword" value="${allKeyword.ccId}" ${isChecked ? "checked" : ""}>
								<span>${allKeyword.ccName}</span>
							</label>
						</c:forEach>
					</div>
				</div>
				<div class="com-filter-section">
					<div class="com-button-container">
						<label class="com-filter-title">선택된 관심 분야</label>
						<button type="button" class="com-filter-reset-btn">초기화</button>
					</div>
					<div class="com-selected-filters">
						<c:forEach var="userKeyword" items="${member.interests}">
							<span class="com-selected-filter" data-filter="${userKeyword.ccName}">${userKeyword.ccName}</span>
						</c:forEach>
					</div>
				</div>
				<button type="submit" class="com-submit-search-btn">수정</button>
			</form>
		</div>
	</div>
</div>

<div class="modal-overlay" id="student-modal-overlay">
	<div class="modal-content">
		<button class="modal-close-btn" type="button">&times;</button>
		<h3>학생 인증 신청</h3>
		<p>학생 회원 인증을 위하여 증빙서류를 첨부해주세요.</p>
		<div class="modal-form">
			<input type="file" id="student-auth-input">
			<span class="modal-error-msg" id="modal-error-msg"></span>
			<button class="btn btn-primary" id="student-confirm-btn" type="button">인증</button>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script src="/js/mpg/mif/inq/selectMyInquiryView.js"></script>
</html>
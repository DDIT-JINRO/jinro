<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/rsm/rsm/Resume.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">경력관리</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-itemIn"><a href="/rsm/rsm">이력서</a></div>
		<div class="channel-sub-section-item"><a href="/cdp/sint/qestnlst/questionList.do">자기소개서</a></div>
		<div class="channel-sub-section-item"><a href="/cdp/imtintrvw/intrvwitr/interviewIntro.do">모의면접</a></div>
		<div class="channel-sub-section-item"><a href="/aifdbck/rsm">AI 피드백</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab active" href="/rsm/rsm">이력서</a>
		    <a class="tab" href="/cdp/rsm/rsmb/resumeBoardList.do">이력서 템플릿 게시판</a>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			이력서 페이지
			<section class="personal-info-section">

				<form class="personal-info-form">
					<div class="resume-title">
						<input type="text" name="resumeTitle" placeholder="제목을 입력해주세요."
							required>
					</div>
					<div class="section-header">
						<h2>인적사항</h2>
						<p class="required-info">* 필수 입력 정보입니다.</p>
					</div>
					<div class="form-profile-grid">
						<div class="form-group name-group">
							<label for="name">이름 <span class="required-asterisk">*</span></label>
							<input type="text" id="name" name="name"
								placeholder="이름을 입력해주세요." required> <span
								class="error-message" id="name-error">이름을 입력해주세요.</span>
						</div>

						<div class="form-group dob-group">
							<label for="dob">생년월일 <span class="required-asterisk">*</span></label>
							<input type="date" id="dob" name="dob" required> <span
								class="error-message" id="dob-error">생년월일을 입력해주세요.</span>
						</div>

						<div class="form-group gender-group">
							<label for="gender">성별 <span class="required-asterisk">*</span></label>
							<select id="gender" name="gender" required>
								<option value="">선택</option>
								<option value="male">남자</option>
								<option value="female">여자</option>
							</select> <span class="error-message" id="gender-error">성별을 선택해주세요.</span>
						</div>

						<div class="form-group photo-group">
							<label for="photo-upload" class="photo-upload-area"> <i
								class="fa fa-plus" aria-hidden="true"></i> <span>사진추가</span> <input
								type="file" id="photo-upload" name="photo" accept="image/*"
								class="sr-only">
							</label>
						</div>
						<div class="form-group email-group">
							<label for="email">이메일 <span class="required-asterisk">*</span></label>
							<input type="email" id="email" name="email" placeholder="이메일"
								required> <span class="error-message" id="email-error">이메일을
								입력해주세요.</span>
						</div>


						<div class="form-group phone-group">
							<label for="phone">전화번호</label> <input type="tel" id="phone"
								name="phone" placeholder="전화번호">
						</div>

						<div class="form-group mobile-phone-group">
							<label for="mobile-phone">휴대폰번호 <span
								class="required-asterisk">*</span></label> <input type="tel"
								id="mobile-phone" name="mobile-phone"
								placeholder="휴대폰번호를 입력해주세요." required> <span
								class="error-message" id="mobile-phone-error">휴대폰번호를
								입력해주세요.</span>
						</div>

						<div class="form-group address-group">
							<label for="address">주소</label>
							<div class="input-with-icon">
								<input type="text" id="address" name="address" placeholder="">
								<i class="fa fa-search icon-search" aria-label="주소 검색"></i>
							</div>
						</div>
					</div>
					<div class="form-JobWish">
					<div class="JobWish-header">
						<label for="desired-job">희망 직무</label>
						<button type="button" id="add-job">추가</button>
					</div>
						<div class="job-input-group">
							<input type="text" id="desired-job" name="desired-job"
								placeholder="희망 직무를 입력하세요" required>
						</div>
					</div>

					<div class="form-Skills">
						<div class="Skills-header">
							<label for="skills">스킬</label>
							<button type="button" id="add-skill">추가</button>
						</div>
						<div class="skills-input-group">
							<input type="text" id="skills" name="skills"
								placeholder="스킬을 입력하세요" required>
						</div>
					</div>
					
				</form>
			</section>
			<button type="submit">제출</button>
			<div class="button-group">
				<!-- 학력 불러오기 버튼 -->
				<button type="button" id="load-education" name="rsId" data-id="1">학력
					불러오기</button>

				<!-- 자격증 불러오기 버튼 -->
				<button type="button" id="load-certificate" name="rsId" data-id="2">자격증
					불러오기</button>
					
				<!-- 대외활동 불러오기 버튼 -->
				<button type="button" id="load-activities" name="rsId" data-id="3">대외활동</button>
			</div>
			<a href="/rsm/rsm/detail.do">이력서 디테일</a>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script type="text/javascript" src="/js/cdp/rsm/rsm/Resume.js"></script>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
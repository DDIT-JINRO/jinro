<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/aifdbck/rsm/aiFeedbackResume.css">
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
		<div class="channel-sub-section-item"><a href="/sint/qestnlst">자기소개서</a></div>
		<div class="channel-sub-section-item"><a href="/imtintrvw/bsintrvw">모의면접</a></div>
		<div class="channel-sub-section-itemIn"><a href="cdp/aifdbck/rsm/aiFeedbackResumeList.do">AI 피드백</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab active" href="/cdp/aifdbck/rsm/aiFeedbackResumeList.do">이력서</a>
		    <a class="tab" href="/cdp/aifdbck/sint/aiFeedbackSelfIntroList.do">자기소개서</a>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			AI 피드백 이력서
  			<a href="/aifdbck/rsm/detail.do">AI 피드백 이력서 디테일</a>
  			<div class="public-wrapper-main">
			<!-- 자기소개서 목록 드롭다운 -->
			<select class="self-intro-select" id="selfIntroList">
				<option value="">내가 작성한 자기소개서 리스트를 선택하세요.</option>
				<c:forEach var="intro" items="${selfIntroList}">
					<option value="${intro.siId}">${intro.siTitle}</option>
				</c:forEach>
			</select> <br /> <br />
			<div class="aifb-container">
				<!-- 자기소개서 원본 입력 영역 -->
				<h1 class="aifb-title">이력서 제목</h1>
				<div class="aifb-content-wrapper">
					<div class="aifb-section">
						<div class="aifb-section-header">
							<h2>이력서</h2>
						</div>
						<div class="aifb-questions-wrapper" id="questionsWrapper">
							이력서의 내용이 출력될 공간입니다</div>
					</div>

					<!-- AI 피드백 출력 영역 -->
					<div class="aifb-feedback-wrapper">
						<div class="aifb-feedback-header">
							<h2>AI 피드백</h2>
						</div>
						<div class="aifb-feedback-area" id="feedbackArea">AI의 피드백 내용이 출력될 공간입니다</div>
					</div>
				</div>

				<div class="aifb-button-wrapper">
					<button class="aifb-button back" onclick="history.back()">뒤로가기</button>
					<button class="aifb-button proofread" onclick="requestProofread()">내
						자기소개서 수정하러 가기</button>
				</div>
			</div>
		</div>
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
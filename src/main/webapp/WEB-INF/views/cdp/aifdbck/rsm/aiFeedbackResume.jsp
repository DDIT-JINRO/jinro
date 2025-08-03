<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/aifdbck/rsm/aiFeedbackResume.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<section class="channel">
	<div class="channel-title">
		<div class="channel-title-text">경력관리</div>
	</div>
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item"><a href="/cdp/rsm/rsm/resumeList.do">이력서</a></div>
		<div class="channel-sub-section-item"><a href="/cdp/sint/qestnlst/questionList.do">자기소개서</a></div>
		<div class="channel-sub-section-item"><a href="/cdp/imtintrvw/intrvwitr/interviewIntro.do">모의면접</a></div>
		<div class="channel-sub-section-itemIn"><a href="/cdp/aifdbck/rsm/aiFeedbackResumeList.do">AI 피드백</a></div>
	</div>
</section>

<div>
	<div class="public-wrapper">
		<div class="tab-container" id="tabs">
		    <a class="tab active" href="/cdp/aifdbck/rsm/aiFeedbackResumeList.do">이력서</a>
		    <a class="tab" href="/cdp/aifdbck/sint/aiFeedbackSelfIntroList.do">자기소개서</a>
  		</div>

  		<div class="public-wrapper-main">
  			AI 피드백 이력서
  			<a href="/aifdbck/rsm/detail.do">AI 피드백 이력서 디테일</a>
  			<div class="public-wrapper-main">

				<!-- 이력서 목록 드롭다운 -->
				<select class="resume-select" id="resumeList">
					<option value="">내가 작성한 이력서를 선택하세요.</option>
					<c:forEach var="resume" items="${resumeList}">
						<option value="${resume.resumeId}">${resume.resumeTitle}</option>
					</c:forEach>
				</select>

				<br /> <br />

				<div class="aifb-container">
					<!-- 이력서 원본 입력 영역 -->
					<h1 class="aifb-title">이력서 제목</h1>
					<div class="aifb-content-wrapper">
						<div class="aifb-section">
							<div class="aifb-section-header">
								<h2>이력서</h2>
							</div>
							<div class="aifb-questions-wrapper" id="questionsWrapper">
								이력서의 내용이 출력될 공간입니다
							</div>
						</div>

						<!-- AI 피드백 출력 영역 -->
						<div class="aifb-feedback-wrapper">
							<div class="aifb-feedback-header">
								<h2>AI 피드백</h2>
							</div>
							<div class="aifb-feedback-area" id="feedbackArea">
								AI의 피드백 내용이 출력될 공간입니다
							</div>
						</div>
					</div>

					<div class="aifb-button-wrapper">
						<button class="aifb-button back" onclick="history.back()">뒤로가기</button>
						<button class="aifb-button proofread" onclick="requestProofread()">내 이력서 수정하러 가기</button>
					</div>
				</div>
			</div>
  		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>

<script src="/js/cdp/aifdbck/rsm/aiFeedbackResume.js" defer></script>
<script>
	// 이력서 선택 → AI 피드백 요청 등 기능 여기에 구현 예정
</script>

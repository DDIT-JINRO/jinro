<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangement.css">
<section class="channel">
	<div class="channel-title">
		<div class="channel-title-text">경력관리</div>
	</div>
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/cdp/rsm/rsm/resumeList.do">이력서</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/cdp/sint/qestnlst/questionList.do">자기소개서</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/cdp/imtintrvw/intrvwitr/interviewIntro.do">모의면접</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/cdp/aifdbck/rsm/aiFeedbackResumeList.do">AI 피드백</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="tab-container" id="tabs">
			<a class="tab" href="/cdp/imtintrvw/intrvwitr/interviewIntro.do">면접의 기본</a>
			<a class="tab" href="/cdp/imtintrvw/intrvwqestnlst/intrvwQuestionList.do">면접 질문 리스트</a>
			<a class="tab active" href="/cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangementList.do">면접 질문 관리</a>
			<a class="tab" href="/cdp/imtintrvw/aiimtintrvw/aiImitationInterview.do">AI 모의 면접</a>
		</div>
		<div class="public-wrapper-main">
			<form method="get" action="/cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangementList.do" class="intro-search-filter">
				<input type="text" name="keyword" value="${param.keyword}" placeholder="면접질문 제목 검색" class="intro-search-input" />

				<select name="status" class="intro-status-select">
					<option value="">전체 상태</option>
					<option value="작성중" <c:if test="${param.status eq '작성중'}">selected</c:if>>작성중</option>
					<option value="완료" <c:if test="${param.status eq '완료'}">selected</c:if>>완료</option>
				</select>

				<button type="submit" class="intro-search-btn">검색</button>
			</form>
			<div class="intro-list-section">
				<sec:authorize access="isAuthenticated()">
					<div class="intro-list-header">총 ${articlePage.total}건</div>
				</sec:authorize>

				<c:forEach var="question" items="${articlePage.content}">
					<div class="intro-card">
						<div class="intro-title-section">
							<div class="intro-title">${question.idlTitle}</div>
							<div class="intro-meta">
								<span class="intro-date">
									수정일 :
									<fmt:formatDate value="${question.idlUpdatedAt}" pattern="yyyy. MM. dd (E) HH:mm" />
								</span>
								<span class="intro-status">
									상태 :
									<c:choose>
										<c:when test="${question.idlStatus eq '완료'}">완료</c:when>
										<c:when test="${question.idlStatus eq '작성중'}">임시 저장</c:when>
										<c:otherwise>미지정</c:otherwise>
									</c:choose>
								</span>
							</div>
						</div>
						<a href="/cdp/imtintrvw/intrvwqestnmn/detail.do?idlId=${question.idlId}"> 면접질문 수정하러 가기 </a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/pse/cr/cco/careerComparsionView.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진로 탐색</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/pse/cat/careerAptitudeTest.do">진로 심리검사</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/pse/cr/crl/selectCareerList.do">직업백과</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="comparison-grid columns-${jobsList.size()}">
			<div class="grid-placeholder"></div>
			<c:forEach var="job" items="${jobsList}">			
				<div class="job-card">
					<button class="close-btn">&times;</button>
					<h4>${job.jobName}</h4>
					<p>${job.jobMainDuty}</p>
					<a href="#" class="btn-detail">직업 상세보기</a>
				</div>
			</c:forEach>
			
			<div class="table-cell header">직업 대분류</div>
			<c:forEach var="job" items="${jobsList}">			
				<div class="table-cell">${job.jobLcl}</div>
			</c:forEach>

			<div class="table-cell header">직업 중분류</div>
			<c:forEach var="job" items="${jobsList}">			
				<div class="table-cell">${job.jobMcl}</div>
			</c:forEach>

			<div class="table-cell header">평균 학력</div>
			<c:forEach var="job" items="${jobsList}">			
				<div class="table-cell">${job.education}</div>
			</c:forEach>
			
			<div class="table-cell header">평균 연봉</div>
			<c:forEach var="job" items="${jobsList}">			
				<div class="table-cell">${job.averageSal}</div>
			</c:forEach>
			
			<div class="table-cell header">미래 전망</div>
			<c:forEach var="job" items="${jobsList}">			
				<div class="table-cell">${job.prospect}</div>
			</c:forEach>

			<div class="table-cell header">만족도</div>
			<c:forEach var="job" items="${jobsList}">			
				<div class="table-cell">${job.jobSatis}</div>
			</c:forEach>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
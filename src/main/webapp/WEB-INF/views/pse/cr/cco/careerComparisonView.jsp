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
	<%-- 이 코드를 기존 코드의 <div class="public-wrapper"> 부터 </div> 까지의 내용과 교체하세요. --%>
	<div class="public-wrapper">
		<%-- 테이블 형태로 직업 정보를 표시합니다. --%>
		<table class="comparison-table">
			<thead>
				<tr>
					<%-- 비교 항목 헤더가 들어갈 첫 번째 열은 비워둡니다. --%>
					<th></th>

					<%-- jobsList에 있는 직업 수만큼 직업 카드를 생성합니다. --%>
					<c:forEach var="job" items="${jobsList}">
						<th class="job-card-header">
							<div class="job-card">
								<button class="bookmark-btn ${job.isBookmark == job.jobTargetId ? 'active' : '' }" data-category-id="G03004" data-target-id="${job.jobTargetId}">
									<span class="icon-active">
										<img src="/images/bookmark-btn-active.png" alt="활성 북마크" width="30" height="30">
									</span>
									<span class="icon-inactive">
										<img src="/images/bookmark-btn-inactive.png" alt="비활성 북마크" width="30" height="30">
									</span>
								</button>
								<button class="close-btn">&times;</button>
								<h4>${job.jobName}</h4>
								<a href="/pse/cr/crl/selectCareerDetail.do?jobCode=${job.jobCode}" class="btn-detail">직업 상세보기</a>
							</div>
						</th>
					</c:forEach>
				</tr>
			</thead>
			<%-- careerComparsionView.jsp 파일의 <tbody> 부분을 아래와 같이 수정하세요. --%>
			<tbody>
				<tr>
					<th class="row-header">하는 일</th>
					<c:forEach var="job" items="${jobsList}">
						<td class="align-left">${job.jobMainDuty}</td>
					</c:forEach>
				</tr>
				<tr>
					<th class="row-header">직업 대분류</th>
					<c:forEach var="job" items="${jobsList}">
						<td>${job.jobLcl}</td>
					</c:forEach>
				</tr>
				<tr>
					<th class="row-header">직업 중분류</th>
					<c:forEach var="job" items="${jobsList}">
						<td>${job.jobMcl}</td>
					</c:forEach>
				</tr>
				<%-- ▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 여기부터 수정 ▼▼▼▼▼▼▼▼▼▼▼▼▼▼ --%>
				<tr>
					<%-- '평균 학력' 정렬을 위한 클래스와 데이터 속성 추가 --%>
					<th class="row-header sortable-header" data-sort-key="education">평균 학력 ↕</th>
					<c:forEach var="job" items="${jobsList}">
						<td>${job.education}</td>
					</c:forEach>
				</tr>
				<tr>
					<%-- '평균 연봉' 정렬을 위한 클래스와 데이터 속성 추가 --%>
					<th class="row-header sortable-header" data-sort-key="salary">평균 연봉 ↕</th>
					<c:forEach var="job" items="${jobsList}">
						<td>${job.averageSal}</td>
					</c:forEach>
				</tr>
				<tr>
					<%-- '미래 전망' 정렬을 위한 클래스와 데이터 속성 추가 --%>
					<th class="row-header sortable-header" data-sort-key="prospect">미래 전망 ↕</th>
					<c:forEach var="job" items="${jobsList}">
						<td>${job.prospect}</td>
					</c:forEach>
				</tr>
				<tr>
					<%-- '만족도' 정렬을 위한 클래스와 데이터 속성 추가 --%>
					<th class="row-header sortable-header" data-sort-key="satisfaction">만족도 ↕</th>
					<c:forEach var="job" items="${jobsList}">
						<td>${job.jobSatis}</td>
					</c:forEach>
				</tr>
				<%-- ▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 여기까지 수정 ▲▲▲▲▲▲▲▲▲▲▲▲▲▲ --%>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script src="/js/pse/cr/cco/careerComparsionView.js"></script>
</html>

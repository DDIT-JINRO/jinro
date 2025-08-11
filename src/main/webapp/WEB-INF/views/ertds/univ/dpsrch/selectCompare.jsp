<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/univ/dpsrch/deptCompare.css">
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진학 정보</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn">
			<a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab" href="/ertds/univ/uvsrch/selectUnivList.do">대학 검색</a>
			<a class="tab active" href="/ertds/univ/dpsrch/selectDeptList.do">학과 정보</a>
			<a class="tab" href="/ertds/univ/uvivfb/selectInterviewList.do">면접 후기</a>
		</div>
	</div>
</div>
<div>
	<div class="public-wrapper">
		<table class="comparison-table">
			<thead>
				<tr>
					<%-- 비교 항목 헤더가 들어갈 첫 번째 열은 비워둡니다. --%>
					<th></th>
					<c:forEach var="dept" items="${compareList}">
						<th class="dept-card-header">
							<div class="dept-card">
								<c:set var="isBookmarked" value="false" />

								<c:forEach var="bookmark" items="${bookMarkVOList}">
									<c:if test="${dept.uddId eq bookmark.bmTargetId}">
										<c:set var="isBookmarked" value="true" />
									</c:if>
								</c:forEach>

								<button class="bookmark-btn ${isBookmarked ? 'active' : ''}" data-category-id="G03006" data-target-id="${fn:escapeXml(dept.uddId)}">
									<span class="icon-active"> <img src="/images/bookmark-btn-active.png" alt="활성 북마크">
									</span> <span class="icon-inactive"> <img src="/images/bookmark-btn-inactive.png" alt="비활성 북마크">
									</span>
								</button>
								<button class="close-btn">&times;</button>
								<h4>${dept.uddMClass}</h4>
								<p>${dept.uddLClass}</p>
								<a href="/ertds/univ/dpsrch/selectDetail.do?uddId=${dept.uddId}" class="btn-detail">학과 상세보기</a>
							</div>
						</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th class="row-header sortable-header" data-sort-key="admissionRate">입학 경쟁률 ↕</th>
					<c:forEach var="dept" items="${compareList}">
						<td>${dept.admissionRate}</td>
					</c:forEach>
				</tr>
				<tr>
					<th class="row-header sortable-header" data-sort-key="avgSalary">첫 평균 급여 ↕</th>
					<c:forEach var="dept" items="${compareList}">
						<td>${dept.avgSalary}만원</td>
					</c:forEach>
				</tr>
				<tr>
					<th class="row-header sortable-header" data-sort-key="avgTuitionFormatted">평균 등록금 ↕</th>
					<c:forEach var="dept" items="${compareList}">
						<td>${dept.avgTuitionFormatted}</td>
					</c:forEach>
				</tr>
				<tr>
					<th class="row-header sortable-header" data-sort-key="avgScholarFormatted">평균 장학금 ↕</th>
					<c:forEach var="dept" items="${compareList}">
						<td>${dept.avgScholarFormatted}</td>
					</c:forEach>
				</tr>
				<tr>
					<th class="row-header sortable-header" data-sort-key="satisfactionAvg">만족도 ↕</th>
					<c:forEach var="dept" items="${compareList}">
						<td>${dept.satisfactionGrade}(${dept.satisfactionAvg})</td>
					</c:forEach>
				</tr>
				<tr>
					<th class="row-header sortable-header" data-sort-key="empRate">취업률 ↕</th>
					<c:forEach var="dept" items="${compareList}">
						<td>${dept.empRate}%</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script type="text/javascript" src="/js/ertds/univ/dpsrch/deptCompare.js"></script>
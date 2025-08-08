<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/hgschl/HighSchoolDetail.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진학 정보</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn">
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
		<!--
			<div class="tab-container" id="tabs">
	 		    <div class="tab active ">고등학교 검색</div>
				<div class="tab">학과 정보</div>
	  		</div>
  		-->
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="detail-container" id="highSchoolDetailContainer"
			data-hs-name="${highSchool.hsName}"
			data-hs-addr="${highSchool.hsAddr}" data-hs-tel="${highSchool.hsTel}"
			data-hs-lat="${empty highSchool.hsLat ? 0 : highSchool.hsLat}"
			data-hs-lot="${empty highSchool.hsLot ? 0 : highSchool.hsLot}">

			<div class="detail-container">
				<div class="school-summary-box">
					<h2>${highSchool.hsName}</h2>
					<ul class="summary-info-list">
						<li><strong>지역:</strong> ${highSchool.hsRegion}</li>
						<li><strong>유형:</strong> ${highSchool.hsTypeName},
							${highSchool.hsCoeduType}</li>
						<li><strong>설립:</strong> ${highSchool.hsFoundType}</li>
					</ul>
				</div>

				<div class="detail-section">
					<h3 class="section-title">학교 위치</h3>
					<div id="map"></div>
				</div>

				<div class="detail-section">
					<h3 class="section-title">상세 정보</h3>
					<table class="details-table">
						<tbody>
							<tr>
								<th>주소</th>
								<td>${highSchool.hsAddr}</td>
							</tr>
							<tr>
								<th>연락처</th>
								<td>${highSchool.hsTel}</td>
							</tr>
							<tr>
								<th>홈페이지</th>
								<td><a href="${highSchool.hsHomepage}" target="_blank">${highSchool.hsHomepage}</a></td>
							</tr>
							<tr>
								<th>설립일</th>
								<td>${highSchool.hsFoundDate}</td>
							</tr>
							<tr>
								<th>개교기념일</th>
								<td>${highSchool.hsAnnivAt}</td>
							</tr>
						</tbody>
					</table>
				</div>


				<c:if test="${!empty deptList}">
					<div class="detail-section">
						<h3 class="section-title">학과 정보</h3>
						<ul class="dept-list">
							<c:forEach var="dept" items="${deptList}">
								<li>${dept.hsdName}</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>

				<div class="bottom-button-wrapper">
					<button type="button" id="pdf-preview-btn" class="btn-pdf-preview">PDF
						미리보기</button>
					<button type="button" id="pdf-download-btn"
						class="btn-pdf-download">PDF 다운로드</button>
					<a href="/ertds/hgschl/selectHgschList.do" class="btn-list-bottom">목록</a>
				</div>

			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1881066df7ed9e16e4315953d2419995&libraries=services,clusterer,drawing"></script>

<script src="/js/ertds/hgschl/HighSchoolDetail.js"></script>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
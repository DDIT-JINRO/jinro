<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
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
		<div class="public-wrapper-main">
			<!-- 검색 및 필터 영역 -->
			<form method="get" action="/ertds/hgschl/selectHgschList.do">
				<div class="search-wrapper">

					<!-- 학교명 검색 -->
					<input type="text" name="schoolName" placeholder="학교명을 입력하세요"
						class="search-input" value="${param.schoolName}" />

					<!-- 기본 필터 -->
					<div class="filter-section">
						<div class="filter-title">지역</div>
						<div class="filter-options">
							<c:forEach var="region" items="${regionList}">
								<label class="filter-item"> <input type="checkbox"
									name="regionFilter" value="${region.code}" /> <span
									class="filter-label">${region.name}</span>
								</label>
							</c:forEach>
						</div>

						<div class="filter-title">학교유형</div>
						<div class="filter-options">
							<label><input type="radio" name="schoolType" value="일반" />
								일반</label> <label><input type="radio" name="schoolType"
								value="자사고" /> 자사고</label> <label><input type="radio"
								name="schoolType" value="과학고" /> 과학고</label>
							<!-- 기타 -->
						</div>

						<div class="filter-title">계열</div>
						<div class="filter-options">
							<label><input type="radio" name="courseType" value="문과" />
								문과</label> <label><input type="radio" name="courseType"
								value="이과" /> 이과</label>
						</div>
					</div>

					<!-- 검색 버튼 -->
					<button type="submit" class="search-button">검색</button>
				</div>
			</form>

			<!-- 고등학교 리스트 -->
			<div class="highschool-list">
				<c:forEach var="school" items="${schoolList}">
					<div class="school-item">
						<a href="/ertds/hgschl/selectHgschDetail.do?schoolId=${school.id}"
							class="school-name">${school.name}</a>
						<div class="school-info">지역: ${school.region} / 유형:
							${school.type} / 계열: ${school.course}</div>
					</div>
				</c:forEach>
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
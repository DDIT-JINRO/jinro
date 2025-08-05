<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/hgschl/HighSchoolList.css">
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
				<div class="search-filter-box">
					<div class="filter-row">
						<div class="filter-controls" style="width: 100%;">
							<div class="main-search-bar">
								<input type="text" name="schoolName" placeholder="학교명을 입력하세요."
									class="search-input" value="${param.schoolName}" />
								<button type="submit" class="search-button">검색</button>
							</div>
						</div>
					</div>

					<div class="filter-row">
						<div class="filter-label-title">지역</div>
						<div class="filter-controls">
							<c:forEach var="region" items="${regionList}">
								<label> <input type="checkbox" name="regionFilter"
									value="${region.code}" /> <span class="filter-tag">${region.name}</span>
								</label>
							</c:forEach>
						</div>
					</div>

					<div class="filter-row">
						<div class="filter-label-title">학교유형</div>
						<div class="filter-controls">
							<label><input type="radio" name="schoolType" value="일반" /><span
								class="filter-tag">일반</span></label> <label><input type="radio"
								name="schoolType" value="자사고" /><span class="filter-tag">자사고</span></label>
							<label><input type="radio" name="schoolType" value="과학고" /><span
								class="filter-tag">과학고</span></label>
						</div>
					</div>

					
				</div>
			</form>

			<!-- 고등학교 리스트 -->
			<div class="result-list-wrapper">
				<div class="list-header">
					<div class="header-item">대학명</div>
					<div class="header-item">공학여부</div>
					<div class="header-item">설립구분</div> <!-- 일반계/전문계 -->
					<div class="header-item">설립유형</div> <!-- 국공립/사립 -->
					<div class="header-item">지역</div>
					<div class="header-item">북마크</div>
				</div>
				<div class="highschool-list">
					<c:forEach var="school" items="${schoolList}">
						<div class="school-item">
							<a
								href="/ertds/hgschl/selectHgschDetail.do?schoolId=${school.hsId}"
								class="school-name">${school.hsName}</a>
							<div>${school.hsCoeduType}</div><!-- 공학여부 -->
							<div>${school.hsGeneralType}</div><!-- 일반계/전문계 -->
							<div>${school.hsFoundType}</div><!-- 국공립/사립 -->
							<div>${school.hsRegion}</div>
							<div class="bookmark-icon">&star;</div>
						</div>
					</c:forEach>
				</div>
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
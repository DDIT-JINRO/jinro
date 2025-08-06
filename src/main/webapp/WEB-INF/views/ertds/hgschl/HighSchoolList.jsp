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
				<div class="main-search-container">
					<div class="main-search-input-wrapper">
						<i class="icon-search"></i>
						<%-- 돋보기 아이콘 --%>
						<input type="text" name="keyword" class="main-search-input"
							placeholder="검색어를 입력하세요." value="${checkedFilters.keyword}">
					</div>
					<button type="submit" class="main-search-btn">검색</button>
				</div>

				<div class="search-filter-box">
					<div class="filter-toggle-bar" id="filter-toggle-btn">
						<span>상세검색</span>
					</div>

					<div class="filter-content" id="filter-content">
						<div class="filter-row">
							<div class="filter-label-title">지역</div>
							<div class="filter-controls">
								<c:forEach var="region" items="${regionList}">
									<label class="filter-item"> <input type="checkbox"
										name="regionFilter" value="${region.ccId}" data-label="지역"
										data-name="${region.ccName}"
										<c:if test="${fn:contains(checkedFilters.regionFilter, region.ccId)}">checked</c:if> />
										<span>${region.ccName}</span>
									</label>
								</c:forEach>
							</div>
						</div>

						<div class="filter-row">
							<div class="filter-label-title">학교 유형</div>
							<div class="filter-controls">
								<c:forEach var="sType" items="${schoolTypeList}">
									<label class="filter-item"> <input type="checkbox"
										name="schoolType" value="${sType.ccId}" data-label="학교 유형"
										data-name="${sType.ccName}"
										<c:if test="${fn:contains(checkedFilters.schoolType, sType.ccId)}">checked</c:if> />
										<span>${sType.ccName}</span>
									</label>
								</c:forEach>
							</div>
						</div>

						<div class="filter-row">
							<div class="filter-label-title">공학 여부</div>
							<div class="filter-controls">
								<c:forEach var="cType" items="${coedTypeList}">
									<label class="filter-item"> <input type="checkbox"
										name="coedTypeFilter" value="${cType.ccId}" data-label="공학 여부"
										data-name="${cType.ccName}"
										<c:if test="${fn:contains(checkedFilters.coedTypeFilter, cType.ccId)}">checked</c:if> />
										<span>${cType.ccName}</span>
									</label>
								</c:forEach>
							</div>
						</div>

						<div class="filter-row">
							<div class="filter-label-title">필터 조건</div>
							<div class="selected-filters" id="selected-filters-container">
							</div>
						</div>
					</div>
				</div>
			</form>

			<!-- 고등학교 리스트 -->
			<div class="result-list-wrapper">
				<div class="list-header">
					<div class="header-item">대학명</div>
					<div class="header-item">공학여부</div>
					<div class="header-item">학교유형</div>
					<div class="header-item">설립유형</div>
					<!-- 국공립/사립 -->
					<div class="header-item">지역</div>
				</div>
				<div class="highschool-list">
					<c:forEach var="school" items="${articlePage.content}">
						<div class="school-item">
							<a href="/ertds/hgschl/selectHgschDetail.do?hsId=${school.hsId}"
								class="school-name">${school.hsName}</a>
							<div>${school.hsCoeduType}</div>
							<div>${school.hsTypeName}</div>
							<div>${school.hsFoundType}</div>
							<div>${school.hsRegion}</div>
						</div>
					</c:forEach>
					<c:if test="${articlePage.total == 0}">
						<div style="text-align: center; padding: 50px;">검색 결과가 없습니다.</div>
					</c:if>
				</div>
			</div>


			<!-- 페이지레이션 -->
			<div class="card-footer clearfix">
				<ul class="pagination">
					<!-- Previous -->
					<li><a
						href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${checkedFilters.keyword}<c:forEach var='region' items='${checkedFilters.regionFilter}'>&regionFilter=${region}</c:forEach>&schoolType=${checkedFilters.schoolType}&coedTypeFilter=${checkedFilters.coedTypeFilter}&departmentFilter=${checkedFilters.departmentFilter}"
						class="${articlePage.startPage < 6 ? 'disabled' : ''}"> ←
							Previous </a></li>

					<!-- Page Numbers -->
					<c:forEach var="pNo" begin="${articlePage.startPage}"
						end="${articlePage.endPage}">
						<li><a
							href="${articlePage.url}?currentPage=${pNo}&keyword=${checkedFilters.keyword}<c:forEach var='region' items='${checkedFilters.regionFilter}'>&regionFilter=${region}</c:forEach>&schoolType=${checkedFilters.schoolType}&coedTypeFilter=${checkedFilters.coedTypeFilter}&departmentFilter=${checkedFilters.departmentFilter}"
							class="${pNo == articlePage.currentPage ? 'active' : ''}">
								${pNo} </a></li>
					</c:forEach>

					<!-- Next -->
					<li><a
						href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${checkedFilters.keyword}<c:forEach var='region' items='${checkedFilters.regionFilter}'>&regionFilter=${region}</c:forEach>&schoolType=${checkedFilters.schoolType}&coedTypeFilter=${checkedFilters.coedTypeFilter}&departmentFilter=${checkedFilters.departmentFilter}"
						class="${articlePage.endPage >= articlePage.totalPages ? 'disabled' : ''}">
							Next → </a></li>
				</ul>
			</div>

		</div>
	</div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script src="/js/ertds/hgschl/HighSchoolist.js"></script>
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
		
			<div class="filter-section">
		        <form method="get" action="/ertds/hgschl/selectHgschList.do">
				    <div class="com-default-search">
				        <div class="com-select-wrapper">
				            <svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
				                <path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
				            </svg>
				        </div>
				        <input type="search" name="keyword" placeholder="검색어를 입력하세요">
				        <button class="com-search-btn" type="button">
				            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
				                <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
				            </svg>
				        </button>
				    </div>
				
				    <div class="com-accordion-filter">
				        <button type="button" class="com-accordion-header" id="com-accordion-toggle">
				            <span>필터</span>
				            <span class="com-arrow-icon">▲</span>
				        </button>
				        <div class="com-accordion-panel" id="com-accordion-panel">
					        <div class="com-accordion-content">
				            <div class="com-filter-section">
				                <label class="com-filter-title">지역</label>
	                            <div class="com-filter-options">
	                                <c:forEach var="cType" items="${regionList}">
	                                    <label class="com-filter-item"> 
	                                        <input type="checkbox" name="regionFilter" value="${region.ccId}" data-label="지역" data-name="${region.ccEtc}"
	                                               <c:if test="${fn:contains(checkedFilters.regionFilter, region.ccId)}">checked</c:if> />
	                                        <span>${cType.ccName}</span>
	                                    </label>
	                                </c:forEach>
	                            </div>
	                        </div>
	
				            <div class="com-filter-section">
	                            <label class="com-filter-title">학교 유형</label>
	                            <div class="com-filter-options">
	                                <c:forEach var="cType" items="${schoolTypeList}">
	                                    <label class="com-filter-item"> 
	                                        <input type="checkbox" name="schoolType" value="${sType.ccId}" data-label="학교 유형" data-name="${sType.ccName}"
	                                               <c:if test="${fn:contains(checkedFilters.schoolType, sType.ccId)}">checked</c:if> />
	                                        <span>${cType.ccName}</span>
	                                    </label>
	                                </c:forEach>
	                            </div>
	                        </div>
				            
				            <div class="com-filter-section">
	                            <label class="com-filter-title">공학 여부</label>
	                            <div class="com-filter-options">
	                                <c:forEach var="cTarget" items="${coedTypeList}">
	                                    <label class="com-filter-item"> 
	                                        <input type="checkbox" name="coedTypeFilter" value="${cType.ccId}" data-label="공학 여부" data-name="${cType.ccName}"
	                                               <c:if test="${fn:contains(checkedFilters.coedTypeFilter, cType.ccId)}">checked</c:if> />
	                                        <span>${cTarget.ccName}</span>
	                                    </label>
	                                </c:forEach>
	                            </div>
	                        </div>
				            <div class="com-filter-section">
				            	<div class="com-button-container">
				                  <label class="com-filter-title">선택된 필터</label>
				                <button type="button" class="com-filter-reset-btn">초기화</button>
				            	</div>
				                <div class="com-selected-filters">
				                </div>
				            </div>
				            <button type="submit" class="com-submit-search-btn">검색</button>
				          </div>
				        </div>
				    </div>
				</form>
	        </div>
	        <p>총 ${articlePage.total}건</p>
			<!-- 검색 및 필터 영역 -->

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
		        <li>
		            <%-- c:url 태그로 이전 페이지 링크 생성 --%>
		            <c:url value="/ertds/hgschl/selectHgschList.do" var="prevUrl">
		                <c:param name="currentPage" value="${articlePage.startPage - 5}" />
		                <c:param name="keyword" value="${checkedFilters.keyword}" />
		                <%-- 현재 적용된 모든 필터 값들을 다시 파라미터로 추가 --%>
		                <c:forEach var="filter" items="${checkedFilters.regionFilter}"><c:param name="regionFilter" value="${filter}" /></c:forEach>
		                <c:forEach var="filter" items="${checkedFilters.schoolType}"><c:param name="schoolType" value="${filter}" /></c:forEach>
		                <c:forEach var="filter" items="${checkedFilters.coedTypeFilter}"><c:param name="coedTypeFilter" value="${filter}" /></c:forEach>
		            </c:url>
		            <a href="${prevUrl}" class="${articlePage.startPage < 6 ? 'disabled' : ''}">← Previous</a>
		        </li>
		
		        <c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
		            <li>
		                <%-- c:url 태그로 각 페이지 번호 링크 생성 --%>
		                <c:url value="/ertds/hgschl/selectHgschList.do" var="pageUrl">
		                    <c:param name="currentPage" value="${pNo}" />
		                    <c:param name="keyword" value="${checkedFilters.keyword}" />
		                    <%-- 현재 적용된 모든 필터 값들을 다시 파라미터로 추가 --%>
			                <c:forEach var="filter" items="${checkedFilters.regionFilter}"><c:param name="regionFilter" value="${filter}" /></c:forEach>
			                <c:forEach var="filter" items="${checkedFilters.schoolType}"><c:param name="schoolType" value="${filter}" /></c:forEach>
			                <c:forEach var="filter" items="${checkedFilters.coedTypeFilter}"><c:param name="coedTypeFilter" value="${filter}" /></c:forEach>
		                </c:url>
		                <a href="${pageUrl}" class="${pNo == articlePage.currentPage ? 'active' : ''}">${pNo}</a>
		            </li>
		        </c:forEach>
		
		        <li>
		            <%-- c:url 태그로 다음 페이지 링크 생성 --%>
		            <c:url value="/ertds/hgschl/selectHgschList.do" var="nextUrl">
		                <c:param name="currentPage" value="${articlePage.startPage + 5}" />
		                <c:param name="keyword" value="${checkedFilters.keyword}" />
		                <%-- 현재 적용된 모든 필터 값들을 다시 파라미터로 추가 --%>
		                <c:forEach var="filter" items="${checkedFilters.regionFilter}"><c:param name="regionFilter" value="${filter}" /></c:forEach>
		                <c:forEach var="filter" items="${checkedFilters.schoolType}"><c:param name="schoolType" value="${filter}" /></c:forEach>
		                <c:forEach var="filter" items="${checkedFilters.coedTypeFilter}"><c:param name="coedTypeFilter" value="${filter}" /></c:forEach>
		            </c:url>
		            <a href="${nextUrl}" class="${articlePage.endPage >= articlePage.totalPages ? 'disabled' : ''}">Next →</a>
		        </li>
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
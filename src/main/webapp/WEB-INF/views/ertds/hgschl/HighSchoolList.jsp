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
                                <input type="text" name="keyword" placeholder="학교명을 입력하세요."
                                    class="search-input" value="${checkedFilters.keyword}" />
                                <button type="submit" class="search-button">검색</button>
                            </div>
                        </div>
                    </div>

                    <div class="filter-row">
                        <div class="filter-label-title">지역</div>
                        <div class="filter-controls">
                            <%-- regionList는 컨트롤러에서 별도로 조회해서 모델에 담아줘야 합니다. --%>
                            <c:forEach var="region" items="${regionList}">
                                <label> 
                                    <input type="checkbox" name="regionFilter" value="${region.code}"
                                           <c:if test="${fn:contains(checkedFilters.regionFilter, region.code)}">checked</c:if> /> 
                                    <span class="filter-tag">${region.name}</span>
                                </label>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="filter-row">
                        <div class="filter-label-title">학교유형</div>
                        <div class="filter-controls">
                            <label><input type="radio" name="schoolType" value="일반" ${checkedFilters.schoolType == '일반' ? 'checked' : ''} /><span class="filter-tag">일반</span></label> 
                            <label><input type="radio" name="schoolType" value="자사고" ${checkedFilters.schoolType == '자사고' ? 'checked' : ''} /><span class="filter-tag">자사고</span></label>
                            <label><input type="radio" name="schoolType" value="과학고" ${checkedFilters.schoolType == '과학고' ? 'checked' : ''} /><span class="filter-tag">과학고</span></label>
                        </div>
                    </div>
                    
                    <div class="filter-row">
                        <div class="filter-label-title">공학여부</div>
                        <div class="filter-controls">
                             <label><input type="radio" name="coedTypeFilter" value="M" ${checkedFilters.coedTypeFilter == 'M' ? 'checked' : ''} /><span class="filter-tag">남</span></label>
                             <label><input type="radio" name="coedTypeFilter" value="F" ${checkedFilters.coedTypeFilter == 'F' ? 'checked' : ''} /><span class="filter-tag">여</span></label>
                             <label><input type="radio" name="coedTypeFilter" value="C" ${checkedFilters.coedTypeFilter == 'C' ? 'checked' : ''} /><span class="filter-tag">남녀공학</span></label>
                        </div>
                    </div>
                    
                    <div class="filter-row" id="department-filter-row">
                        <div class="filter-label-title">학과</div>
                        <div class="filter-controls">
                             <%-- 이 부분은 JS로 특정 학교유형 선택 시 보여주도록 처리 --%>
                             <label><input type="checkbox" name="departmentFilter" value="DEPT01" ${checkedFilters.departmentFilter == 'DEPT01' ? 'checked' : ''} /><span class="filter-tag">프로그래밍과</span></label>
                             <label><input type="checkbox" name="departmentFilter" value="DEPT02" ${checkedFilters.departmentFilter == 'DEPT02' ? 'checked' : ''} /><span class="filter-tag">디자인과</span></label>
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
                    <c:forEach var="school" items="${articlePage.content}">
                        <div class="school-item">
                            <a href="/ertds/hgschl/selectHgschDetail.do?hsId=${school.hsId}" class="school-name">${school.hsName}</a>
                            <div>${school.hsCoeduType}</div>
                            <div>${school.hsGeneralType}</div>
                            <div>${school.hsFoundType}</div>
                            <div>${school.hsRegion}</div>
                            <div class="bookmark-icon">&star;</div>
                        </div>
                    </c:forEach>
                    <c:if test="${articlePage.total == 0}">
						<div style="text-align: center; padding: 50px;">검색 결과가 없습니다.</div>
					</c:if>
                </div>
			</div>
			
			<div class="card-footer clearfix">
                <ul class="pagination">
                    <li>
                        <c:url value="/ertds/hgschl/selectHgschList.do" var="prevUrl">
                            <c:param name="currentPage" value="${articlePage.startPage - 5}" />
                            <c:param name="keyword" value="${checkedFilters.keyword}" />
                            <c:forEach var="region" items="${checkedFilters.regionFilter}"><c:param name="regionFilter" value="${region}" /></c:forEach>
                            <c:param name="schoolType" value="${checkedFilters.schoolType}" />
                            <c:param name="coedTypeFilter" value="${checkedFilters.coedTypeFilter}" />
                            <c:param name="departmentFilter" value="${checkedFilters.departmentFilter}" />
                        </c:url>
                        <a href="${prevUrl}" class="${articlePage.startPage < 6 ? 'disabled' : ''}">← Previous</a>
                    </li>

                    <c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
                        <li>
                            <c:url value="/ertds/hgschl/selectHgschList.do" var="pageUrl">
	                            <c:param name="currentPage" value="${pNo}" />
	                            <c:param name="keyword" value="${checkedFilters.keyword}" />
	                            <c:forEach var="region" items="${checkedFilters.regionFilter}"><c:param name="regionFilter" value="${region}" /></c:forEach>
	                            <c:param name="schoolType" value="${checkedFilters.schoolType}" />
	                            <c:param name="coedTypeFilter" value="${checkedFilters.coedTypeFilter}" />
	                            <c:param name="departmentFilter" value="${checkedFilters.departmentFilter}" />
	                        </c:url>
                            <a href="${pageUrl}" class="${pNo == articlePage.currentPage ? 'active' : ''}">${pNo}</a>
                        </li>
                    </c:forEach>

                    <li>
                        <c:url value="/ertds/hgschl/selectHgschList.do" var="nextUrl">
                            <c:param name="currentPage" value="${articlePage.startPage + 5}" />
                            <c:param name="keyword" value="${checkedFilters.keyword}" />
                            <c:forEach var="region" items="${checkedFilters.regionFilter}"><c:param name="regionFilter" value="${region}" /></c:forEach>
                            <c:param name="schoolType" value="${checkedFilters.schoolType}" />
                            <c:param name="coedTypeFilter" value="${checkedFilters.coedTypeFilter}" />
                            <c:param name="departmentFilter" value="${checkedFilters.departmentFilter}" />
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
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
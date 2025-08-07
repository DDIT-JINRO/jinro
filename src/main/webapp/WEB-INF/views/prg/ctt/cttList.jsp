<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/prg/ctt/cttList.css">
<!-- 스타일 여기 적어주시면 가능 -->

<section class="channel">
    <!-- 네비게이션 -->
    <div class="channel-title">
        <div class="channel-title-text">프로그램</div>
    </div>
    <div class="channel-sub-sections">
        <div class="channel-sub-section-itemIn">
            <a href="/prg/ctt/cttList.do">공모전</a>
        </div>
        <div class="channel-sub-section-item">
            <a href="/prg/act/vol/volList.do">대외활동</a>
        </div>
        <div class="channel-sub-section-item">
            <a href="/prg/std/stdGroupList.do">스터디그룹</a>
        </div>
    </div>
</section>

<div class="public-wrapper">
    <div class="public-wrapper-main">
        공모전목록
        <div class="filter-section">
            <div class="filter-tabs">
                <span class="filter-tab active">전체</span>
                <span class="filter-tab">인턴십</span>
                <span class="filter-tab">봉사활동</span>
                <span class="filter-tab">서포터즈</span>
            </div>
            <div class="search-bar">
                <input type="text" placeholder="검색어를 입력하세요.">
                <button class="search-button">검색</button>
            </div>
        </div>

        <div class="list-container">
            <c:forEach var="contest" items="${contestList}">
                <div class="contest-card">
                    <div class="card-image-box">
                        <img src="/files/download?fileGroupId=${contest.fileGroupId}&seq=1"
                             alt="포스터 이미지" class="contest-image">
                    </div>
                    <div class="card-content">
                        <h3 class="contest-title">
                            <a href="/prg/ctt/cttDetail.do?cttId=${contest.contestId}">
                                ${contest.contestTitle}
                            </a>
                        </h3>
                        <p class="contest-description">${contest.contestDescription}</p>
                        <div class="contest-meta">
                            <span class="meta-item">${contest.contestStartDate} ~ ${contest.contestEndDate}</span><br />
                            <span class="meta-item">조회수 ${contest.contestRecruitCount}</span>
                            <span class="meta-item">작성일자 ${contest.contestCreatedAt}</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="card-footer clearfix">
            <ul class="pagination">
                <li>
                    <c:url value="/ertds/hgschl/selectHgschList.do" var="prevUrl">
                        <c:param name="currentPage" value="${articlePage.startPage - 5}" />
                        <c:param name="keyword" value="${checkedFilters.keyword}" />
                        <c:forEach var="region" items="${checkedFilters.regionFilter}">
                            <c:param name="regionFilter" value="${region}" />
                        </c:forEach>
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
                            <c:forEach var="region" items="${checkedFilters.regionFilter}">
                                <c:param name="regionFilter" value="${region}" />
                            </c:forEach>
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
                        <c:forEach var="region" items="${checkedFilters.regionFilter}">
                            <c:param name="regionFilter" value="${region}" />
                        </c:forEach>
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

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<script>
    // 필요한 스크립트 작성 영역
</script>

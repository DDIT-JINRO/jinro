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
        <div class="filter-section">
            <form method="get" action="/prg/ctt/cttList.do">
                <input type="hidden" name="contestGubunFilter" value="G32001" />
                <div class="main-search-container">
                    <div class="main-search-input-wrapper">
                        <i class="icon-search"></i>
                        <input type="text" name="keyword" class="main-search-input"
                               placeholder="검색어를 입력하세요." value="${articlePage.keyword}">
                    </div>
                    <button type="submit" class="main-search-btn">검색</button>
                </div>

                <div class="search-filter-box">
                    <div class="filter-toggle-bar" id="filter-toggle-btn">
                        <span>상세검색</span>
                    </div>

                    <div class="filter-content" id="filter-content">
                        <div class="filter-row">
						    <div class="filter-label-title">모집 상태</div>
						    <div class="filter-controls">
						        <label class="filter-item">
						            <input type="checkbox" name="contestStatusFilter" value="proceeding" data-label="모집 상태" data-name="진행중"
						                   <c:if test="${fn:contains(paramValues.contestStatusFilter, 'proceeding')}">checked</c:if> />
						            <span>진행중</span>
						        </label>
						        <label class="filter-item">
						            <input type="checkbox" name="contestStatusFilter" value="finished" data-label="모집 상태" data-name="마감"
						                   <c:if test="${fn:contains(paramValues.contestStatusFilter, 'finished')}">checked</c:if> />
						            <span>마감</span>
						        </label>
						    </div>
						</div>

                        <div class="filter-row">
                            <div class="filter-label-title">모집 분야</div>
                            <div class="filter-controls">
                                <c:forEach var="cType" items="${contestTypeList}">
                                    <label class="filter-item"> 
                                        <input type="checkbox" name="contestTypeFilter" value="${cType.ccId}" data-label="모집 분야" data-name="${cType.ccName}"
                                               <c:if test="${fn:contains(checkedFilters.contestTypeFilter, cType.ccId)}">checked</c:if> />
                                        <span>${cType.ccName}</span>
                                    </label>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="filter-row">
                            <div class="filter-label-title">모집 대상</div>
                            <div class="filter-controls">
                                <c:forEach var="cTarget" items="${contestTargetList}">
                                    <label class="filter-item"> 
                                        <input type="checkbox" name="contestTargetFilter" value="${cTarget.ccId}" data-label="모집 대상" data-name="${cTarget.ccName}"
                                               <c:if test="${fn:contains(checkedFilters.contestTargetFilter, cTarget.ccId)}">checked</c:if> />
                                        <span>${cTarget.ccName}</span>
                                    </label>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="filter-row">
                            <div class="filter-label-title selected-filter-label">필터 조건</div>
                            <div class="selected-filters" id="selected-filters-container">
                                </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="list-container">
            <c:if test="${not empty articlePage.content}">
                <c:forEach var="contest" items="${articlePage.content}">
                    <a href="/prg/ctt/cttDetail.do?cttId=${contest.contestId}" class="contest-card">
                        <div class="card-image-box">
                            <img src="/files/download?fileGroupId=${contest.fileGroupId}&seq=1"
                                 alt="포스터 이미지" class="contest-image">
                        </div>
                        <div class="card-content">
                            <h3 class="contest-title">${contest.contestTitle}</h3>
                            <div class="contest-description">
							    <c:set var="descItems" value="${fn:split(contest.contestDescription, '●')}" />
							    <c:forEach var="item" items="${descItems}" begin="1" end="3">
							        <c:if test="${not empty fn:trim(item)}">
							            <c:set var="maxLength" value="60" />
							            <span>● 
							                <c:choose>
							                    <c:when test="${fn:length(fn:trim(item)) > maxLength}">
							                        <c:out value="${fn:substring(fn:trim(item), 0, maxLength)}..." />
							                    </c:when>
							                    <c:otherwise>
							                        <c:out value="${fn:trim(item)}" />
							                    </c:otherwise>
							                </c:choose>
							            </span><br>
							        </c:if>
							    </c:forEach>
							</div>
                            <div class="contest-meta">
                                <span class="meta-item">
                                    ${contest.contestGubunName} | ${contest.contestTypeName} | ${contest.contestTargetName}
                                </span>
                                <br />
                                <span class="meta-item">조회수 ${contest.contestRecruitCount}</span>
                                <span class="meta-item">
                                    <fmt:formatDate value="${contest.contestCreatedAt}" pattern="yyyy. MM. dd" />
                                </span>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </c:if>
            <c:if test="${empty articlePage.content}">
                <div style="text-align: center; padding: 50px;">검색 결과가 없습니다.</div>
            </c:if>
        </div>
    </div>

        <div class="card-footer clearfix">
            <ul class="pagination">
                <li>
                    <c:url value="${articlePage.url}" var="prevUrl">
                        <c:param name="currentPage" value="${articlePage.startPage - 1}" />
                    </c:url>
                    <a href="${prevUrl}" class="${articlePage.startPage <= 1 ? 'disabled' : ''}">← Previous</a>
                </li>
                <c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
                    <li>
                        <c:url value="${articlePage.url}" var="pageUrl">
                            <c:param name="currentPage" value="${pNo}" />
                        </c:url>
                        <a href="${pageUrl}" class="${pNo == articlePage.currentPage ? 'active' : ''}">${pNo}</a>
                    </li>
                </c:forEach>
                <li>
                    <c:url value="${articlePage.url}" var="nextUrl">
                        <c:param name="currentPage" value="${articlePage.endPage + 1}" />
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

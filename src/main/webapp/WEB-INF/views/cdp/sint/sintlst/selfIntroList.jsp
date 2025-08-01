<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/sint/sintlst/selfIntroList.css">
<!-- 스타일 여기 적어주시면 가능 -->

<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">경력관리</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/rsm/rsm">이력서</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/cdp/sint/qestnlst/questionList.do">자기소개서</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/imtintrvw/bsintrvw">모의면접</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/aifdbck/rsm">AI 피드백</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab" href="/cdp/sint/qestnlst/questionList.do">질문 리스트</a> 
			<a class="tab active" href="/sint/sintlst">자기소개서 리스트</a> 
			<a class="tab" href="/sint/sintwrt">자기소개서 작성</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<form method="get" action="/sint/sintlst" class="intro-search-filter">
				<!-- 검색어 입력 -->
				<input type="text" name="keyword" value="${keyword.param}"
					placeholder="자기소개서 제목 검색" class="intro-search-input" />

				<!-- 상태 선택 -->
				<select name="status" class="intro-status-select">
					<option value="">전체 상태</option>
					<option value="작성중"
						<c:if test="${param.status eq '작성중'}">selected</c:if>>작성중</option>
					<option value="완료"
						<c:if test="${param.status eq '완료'}">selected</c:if>>완료</option>
				</select>

				<!-- 검색 버튼 -->
				<button type="submit" class="intro-search-btn">검색</button>
			</form>
			<div class="intro-list-section">
				<sec:authorize access="isAuthenticated()">
					<div class="intro-list-header">총 ${articlePage.total}건</div>
				</sec:authorize>

				<c:forEach var="intro" items="${articlePage.content}">
					<div class="intro-card">
						<div class="intro-title-section">
							<div class="intro-title">${intro.siTitle}</div>
							<div class="intro-meta">
								<span class="intro-date"> 수정일 : <fmt:formatDate
										value="${intro.siUpdatedAt}" pattern="yyyy. MM. dd (E) HH:mm" />
								</span> <span class="intro-status"> 상태 : <c:choose>
										<c:when test="${intro.siStatus eq '완료'}">완료</c:when>
										<c:when test="${intro.siStatus eq '작성중'}">임시 저장</c:when>
										<c:otherwise>미지정</c:otherwise>
									</c:choose>
								</span>
							</div>
						</div>
						<a class="intro-edit-button"
							href="/sint/sintwrt?siId=${intro.siId}"> 자기소개서 수정하러 가기 </a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
</div>

<div class="card-footer clearfix">
	<ul class="pagination">
		<!-- Previous -->
		<li><a
			href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${param.keyword}&status=${param.status}"
			class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>">
				← Previous </a></li>

		<!-- Page Numbers -->
		<c:forEach var="pNo" begin="${articlePage.startPage}"
			end="${articlePage.endPage}">
			<li><a
				href="${articlePage.url}?currentPage=${pNo}&keyword=${param.keyword}&status=${param.status}"
				class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>">
					${pNo} </a></li>
		</c:forEach>

		<!-- Next -->
		<li><a
			href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${param.keyword}&gubun=${param.status}"
			class="<c:if test='${articlePage.endPage >= articlePage.totalPages}'>disabled</c:if>">
				Next → </a></li>
	</ul>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
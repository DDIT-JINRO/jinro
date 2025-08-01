<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/sint/qestnlst/questionList.css">


<!-- 스타일 여기 적어주시면 가능 -->
<style>
</style>
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
			<a class="tab active" href="/cdp/sint/qestnlst/questionList.do">질문 리스트</a> 
			<a class="tab" href="/cdp/sint/sintlst/selfIntroList.do">자기소개서 리스트</a> 
			<a class="tab" href="/cdp/sint/sintwrt/selfIntroWriting.do">자기소개서 작성</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="public-wrapper-main">
				<form method="get" action="/cdp/sint/qestnlst/questionList.do">
					<div class="search-wrapper">
						<input type="text" name="keyword" value="${articlePage.keyword}"
							placeholder="질문 검색" class="search-input" />
						<!-- 기본 필터 -->
						<div class="filter-section">
							<div class="filter-title">직무 필터</div>
							<div class="filter-options">
								<c:forEach var="code" items="${codeVOList}">
									<label class="filter-item"> <input type="checkbox"
										class="filter-checkbox" name="siqJobFilter"
										value="${code.ccId}" data-name="${code.ccName}"
										data-id="${code.ccId}" /> <span class="filter-label">${code.ccName}</span>
									</label>
								</c:forEach>
							</div>
						</div>

						<!-- 선택된 필터 표시 영역 -->
						<div class="filter-section selected-filters-wrapper">
							<div class="filter-title">선택된 필터</div>
							<div class="selected-filters" id="selected-filters">
								<!-- 선택된 항목 JS로 표시 -->
							</div>
						</div>


						<button type="submit" class="search-button">검색</button>
					</div>
				</form>


				<form id="cartForm" method="post" action="/cdp/sint/qestnlst/cart">
					<input type="hidden" id="questionIds" name="questionIds" />

					<div class="question-list">
						<c:forEach var="q" items="${articlePage.content}">
							<div class="question">
								<div class="question-left">
									<div class="question-tag">${codeMap[q.siqJob]}</div>
									<div class="question-text">${q.siqContent}</div>
								</div>
								<div class="question-right">
									<input type="checkbox" data-id="${q.siqId}"
										onchange="toggleQuestion(this, '${q.siqId}', '${q.siqContent}')" />
								</div>
							</div>
						</c:forEach>
					</div>

					<!-- 오른쪽 패널 -->
					<div class="cart-panel">
						<div id="cartSidebar"></div>
						<!-- 로그인된 사용자에게는 실제 장바구니 제출 버튼 -->
						<sec:authorize access="isAuthenticated()">
							<button type="button" onclick="submitCartForm()">자기소개서
								작성</button>
						</sec:authorize>

						<!-- 익명(비로그인) 사용자에게는 로그인 페이지로 -->
						<sec:authorize access="!isAuthenticated()">
							<button type="button"
								onclick="location.href='${pageContext.request.contextPath}/login?'">
								로그인 후 작성</button>
						</sec:authorize>
					</div>
				</form>

				<div class="card-footer clearfix">
					<ul class="pagination">
						<!-- Previous -->
						<li><a
							href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${articlePage.keyword}
				<c:forEach var='filter' items='${paramValues.siqJobFilter}'>
					&siqJobFilter=${filter}
				</c:forEach>"
							class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>">
								← Previous </a></li>

						<!-- Page Numbers -->
						<c:forEach var="pNo" begin="${articlePage.startPage}"
							end="${articlePage.endPage}">
							<li><a
								href="${articlePage.url}?currentPage=${pNo}&keyword=${articlePage.keyword}
					<c:forEach var='filter' items='${paramValues.siqJobFilter}'>
						&siqJobFilter=${filter}
					</c:forEach>"
								class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>">
									${pNo} </a></li>
						</c:forEach>

						<!-- Next -->
						<li><a
							href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${articlePage.keyword}
				<c:forEach var='filter' items='${paramValues.siqJobFilter}'>
					&siqJobFilter=${filter}
				</c:forEach>"
							class="<c:if test='${articlePage.endPage >= articlePage.totalPages}'>disabled</c:if>">
								Next → </a></li>
					</ul>
				</div>

			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script type="text/javascript"
	src="/js/cdp/sint/qestnlst/questionList.js">
</script>



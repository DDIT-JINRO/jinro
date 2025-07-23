<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="/css/pagenation.css">
<link rel="stylesheet" href="/css/cdp/sint/qestnlst/questionList.css">
<script type="text/javascript">
		window.currentMemId = '<c:out value="${memId}" default="" />';
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/cdp/sint/qestnlst/questionList.js">
</script>

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
		<div class="channel-sub-section-item"><a href="/rsm/rsm">이력서</a></div>
		<div class="channel-sub-section-itemIn"><a href="/sint/qestnlst">자기소개서</a></div>
		<div class="channel-sub-section-item"><a href="/imtintrvw/bsintrvw">모의면접</a></div>
		<div class="channel-sub-section-item"><a href="/aifdbck/rsm">AI 피드백</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab active" href="/sint/qestnlst">질문 리스트</a>
		    <a class="tab" href="/sint/sintlst">자기소개서 리스트</a>
		    <a class="tab" href="/sint/sintwrt">자기소개서 작성</a>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			질문 리스트 
			<div class="public-wrapper-main">
			  <form method="get" action="/sint/qestnlst">
			    <div style="margin-bottom: 10px;">
			      <input type="text" name="keyword" value="${articlePage.keyword}" placeholder="질문 검색" />
			      <select name="siqJob">
			        <option value="">전체 직무</option>
					<c:forEach var="code" items="${codeVOList}">
					  <option value="${code.ccId}" <c:if test="${siqJobFilter == code.ccId}">selected</c:if>>${code.ccName}</option>
					</c:forEach>
			      </select>
			      <button type="submit">검색</button>
			    </div>
			  </form>

				<form id="cartForm" method="post" action="/sint/qestnlst/cart">
					<input type="hidden" id="questionIds" name="questionIds" />

					<div class="question-list">
						<c:forEach var="q" items="${articlePage.content}">
						  <div class="question">
							  <div class="question-left">
							    <div class="question-tag">${codeMap[q.siqJob]}</div>
							    <div class="question-text">${q.siqContent}</div>
							  </div>
							  <div class="question-right">
							    <input type="checkbox"
							      data-id="${q.siqId}"
							      onchange="toggleQuestion(this, '${q.siqId}', '${q.siqContent}')"/>
							  </div>
							</div>
						</c:forEach>
					</div>

					<!-- 오른쪽 패널 -->
					<div class="cart-panel">
						<div id="cartSidebar"></div>
						<button type="button" onclick="submitCartForm()">자기소개서 작성</button>
					</div>
				</form>

				<div class="card-footer clearfix">
					<ul class="pagination">
						<!-- Previous -->
						<li><a
							href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${articlePage.keyword}&siqJob=${siqJobFilter}"
							class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>">
								← Previous </a></li>

						<!-- Page Numbers -->
						<c:forEach var="pNo" begin="${articlePage.startPage}"
							end="${articlePage.endPage}">
							<li><a
								href="${articlePage.url}?currentPage=${pNo}&keyword=${articlePage.keyword}&siqJob=${siqJobFilter}"
								class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>">
									${pNo} </a></li>
						</c:forEach>

						<!-- Next -->
						<li><a
							href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${articlePage.keyword}&siqJob=${siqJobFilter}"
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
<script>

</script>



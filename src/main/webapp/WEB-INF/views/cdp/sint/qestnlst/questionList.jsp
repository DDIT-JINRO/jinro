<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="/css/pagenation.css">
<!-- 스타일 여기 적어주시면 가능 -->
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
			        <option value="사업관리" <c:if test="${siqJobFilter == '사업관리'}">selected</c:if>>사업관리</option>
			        <option value="보건의료" <c:if test="${siqJobFilter == '보건의료'}">selected</c:if>>보건·의료</option>
			        <option value="금융보험" <c:if test="${siqJobFilter == '금융보험'}">selected</c:if>>금융보험</option>
			        <!-- 필요한 직무 추가 -->
			      </select>
			      <button type="submit">검색</button>
			    </div>
			  </form>
			
			  <form method="post" action="/sint/create">
			    <table border="1" width="100%" style="margin-top: 20px;">
			      <thead>
			        <tr>
			          <th width="5%"></th>
			          <th>질문 내용</th>
			        </tr>
			      </thead>
			      <tbody>
			        <c:forEach var="q" items="${articlePage.content}">
			          <tr>
			            <td><input type="checkbox" name="selectedQIds" value="${q.siqId}" /></td>
			            <td>${q.siqContent}</td>
			          </tr>
			        </c:forEach>
			      </tbody>
			    </table>
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
	// 스크립트 작성 해주시면 됩니다.
</script>
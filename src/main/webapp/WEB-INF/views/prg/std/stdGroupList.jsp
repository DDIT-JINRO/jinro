<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/prg/std/stdGroupList.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">프로그램</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/prg/ctt/cttList.do">공모전</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/prg/act/vol/volList.do">대외활동</a></div>
		<div class="channel-sub-section-itemIn"><a href="/prg/std/stdGroupList.do">스터디그룹</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="public-wrapper-main">
		<!-- 여기부터 작성해 주시면 됩니다 -->
			<form method="get" action="/prg/std/stdGroupList.do">
			    <div class="com-default-search">
			        <div class="com-select-wrapper">
			            <select name="searchType" class="com-status-filter">
			                <option value="title" ${searchType=='title' ? 'selected' : '' }>제목</option>
			                <option value="content" ${searchType=='content' ? 'selected' : '' }>내용</option>
			            </select>
			            <svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
			                <path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
			            </svg>
			        </div>
			        <input type="search" name="searchKeyword" placeholder="스터디그룹 내에서 검색" value="${searchKeyword }">
			        <button class="com-search-btn" type="submit">
			            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
			                <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
			            </svg>
			        </button>
   			        <div class="com-select-wrapper">
			            <select name="sortBy" class="com-status-filter">
			                <option value="createDesc" ${sortBy=='createDesc' ? 'selected' : '' }>최신순</option>
			                <option value="createAsc" ${sortBy=='createAsc' ? 'selected' : '' }>과거순</option>
			                <option value="viewCntDesc" ${sortBy=='viewCntDesc' ? 'selected' : '' }>조회순</option>
			                <option value="enteredMemDesc" ${sortBy=='enteredMemDesc' ? 'selected' : '' }>입장인원순</option>
			            </select>
			            <svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
			                <path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
			            </svg>
			        </div>
			    </div>

			    <div class="com-accordion-filter">
			        <button type="button" class="com-accordion-header" id="com-accordion-toggle">
			            <span>필터</span>
			            <span class="com-arrow-icon">▲</span>
			        </button>
			        <div class="com-accordion-panel" id="com-accordion-panel">
       				    <!-- 1) 지역 -->
					    <div class="filter-section" data-single-select>
					      <label class="filter-title">지역</label>
					      <div class="com-filter-options">
				     	   	<label class="filter-item">
					          <input type="radio" name="region" value="" <c:if test="${region==null or region=='' }">checked</c:if> >
					          <span>전체</span>
					        </label>
							<c:forEach var="reg" items="${regionList }">
						        <label class="filter-item">
						          <input type="radio" name="region" value="${reg.key }" ${region==reg.key ? 'checked' : '' }>
						          <span>${reg.value }</span>
						        </label>
							</c:forEach>
					      </div>
					    </div>
					<div class="filter-row">
	    			    <!-- 2) 성별-->
					    <div class="filter-section" data-single-select>
					      <label class="filter-title">성별</label>
					      <div class="filter-options">
					        <label class="filter-item">
					          <input type="radio" name="gender" value="" ${gender==null or gender=='' ? 'checked' : '' }>
					          <span>전체</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="gender" value="all" ${gender=='all' ? 'checked' : '' }>
					          <span>성별무관</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="gender" value="men" ${gender=='men' ? 'checked' : '' }>
					          <span>남자만</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="gender" value="women" ${gender=='women' ? 'checked' : '' }>
					          <span>여자만</span>
					        </label>
					      </div>
					    </div>

	    			    <!-- 3) 인원제한 -->
					    <div class="filter-section" data-single-select>
					      <label class="filter-title">인원제한</label>
					      <div class="filter-options">
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="2" ${maxPeople=='2' ? 'checked' : '' }>
					          <span>2명</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="3" ${maxPeople=='3' ? 'checked' : '' }>
					          <span>3명</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="5" ${maxPeople=='5' ? 'checked' : '' }>
					          <span>5명</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="10" ${maxPeople=='10' ? 'checked' : '' }>
					          <span>10명</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="15" ${maxPeople=='15' ? 'checked' : '' }>
					          <span>15명</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="20" ${maxPeople=='20' ? 'checked' : '' }>
					          <span>20명</span>
					        </label>
					      </div>
					    </div>
					</div>

           			    <!-- 3) 관심사 -->
						<!-- data-single-select 속성은 섹션 단일 선택을 위해 그대로 유지 -->
						  <!-- 1) 서브그룹: 학업 -->
				  		<div class="filter-section" data-single-select>
				  			<label class="filter-title">관심사</label>
						  <div class="filter-subgroup">
						    <div class="subgroup-header">학업</div>
						    <div class="filter-options">
						    <c:forEach var="interest" items="${interestMap }">
								<c:if test="${fn:startsWith(interest,'study') }">
									<label class="filter-item">
										<input type="checkbox" name=interestItems value="${interest.key }"
										<c:if test="${interestItems != null and interestItems.contains(interest.key) }">checked</c:if>	 />
										<span>${interest.value }</span>
									</label>
								</c:if>
							</c:forEach>
						    </div>
						  </div>

							<!-- 2) 서브그룹: 진로 -->
						  <div class="filter-subgroup">
						    <div class="subgroup-header">진로</div>
						    <div class="filter-options">
						    <c:forEach var="interest" items="${interestMap }">
								<c:if test="${fn:startsWith(interest,'career') }">
									<label class="filter-item">
										<input type="checkbox" name=interestItems value="${interest.key }"
										<c:if test="${interestItems != null and interestItems.contains(interest.key) }">checked</c:if>	 />
										<span>${interest.value }</span>
									</label>
								</c:if>
							</c:forEach>
						    </div>
						  </div>

							<!-- 3) 서브그룹: 취업 -->
						  <div class="filter-subgroup">
						    <div class="subgroup-header">진로</div>
						    <div class="filter-options">
						    <c:forEach var="interest" items="${interestMap }">
								<c:if test="${fn:startsWith(interest,'job') }">
									<label class="filter-item">
										<input type="checkbox" name=interestItems value="${interest.key }"
										<c:if test="${interestItems != null and interestItems.contains(interest.key) }">checked</c:if>	 />
										<span>${interest.value }</span>
									</label>
								</c:if>
							</c:forEach>
						    </div>
						  </div>

						  <!-- 4) 서브그룹: 기타 -->
						  <div class="filter-subgroup">
						    <div class="subgroup-header">기타</div>
						    <div class="filter-options">
						    <c:forEach var="interest" items="${interestMap }">
								<c:if test="${fn:startsWith(interest,'social') }">
									<label class="filter-item">
										<input type="checkbox" name=interestItems value="${interest.key }"
										<c:if test="${interestItems != null and interestItems.contains(interest.key) }">checked</c:if>	 />
										<span>${interest.value }</span>
									</label>
								</c:if>
							</c:forEach>
						    </div>
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
			</form>

			<sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal" var="memId" />
			</sec:authorize>
			<c:forEach var="stdBoardVO" varStatus="stat" items="${articlePage.content }">
				<div class="group-card" data-stdb-id="${stdBoardVO.boardId }">
					<div class="group-info">
						<div class="group-tags">
							<c:choose>
								<c:when test="${myRoomSet.contains(stdBoardVO.chatRoomVO.crId) }"><span class="status-tag tag  entered">참여중</span></c:when>
								<c:when test="${stdBoardVO.maxPeople <= stdBoardVO.curJoinCnt }"><span class="status-tag tag disabled">참여불가</span></c:when>
								<c:otherwise><span class="status-tag available">참여가능</span></c:otherwise>
							</c:choose>
							<span class="tag">${stdBoardVO.region}</span>
							<c:choose>
								<c:when test="${stdBoardVO.gender == 'all'}"><span class="tag">성별무관</span></c:when>
								<c:when test="${stdBoardVO.gender == 'men'}"><span class="tag">남자만</span></c:when>
								<c:when test="${stdBoardVO.gender == 'women'}"><span class="tag">여자만</span></c:when>
							</c:choose>
							<span class="tag">${interestMap[stdBoardVO.interest]}</span>
							<span class="tag">${stdBoardVO.maxPeople}명</span>
						</div>
						<div class="group-title">
							<c:choose>
								<c:when test="${fn:split(stdBoardVO.interest,'.')[0] == 'study' }"> ✍🏻 </c:when>
								<c:when test="${fn:split(stdBoardVO.interest,'.')[0] == 'career' }"> 📚 </c:when>
								<c:when test="${fn:split(stdBoardVO.interest,'.')[0] == 'job' }"> 🏦 </c:when>
								<c:when test="${fn:split(stdBoardVO.interest,'.')[0] == 'social' }"> 👨🏼‍🤝‍👨🏼</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${fn:length(stdBoardVO.boardTitle) gt 26 }">
									<c:out value="${fn:substring(stdBoardVO.boardTitle,0,25) }..."></c:out>
								</c:when>
								<c:otherwise>
									<c:out value="${stdBoardVO.boardTitle }"></c:out>
								</c:otherwise>
							</c:choose>
							<span class="group-capacity">${stdBoardVO.curJoinCnt}/${stdBoardVO.maxPeople}명</span>
						</div>
					</div>
					<div class="group-meta">👁️ ${stdBoardVO.boardCnt} &nbsp; 💬
						${stdBoardVO.replyCnt} &nbsp;&nbsp;<fmt:formatDate value="${stdBoardVO.boardCreatedAt}" pattern="yyyy. MM. dd"/></div>
				</div>
			</c:forEach>
			<div class="group-write-btn-wrapper">
			  <button class="btn-write-group" id="btnWrite">
			    ✏️ 글 작성하기
			  </button>
			</div>
			<div class="card-footer clearfix">
				<ul class="pagination">
					<!-- Previous -->
					<li>
						<a
						href="${articlePage.url }&currentPage=${articlePage.startPage - 5}"
						class="<c:if test='${articlePage.startPage < 6}'>disabled</c:if>">
							← Previous </a>
					</li>

					<!-- Page Numbers -->
					<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
						<li>
							<a
							href="${articlePage.url }&currentPage=${pNo}"
							class="<c:if test='${pNo == articlePage.currentPage}'>active</c:if>"> ${pNo} </a>
						</li>
					</c:forEach>

					<!-- Next -->
					<li>
						<a
						href="${articlePage.url }&currentPage=${articlePage.startPage + 5}"
						class="<c:if test='${articlePage.endPage >= articlePage.totalPages}'>disabled</c:if>">
							Next → </a>
					</li>
				</ul>
			</div>
		</div>
  		<!-- /wrapper main -->
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script src="/js/prg/std/stdGroupList.js"></script>
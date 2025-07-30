<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/prg/std/stdGroupList2.css">
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
		<!-- 여기부터 작성해 주시면 됩니다 -->


<!--   		<div class="public-wrapper-main"> -->
<!--   			스터디그룹목록 -->
<%--   			${myRoomSet } --%>
<!-- 			<div class="group-search"> -->
<!-- 				<form class="group-search form" action="/prg/std/stdGroupList.do" method="get"> -->
<!-- 					<div class="filter-box"> -->
<!-- 						<label for="searchType"> -->
<!-- 							<select id="searchType" name="searchType"> -->
<!-- 								<option value="title">제목</option> -->
<!-- 								<option value="content">내용</option> -->
<!-- 							</select> -->
<!-- 						</label> -->
<!-- 						<label for="searchKeyword">검색 : -->
<!-- 						<input id="searchKeyword" type="text" placeholder="검색어를 입력해주세요" name="searchKeyword"/> -->
<!-- 						</label> -->
<!-- 						<label for="region">지역 : -->
<!-- 							<select id="region" name="region" class="select-box select-box--tall"> -->
<!-- 								<option value="">전체</option> -->
<%-- 								<c:forEach var="region" items="${regionList }"> --%>
<%-- 									<option value="${region.key }">${region.value }</option> --%>
<%-- 								</c:forEach> --%>
<!-- 							</select> -->
<!-- 						</label> -->

<!-- 						<label for="gender">성별 : -->
<!-- 							<input type="radio" name="gender" id="gender-all" value="all" checked/><label for="gender-all">전체</label> -->
<!-- 							<input type="radio" name="gender" id="gender-men" value="men"/><label for="gender-men">남자만</label> -->
<!-- 							<input type="radio" name="gender" id="gender-women" value="women"/><label for="gender-women">여자만</label> -->
<!-- 						</label> -->

<!-- 						<label for="interest">관심사 : -->
<!-- 							<select id="interest" name="interest"> -->
<!-- 						    <option value="">전체</option> -->
<!-- 						    <optgroup label="학업"> -->
<!-- 						      <option value="study.general">공부</option> -->
<!-- 						      <option value="study.exam">수능준비</option> -->
<!-- 						      <option value="study.assignment">과제</option> -->
<!-- 						      <option value="study.etc">기타</option> -->
<!-- 						    </optgroup> -->
<!-- 						    <optgroup label="진로"> -->
<!-- 						      <option value="career.path">진로</option> -->
<!-- 						      <option value="career.admission">진학</option> -->
<!-- 						      <option value="career.etc">기타</option> -->
<!-- 						    </optgroup> -->
<!-- 						    <optgroup label="취업"> -->
<!-- 						      <option value="job.prepare">취업준비</option> -->
<!-- 						      <option value="job.concern">취업고민</option> -->
<!-- 						      <option value="job.etc">기타</option> -->
<!-- 						    </optgroup> -->
<!-- 						    <optgroup label="기타"> -->
<!-- 						      <option value="social.neighbor">동네친구</option> -->
<!-- 						      <option value="social.talk">잡담</option> -->
<!-- 						      <option value="social.etc">기타</option> -->
<!-- 						    </optgroup> -->
<!-- 							</select> -->
<!-- 						</label> -->
<!-- 						<label for="maxPeople">채팅인원제한 : -->
<!-- 							<select id="maxPeople" name="maxPeople"> -->
<!-- 								<option value="">전체</option> -->
<!-- 								<option value="2">2명</option> -->
<!-- 								<option value="5">5명</option> -->
<!-- 								<option value="10">10명</option> -->
<!-- 								<option value="15">15명</option> -->
<!-- 								<option value="20">20명</option> -->
<!-- 							</select> -->
<!-- 						</label> -->
<!-- 					</div> -->
<!-- 					<input type="submit" value="조회"/> -->
<!-- 				</form> -->
<!-- 			</div> -->

<!-- ------------------------------------------------------------------------------------- -->
			<form method="get" action="/prg/std/stdGroupList.do">
			    <div class="com-default-search">
			        <div class="com-select-wrapper">
			            <select name="searchType" class="com-status-filter">
			                <option value="title">제목</option>
			                <option value="content">내용</option>
			            </select>
			            <svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
			                <path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
			            </svg>
			        </div>
			        <input type="search" name="searchKeyword" placeholder="스터디그룹 내에서 검색">
			        <button class="com-search-btn" type="button">
			            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
			                <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
			            </svg>
			        </button>
   			        <div class="com-select-wrapper">
			            <select name="sortBy" class="com-status-filter">
			                <option value="createDesc">최신순</option>
			                <option value="createAsc">과거순</option>
			                <option value="viewCntDesc">조회순</option>
			                <option value="enteredMemDesc">입장인원순</option>
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
					          <input type="radio" name="region" value="" checked>
					          <span>전체</span>
					        </label>
							<c:forEach var="region" items="${regionList }">
						        <label class="filter-item">
						          <input type="radio" name="region" value="${region.key }">
						          <span>${region.value }</span>
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
					          <input type="radio" name="gender" value="" checked>
					          <span>전체</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="gender" value="all">
					          <span>성별무관</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="gender" value="men">
					          <span>남자만</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="gender" value="women">
					          <span>여자만</span>
					        </label>
					      </div>
					    </div>

	    			    <!-- 3) 인원제한 -->
					    <div class="filter-section" data-single-select>
					      <label class="filter-title">인원제한</label>
					      <div class="filter-options">
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="2">
					          <span>2명</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="5">
					          <span>5명</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="10">
					          <span>10명</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="15">
					          <span>15명</span>
					        </label>
					        <label class="filter-item">
					          <input type="radio" name="maxPeople" value="20">
					          <span>20명</span>
					        </label>
					      </div>
					    </div>
					</div>

           			    <!-- 3) 관심사 -->
						<!-- data-single-select 속성은 섹션 단일 선택을 위해 그대로 유지 -->
						<div class="filter-section" data-single-select>
						  <label class="filter-title">관심사</label>

						  <!-- 1) 서브그룹: 학업 -->
						  <div class="filter-subgroup">
						    <div class="subgroup-header">학업</div>
						    <div class="filter-options">
						      <label class="filter-item">
						        <input type="checkbox" name="interest" value="study.general">
						        <span>공부</span>
						      </label>
						      <label class="filter-item">
						        <input type="checkbox" name="interest" value="study.exam">
						        <span>수능준비</span>
						      </label>
						      <label class="filter-item">
						        <input type="checkbox" name="interest" value="study.assignment">
						        <span>과제</span>
						      </label>
						    </div>
						  </div>

						  <!-- 2) 서브그룹: 진로 -->
						  <div class="filter-subgroup">
						    <div class="subgroup-header">진로</div>
						    <div class="filter-options">
						      <label class="filter-item">
						        <input type="checkbox" name="interest" value="career.path">
						        <span>진로</span>
						      </label>
						      <label class="filter-item">
						        <input type="checkbox" name="interest" value="career.admission">
						        <span>진학</span>
						      </label>
						    </div>
						  </div>

						  <!-- 3) 서브그룹: 취업 -->
						  <div class="filter-subgroup">
						    <div class="subgroup-header">취업</div>
						    <div class="filter-options">
						      <label class="filter-item">
						        <input type="checkbox" name="interest" value="job.prepare">
						        <span>취업준비</span>
						      </label>
						      <label class="filter-item">
						        <input type="checkbox" name="interest" value="job.concern">
						        <span>취업고민</span>
						      </label>
						    </div>
						  </div>

						  <!-- 4) 서브그룹: 기타 -->
						  <div class="filter-subgroup">
						    <div class="subgroup-header">기타</div>
						    <div class="filter-options">
						      <label class="filter-item">
						        <input type="checkbox" name="interest" value="social.neighbor">
						        <span>동네친구</span>
						      </label>
						      <label class="filter-item">
						        <input type="checkbox" name="interest" value="social.talk">
						        <span>잡담</span>
						      </label>
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
							🧠
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
						${stdBoardVO.replyCnt} &nbsp;&nbsp;<fmt:formatDate value="${stdBoardVO.boardCreatedAt}"/></div>
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
<script src="/js/prg/std/stdGroupList2.js"></script>
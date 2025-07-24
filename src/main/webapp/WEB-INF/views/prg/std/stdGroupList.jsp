<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/prg/std/stdGroupList.css">
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
		<!-- 여기부터 작성해 주시면 됩니다 -->
		
		
  		<div class="public-wrapper-main">
  			스터디그룹목록
			<div class="group-search">
				<form class="group-search form" action="/prg/std/stdGroupList.do" method="get">
					<div class="filter-box">
						<label for="searchType">
							<select id="searchType" name="searchType">
								<option value="title">제목</option>
								<option value="content">내용</option>
							</select>
						</label>
						<label for="searchKeyword">검색 :
						<input id="searchKeyword" type="text" placeholder="검색어를 입력해주세요" name="searchKeyword"/>
						</label>
						<label for="region">지역 : 
							<select id="region" name="region">
								<option value="">전체</option>
								<option value="G23001">서울</option>
								<option value="G23002">부산</option>
								<option value="G23003">대구</option>
								<option value="G23004">인천</option>
								<option value="G23005">광주</option>
								<option value="G23006">대전</option>
								<option value="G23007">울산</option>
								<option value="G23008">세종</option>
								<option value="G23009">경기</option>
								<option value="G23010">강원</option>
								<option value="G23011">충북</option>
								<option value="G23012">충남</option>
								<option value="G23013">전북</option>
								<option value="G23014">전남</option>
								<option value="G23015">경북</option>
								<option value="G23016">경남</option>
								<option value="G23017">제주</option>
							</select>
						</label>
						
						<label for="gender">성별 : 
							<input type="radio" name="gender" id="gender-all" value="all" checked/><label for="gender-all">전체</label>
							<input type="radio" name="gender" id="gender-men" value="men"/><label for="gender-men">남자만</label>
							<input type="radio" name="gender" id="gender-women" value="women"/><label for="gender-women">여자만</label>
						</label>
						
						<label for="interest">관심사 : 
							<select id="interest" name="interest">
						    <option value="">전체</option>
						    <optgroup label="학업">
						      <option value="study.general">공부</option>
						      <option value="study.exam">수능준비</option>
						      <option value="study.assignment">과제</option>
						      <option value="study.etc">기타</option>
						    </optgroup>
						    <optgroup label="진로">
						      <option value="career.path">진로</option>
						      <option value="career.admission">진학</option>
						      <option value="career.etc">기타</option>
						    </optgroup>
						    <optgroup label="취업">
						      <option value="job.prepare">취업준비</option>
						      <option value="job.concern">취업고민</option>
						      <option value="job.etc">기타</option>
						    </optgroup>
						    <optgroup label="기타">
						      <option value="social.neighbor">동네친구</option>
						      <option value="social.talk">잡담</option>
						      <option value="social.etc">기타</option>
						    </optgroup>
							</select>
						</label>
						<label for="maxPeople">채팅인원제한 : 
							<select id="maxPeople" name="maxPeople">
								<option value="">전체</option>
								<option value="2">2명</option>
								<option value="5">5명</option>
								<option value="10">10명</option>
								<option value="15">15명</option>
								<option value="20">20명</option>
							</select>
						</label>
					</div>
					<input type="submit" value="조회"/>
				</form>
			</div>
			<sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal" var="memId" />
			</sec:authorize>
			<p>${memId}</p>
			<c:forEach var="stdBoardVO" varStatus="stat" items="${articlePage.content }">
				<div class="group-card">
					<div class="group-info">
						<div class="group-tags">
							
							<c:choose>
								<c:when test="${myChatRoomIds.contains(stdBoardVO.chatRoomVO.crId) }"><span class="status-tag tag  entered">참여중</span></c:when>
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
							🧠 ${stdBoardVO.boardTitle} <span class="group-capacity">${stdBoardVO.curJoinCnt}/${stdBoardVO.maxPeople}명</span>
						</div>
					</div>
					<div class="group-meta">👁️ ${stdBoardVO.boardCnt} &nbsp; 💬
						${stdBoardVO.replyCnt} &nbsp;&nbsp;<fmt:formatDate value="${stdBoardVO.boardCreatedAt}"/></div>
				</div>
			</c:forEach>
  			<br/>
			<a href="/prg/std/stdGroupDetail.do?stdGroupId=1">스터디그룹 상세 1번글</a>
  			<br/>
			<a href="/prg/std/stdGroupDetail.do?stdGroupId=2">스터디그룹 상세 2번글</a>
  			<br/>
			<a href="/prg/std/stdGroupDetail.do?stdGroupId=3">스터디그룹 상세 3번글</a>

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
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>
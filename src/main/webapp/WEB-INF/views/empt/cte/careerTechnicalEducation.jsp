<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/empt/cte/careerTechnicalEducation.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">취업 정보</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/empt/ema/employmentAdvertisement.do">채용공고</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/empt/enp/enterprisePosting.do">기업정보</a></div>
		<div class="channel-sub-section-item"><a href="/empt/ivfb/interViewFeedback.do">면접후기</a></div>
		<div class="channel-sub-section-itemIn"><a href="/empt/cte/careerTechnicalEducation.do">직업교육</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
  		<div class="public-wrapper-main">
			<form method="get" action="/empt/cte/careerTechnicalEducation.do">
				<div class="com-default-search">
					<div class="com-select-wrapper">
						<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                			<path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z"
								clip-rule="evenodd" />
            			</svg>
					</div>
					<input type="search" name="keyword" placeholder="직업훈련 기관 및 훈련명">
					<button class="com-search-btn" type="submit">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
                			<path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z"
								clip-rule="evenodd" />
            			</svg>
					</button>
				</div>

				<div class="com-accordion-filter">
					<button type="button" class="com-accordion-header"
						id="com-accordion-toggle">
						<span>필터</span> <span class="com-arrow-icon">▲</span>
					</button>
					<div class="com-accordion-panel" id="com-accordion-panel">
						<div class="com-accordion-content">
							<div class="com-filter-section">
								<label class="com-filter-title">지역</label>
								<div class="com-filter-options">
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="서울"> <span>서울특별시</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="부산"> <span>부산광역시</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="대구"> <span>대구광역시</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="인천"> <span>인천광역시</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="광주"> <span>광주광역시</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="대전"> <span>대전광역시</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="울산"> <span>울산광역시</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="세종"> <span>세종특별자치시</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="경기"> <span>경기도</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="강원"> <span>강원도</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="충북"> <span>충청북도</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="충남"> <span>충청남도</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="전북"> <span>전라북도</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="전남"> <span>전라남도</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="경북"> <span>경상북도</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="경남"> <span>경상남도</span>
									</label>
									<label class="com-filter-item">
									  <input type="checkbox" name="region" value="제주"> <span>제주특별자치도</span>
									</label>

								</div>

								<label class="com-filter-title">마감순</label>
								<div class="com-filter-options">
										<label class="com-filter-item"> 
											<input type="checkbox" name="status" value="마감빠른순"> <span>마감 빠른 순</span>
										</label>
										<label class="com-filter-item"> 
											<input type="checkbox" name="status" value="마감느린순"> <span>마감 느린 순</span>
										</label>
								</div>
							</div>
							<div class="com-filter-section">
								<div class="com-button-container">
									<label class="com-filter-title">선택된 필터</label>
									<button type="button" class="com-filter-reset-btn">초기화</button>
								</div>
								<div class="com-selected-filters"></div>
							</div>
							<button type="submit" class="com-submit-search-btn">검색</button>
						</div>
					</div>
				</div>
			</form>
			<p>총 ${articlePage.total}건</p>

			<div class="hire-list">
			    <div class="accordion-header">
			        <div style="flex: 1;">교육기관명</div>
			        <div style="flex: 2.4;">교육 과정명</div>
			        <div style="flex: 0.7;">마감일</div>
			        <div style="width: 85px;">만족도</div>
			        <div style="width: 80px;">정원</div>
			        <div style="width: 20px;"></div>
			    </div>
			
			    <c:forEach var="data" items="${articlePage.content}" varStatus="status">
			        <div class="accordion-item">
			            <div class="accordion-header">
			                <div class="hire-info-item" style="flex: 1;">${data.jtSchool}</div>
			                <div class="hire-info-item" style="flex: 2.3;">
							    <c:choose>
							        <c:when test="${fn:length(data.jtName) > 35}">
							            ${fn:substring(data.jtName, 0, 30)}...
							        </c:when>
							        <c:otherwise>
							            ${data.jtName}
							        </c:otherwise>
							    </c:choose>
							</div>
			                <div class="hire-info-item" style="flex: 0.8;"><fmt:formatDate value="${data.jtStartDate}" pattern="yyyy. MM. dd" /></div>
			                <div class="hire-info-item" style="width: 80px;">${data.jtScore}</div>
			                <div class="hire-info-item" style="width: 80px;">${data.jtQuota}명</div>
			                <div class="hire-info-item" style="width: 20px;">
			                    <span class="toggle-icon">+</span>
			                </div>
			            </div>
			            <div class="accordion-content">
			                <div class="hire-description-section">
			                    <h4>교육 상세 정보</h4>
			                    
			                    <p><strong>교육명:</strong>&nbsp; ${data.jtName}</p>
			                    <p><strong>교육기관:</strong>&nbsp; ${data.jtSchool}</p>
			                    <p><strong>정원:</strong>&nbsp; ${data.jtQuota}명</p>
			                    <p><strong>교육 대상:</strong>&nbsp; ${data.jtTarget}</p>
			                    <p><strong>교육 평점:</strong>&nbsp; ${data.jtScore}점</p>
			                    <p><strong>훈련비:</strong>&nbsp; <fmt:formatNumber value="${data.jtFee}" type="currency" currencySymbol="" groupingUsed="true" />원</p>
			                    <p><strong>교육기관 주소:</strong>&nbsp; ${data.jtAddress}</p>
			                </div>
			                <div class="hire-url-section">
			                    <h4>훈련 신청 사이트</h4>
			                    <c:if test="${not empty data.jtUrl}">
			                        <a href="${data.jtUrl}" target="_blank" class="homepage-link">사이트로 이동하기</a>
			                    </c:if>
			                    <c:if test="${empty data.jtUrl}">
			                        <p>제공되는 URL이 없습니다.</p>
			                    </c:if>
			                </div>
			                <div class="hire-date-section">
			                    <h4>훈련 기간</h4>
			                    <div class="date-container">
			                        <label>훈련 시작일 : <fmt:formatDate value="${data.jtStartDate}" pattern="yyyy. MM. dd" />
			                        </label> 
			                        <label> 훈련 종료일 : <fmt:formatDate value="${data.jtEndDate}" pattern="yyyy. MM. dd" />
			                        </label>
			                    </div>
			                </div>
			            </div>
			        </div>
			    </c:forEach>
			</div>
			<div class="card-footer clearfix">
				<ul class="pagination">
					<!-- 이전 페이지 -->
					<li>
					  <a href="${articlePage.url}?currentPage=${articlePage.startPage - 5 > 0 ? articlePage.startPage - 5 : 1}&keyword=${param.keyword}&status=${param.status}&region=${param.region}"
					     class="${articlePage.startPage < 6 ? 'disabled' : ''}">
					    ← Previous
					  </a>
					</li>
					
					<!-- 페이지 번호 반복 -->
					<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
					  <li>
					    <a href="${articlePage.url}?currentPage=${pNo}&keyword=${param.keyword}&status=${param.status}&region=${param.region}"
					       class="${pNo == articlePage.currentPage ? 'active' : ''}">
					      ${pNo}
					    </a>
					  </li>
					</c:forEach>
					
					<!-- 다음 페이지 -->
					<li>
					  <a href="${articlePage.url}?currentPage=${articlePage.endPage + 1 <= articlePage.totalPages ? articlePage.endPage + 1 : articlePage.totalPages}&keyword=${param.keyword}&status=${param.status}&region=${param.region}"
					     class="${articlePage.endPage >= articlePage.totalPages ? 'disabled' : ''}">
					    Next →
					  </a>
					</li>
				</ul>
			</div>

		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script src="/js/empt/cte/careerTechnicalEducation.js"></script>

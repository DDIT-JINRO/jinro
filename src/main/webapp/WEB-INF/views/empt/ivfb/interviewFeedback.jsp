<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/empt/ivfb/interviewFeedback.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel" data-error-message="${errorMessage}">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">취업 정보</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/empt/ema/employmentAdvertisement.do">채용공고</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/empt/enp/enterprisePosting.do">기업정보</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/empt/ivfb/interViewFeedback.do">면접후기</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/empt/cte/careerTechnicalEducation.do">직업교육</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="teenListTop">
				<button id=btnWrite>면접 후기 공유하기</button>
			</div>
			<form method="get" action="${articlePage.url}">
				<div class="com-default-search">
					<div class="com-select-wrapper">
						<select name="status" class="com-status-filter">
							<option value="3">전체</option>
							<option value="1">제목</option>
							<option value="2">내용</option>
						</select>
						<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                			<path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z"
								clip-rule="evenodd"
							/>
            			</svg>
					</div>
					<input type="search" class="search-input" name="keyword" placeholder="면접 후기 게시판 내에서 검색">
					<button class="com-search-btn" type="submit">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
                			<path fill-rule="evenodd"
								d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd"
							/>
           				</svg>
					</button>
				</div>
			</form>
			<div class="group-card-content">
        <!-- 샘플 데이터 - 실제로는 JSTL forEach로 동적 생성 -->
        <div class="interview-card">
            <div class="card-header" onclick="toggleCard(this)">
			    <div class="card-header-left">
			        <div class="company-name">삼성전자</div>
			        <div class="card-meta">
			            <div class="meta-item author">
			                작성자 | 김철수
			            </div>
			            <div class="meta-item">
			                <svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor">
			                    <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM2 2a1 1 0 0 0-1 1v1h14V3a1 1 0 0 0-1-1H2zm13 3H1v9a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V5z"/>
			                </svg>
			                2024.01.15
			            </div>
			            <div class="rating">
			                <span class="stars">★★★★☆</span>
			                <span>4.0</span>
			            </div>
			        </div>
			    </div>
			    <div class="card-toggle">
			        <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
			            <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
			        </svg>
			    </div>
			</div>
            <div class="card-content">
                <div class="card-body">
                    <div class="detail-grid">
                        <div class="detail-item">
                            <div class="detail-label">작성자</div>
                            <div class="detail-value">김철수</div>
                        </div>
                        <div class="detail-item">
                            <div class="detail-label">면접 기업</div>
                            <div class="detail-value">삼성전자</div>
                        </div>
                        <div class="detail-item">
                            <div class="detail-label">면접 대상 직무</div>
                            <div class="detail-value">SW 개발자</div>
                        </div>
                        <div class="detail-item">
                            <div class="detail-label">면접일</div>
                            <div class="detail-value">2024.01.10</div>
                        </div>
                        <div class="detail-item">
                            <div class="detail-label">작성일</div>
                            <div class="detail-value">2024.01.15 14:30</div>
                        </div>
                        <div class="detail-item">
                            <div class="detail-label">수정일</div>
                            <div class="detail-value">2024.01.15 14:30</div>
                        </div>
                        <div class="detail-item">
                            <div class="detail-label">평점</div>
                            <div class="detail-value">
                                <span class="stars" style="color: #ffd700;">★★★★☆</span>
                                <span style="margin-left: 8px;">4.0/5.0</span>
                            </div>
                        </div>
                        <div class="detail-item feedback-content">
                            <div class="detail-label">후기 내용</div>
                            <div class="feedback-text">전체적으로 분위기가 좋았고, 면접관분들이 친절하셨습니다. 
기술적인 질문들이 주를 이뤘으며, 특히 자료구조와 알고리즘에 대한 깊이 있는 질문들이 많았습니다.

면접 과정:
1. 자기소개 (5분)
2. 기술 질문 (20분)
3. 프로젝트 경험 (15분)
4. 역질문 (5분)

준비하시는 분들은 기본적인 CS 지식과 본인의 프로젝트에 대해 상세히 설명할 수 있도록 준비하시길 바랍니다.</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

			<ul class="pagination">
				<li>
					<a href="${articlePage.url}?currentPage=${articlePage.startPage - 5}&keyword=${param.keyword}&status=${param.status}" class="${articlePage.startPage < 6 ? 'disabled' : ''}">
						← Previous
					</a>
				</li>
				<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage}">
					<li>
						<a href="${articlePage.url}?currentPage=${pNo}&keyword=${param.keyword}&status=${param.status}" class="page-num ${pNo == articlePage.currentPage ? 'active' : ''}">
						${pNo}
						</a>
					</li>
				</c:forEach>

				<li>
					<a href="${articlePage.url}?currentPage=${articlePage.startPage + 5}&keyword=${param.keyword}&status=${param.status}" class="${articlePage.endPage >= articlePage.totalPages ? 'disabled' : '' }">
						Next →
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript" src="/js/empt/ivfb/interviewFeedback.js"></script>
</body>
</html>
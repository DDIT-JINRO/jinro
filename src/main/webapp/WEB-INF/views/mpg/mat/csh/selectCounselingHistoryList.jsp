<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/mpg/mat/csh/selectCounselingHistoryList.css">
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">마이페이지</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/mpg/mif/inq/selectMyInquiryView.do">내 정보</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/mpg/mat/bmk/selectBookMarkList.do">나의 활동</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/mpg/pay/selectPaymentView.do">결제 구독내역</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab" href="/mpg/mat/bmk/selectBookMarkList.do">북마크</a>
			<a class="tab active" href="/mpg/mat/csh/selectCounselingHistoryList.do">상담 내역</a>
			<a class="tab" href="/mpg/mat/reh/selectResumeHistoryList.do">이력서</a>
			<a class="tab" href="/mpg/mat/sih/selectSelfIntroHistoryList.do">자기소개서</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="activity-container">
				<form method="" action="/" id="com-keyword-filter-form">
					<div class="com-default-search">
						<div class="com-select-wrapper">
							<select name="com-status" class="com-status-filter">
								<option value="all">전체</option>
								<option value="">필터1</option>
								<option value="">필터2</option>
								<option value="">필터3</option>
							</select>
							<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                <path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
                            </svg>
						</div>
						<input type="search" name="com-search-keyword" placeholder="북마크 내에서 검색">
						<button class="com-search-btn" type="button">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
                                <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
                            </svg>
						</button>
					</div>

					<div class="com-accordion-filter">
						<button type="button" class="com-accordion-header" id="com-accordion-toggle">
							<span>필터</span>
							<span class="com-arrow-icon">▲</span>
						</button>
						<div class="com-accordion-panel" id="com-accordion-panel">
							<div class="com-filter-section">
								<label class="com-filter-title">직무 필터</label>
								<div class="com-filter-options">
									<label class="com-filter-item">
										<input type="checkbox" name="filter-keyword" value="univ">
										<span>대학</span>
									</label>
									<label class="com-filter-item">
										<input type="checkbox" name="filter-keyword" value="cop">
										<span>기업</span>
									</label>
									<label class="com-filter-item">
										<input type="checkbox" name="filter-keyword" value="emp">
										<span>채용</span>
									</label>
									<label class="com-filter-item">
										<input type="checkbox" name="filter-keyword" value="job">
										<span>직업</span>
									</label>
									<label class="com-filter-item">
										<input type="checkbox" name="filter-keyword" value="temp">
										<span>이력서 템플릿</span>
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
				</form>

				<div class="counsel-list">
					<div class="counsel-item">
						<div class="item-content">
							<div class="item-header">
								<span class="category-tag">진로고민</span>
								<h3 class="item-title">
									<a href="#">진로를 찾지 못하고 방황하는 분들께</a>
								</h3>
							</div>
							<p class="item-snippet">안녕하세요, 많은 분들이 진로 문제로 고민하시는 것을 보고 조금이나마 도움이 될까 싶어 글을 남깁니다. 저 또한 20대 내내 무엇을 해야 할지, 어떤 길을 가야 할지 몰라 많이 방황했습니다...</p>
							<div class="item-meta">
								<span>작성자 이름</span>
								<span class="divider">·</span>
								<span>2024.07.29</span>
								<span class="divider">·</span>
								<span>조회 1234</span>
							</div>
						</div>
					</div>
					<div class="counsel-item">
						<div class="item-content">
							<div class="item-header">
								<span class="category-tag">Q&A</span>
								<h3 class="item-title">
									<a href="#">비전공자인데 데이터 분석가로 취업 가능할까요?</a>
								</h3>
							</div>
							<p class="item-snippet">통계학과나 컴퓨터공학을 전공하지 않았는데, 데이터 분석 분야로 커리어를 시작하고 싶습니다. 부트캠프를 수료하고 포트폴리오를 준비하면 신입으로 취업할 수 있을지 현직자분들의 조언을 구합니다.</p>
							<div class="item-meta">
								<span>다른작성자</span>
								<span class="divider">·</span>
								<span>2024.07.28</span>
								<span class="divider">·</span>
								<span>조회 587</span>
							</div>
						</div>
					</div>

				</div>

				<ul class="pagination">
					<li>
						<a href="/sint/qestnlst?currentPage=-4&amp;keyword=" class="disabled">← Previous</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=1&amp;keyword=" class="page-num active">1</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=2&amp;keyword=" class="page-num">2</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=3&amp;keyword=" class="page-num">3</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=4&amp;keyword=" class="page-num">4</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=5&amp;keyword=" class="page-num">5</a>
					</li>
					<li>
						<a href="/sint/qestnlst?currentPage=6&amp;keyword=" class="">Next →</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script src="/js/mpg/mat/csh/selectCounselingHistoryList.js"></script>
</html>
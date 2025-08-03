<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/mpg/mat/reh/selectResumeHistoryList.css">
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
			<a class="tab" href="/mpg/mat/csh/selectCounselingHistoryList.do">상담 내역</a>
			<a class="tab active" href="/mpg/mat/reh/selectResumeHistoryList.do">이력서</a>
			<a class="tab" href="/mpg/mat/sih/selectSelfIntroHistoryList.do">자기소개서</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="activity-container">
				<form method="GET" action="/mpg/mat/sih/selectSelfIntroHistoryList.do">
					<div class="com-default-search">
						<div class="com-select-wrapper">
							<select name="status" class="com-status-filter">
								<option value="">전체</option>
								<option value="작성중">작성중</option>
								<option value="완료">작성완료</option>
							</select>
							<svg class="com-select-arrow" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
								<path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
							</svg>
						</div>
						<input type="search" name="keyword" placeholder="내가 작성한 자기소개서에서 검색">
						<button class="com-search-btn" type="submit">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="20" height="20">
								<path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z" clip-rule="evenodd" />
							</svg>
						</button>
					</div>
				</form>
				
				<div class="resume-history-list">
					<div class="resume-history-item">
						<div class="item-content">
							<div class="item-header">
								<span class="category-tag">상태</span>
								<h3 class="item-title">
									<a href="#">제목</a>
								</h3>
							</div>
							<div class="item-meta">
								<span>
									마지막 작성일 : 0000-00-00
								</span>
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
				</ul>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
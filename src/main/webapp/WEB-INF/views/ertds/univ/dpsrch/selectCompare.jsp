<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/univ/dpsrch/deptCompare.css">
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진학 정보</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a></div>
		<div class="channel-sub-section-item"><a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab" href="/ertds/univ/uvsrch/selectUnivList.do">대학 검색</a>
		    <a class="tab active" href="/ertds/univ/dpsrch/selectDeptList.do">학과 정보</a>
		</div>
	</div>
</div>
${compareList}
<div>
	<div class="public-wrapper">
		<div class="comparison-grid">
			<div class="grid-placeholder"></div>

			<div class="job-card">
				<button class="close-btn">&times;</button>
				<h4>직업명</h4>
				<p>직업 설명 직업 설명 직업 설명 직업 설명 직업 설명</p>
				<a href="#" class="btn-detail">직업 상세보기</a>
			</div>
			<div class="job-card">
				<button class="close-btn">&times;</button>
				<h4>직업명</h4>
				<p>직업 설명 직업 설명 직업 설명 직업 설명 직업 설명</p>
				<a href="#" class="btn-detail">직업 상세보기</a>
			</div>
			<div class="job-card">
				<button class="close-btn">&times;</button>
				<h4>직업명</h4>
				<p>직업 설명 직업 설명 직업 설명 직업 설명 직업 설명</p>
				<a href="#" class="btn-detail">직업 상세보기</a>
			</div>
			<div class="job-card">
				<button class="close-btn">&times;</button>
				<h4>직업명</h4>
				<p>직업 설명 직업 설명 직업 설명 직업 설명 직업 설명</p>
				<a href="#" class="btn-detail">직업 상세보기</a>
			</div>
			<div class="job-card">
				<button class="close-btn">&times;</button>
				<h4>직업명</h4>
				<p>직업 설명 직업 설명 직업 설명 직업 설명 직업 설명</p>
				<a href="#" class="btn-detail">직업 상세보기</a>
			</div>

			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>

			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>

			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			
			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			
			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>

			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>

			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>

			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			
			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			
			<div class="table-cell header">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
			<div class="table-cell">비교 내용</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/las/contentUsageStatistics.css">

<h3>로그 및 통계 > 컨텐츠 이용 통계</h3>
<div class="template-container">
	<div class="left-column">
		<div class="template-panel">
			<div class="panel-header">가장 많이 본 게시글</div>
			<div class="chart">
				<canvas id="mostViewedChart"></canvas>
			</div>
		</div>
		<div class="template-panel">
			<div class="panel-header">공지사항 클릭 수 TOP 10</div>
			<div class="chart">
				<canvas id="noticeClickChart"></canvas>
			</div>
		</div>
	</div>

	<div class="right-column">
		<div class="template-panel">
			<div class="panel-header">커뮤니티 게시글 수 및 댓글 수 (기간별)</div>
			<div class="chart">
				<canvas id="communityStatsChart"></canvas>
			</div>
		</div>

		<div class="template-panel">
			<div class="panel-header">신고된 게시글 유형 및 처리 현황</div>
			<div class="chart">
				<canvas id="reportStatusChart"></canvas>
			</div>
		</div>
	</div>
</div>
<script src="/js/include/admin/las/contentUsageStatistics.js"></script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/las/consultationLogsStatistics.css">

<h3>로그 및 통계 > 상담 로그 및 통계</h3>
<div class="template-container">
	<div class="left-column">
		<div class="template-panel">
			<div class="panel-header">상담 종류별 신청 수</div>
			<div class="chart">
				<canvas id="counselingTypeChart"></canvas>
			</div>
		</div>
		<div class="template-panel">
			<div class="panel-header">상담 방법별 비율</div>
			<div class="chart">
				<canvas id="counselingMethodChart"></canvas>
			</div>
		</div>
		<div class="template-panel">
			<div class="panel-header">상담 시간대 통계</div>
			<div class="chart">
				<canvas id="counselingTimeChart"></canvas>
			</div>
		</div>
	</div>

	<div class="right-column">
		<div class="template-panel">
			<div class="panel-header">상담사별 처리 건수 및 만족도</div>
			<div class="table-wrapper">
				<table>
					<thead>
						<tr>
							<th>상담사 이름</th>
							<th>처리 건수</th>
							<th>만족도 평가 (⭐)</th>
						</tr>
					</thead>
					<tbody id="counselor-stats-list">
						</tbody>
				</table>
				<div class="card-footer clearfix">
					<div class="panel-footer pagination"></div>
				</div>
			</div>
		</div>

		<div class="template-panel">
			<div class="panel-header" >AI 상담 사용량</div>
			<div class="chart">
				<canvas id="aiUsageChart"></canvas>
			</div>
		</div>
	</div>
</div>

<script src="/js/include/admin/las/counselingLogsStatistics.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/umg/counselorManagement.css">
<script src="/js/include/admin/umg/counselorManagement.js"></script>
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">상담사 관리</h2>
<body>
	<div class="cnsMng-1">
		<div class="template-panel cnsMng-1-1">
			<div class="middleTitle">상담 예약수</div>
			<img class="image1-1-1-1" alt="" src="/images/admin/admin-image1.png">
			<div class="userCnt-1-1-1">123</div>
			<div class="increase-1-1-1" style="margin-bottom: 10px;">
				<span style="color: rgba(10, 207, 151, 1); margin-right: 5px;">&#9650;</span>
				5.27%
				<div class="increase-1-1-1-p">Since last week</div>
			</div>
			<div class="increase-1-1-1" style="margin-bottom: 10px;">
				<span style="color: rgba(10, 207, 151, 1); margin-right: 5px;">&#9650;</span>
				8.07%
				<div class="increase-1-1-1-p">Since last month</div>
			</div>
			<div class="increase-1-1-1-red">
				<span style="color: rgba(250, 92, 124, 1); margin-right: 5px;">&#9660;</span>
				1.22%
				<div class="increase-1-1-1-p">Since last year</div>
			</div>
		</div>
		<div class="template-panel cnsMng-1-1">
			<div class="middleTitle">월별 상담 통계</div>
			<div class="sparkline-container">
				<canvas id="sparklineChart"></canvas>
			</div>
		</div>
		<div class="template-panel cnsMng-1-1">
			<div class="middleTitle">평균 만족도</div>
		</div>
	</div>
	<div class="cnsMng-2">
		<div class="template-panel cnsMng-2-1">
			<div class="middleTitle">상담사 리스트</div>
		</div>
		<div class="template-panel cnsMng-2-1">
			<div class="middleTitle">상담사 상세</div>
		</div>
	</div>
</body>


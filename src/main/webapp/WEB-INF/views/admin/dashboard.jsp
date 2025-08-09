<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/css/admin/admDashboard.css">
<title>관리자 메인</title>
<script>
	
</script>
<style>
</style>

</head>
<%@ include file="/WEB-INF/views/include/admin/sidebar.jsp"%>
<body>

	<!-- 메인 콘텐츠 영역 -->
	<div class="main-content">
		<div id="content">
			<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">대시 보드</h2>
			<div class="dashboard-1 overflow-wrap">
				<div class="dashboard-1-1 overflow-wrap">
					<div class="template-panel dashboard-1-1-1 overflow-wrap">
						<div class="middleTitle">실시간 이용자</div>
						<img class="image1-1-1-1" alt="" src="/images/admin/admin-image1.png">
						<div class="userCnt-1-1-1">123</div>
						<div class="increase-1-1-1">
							<span style="color: rgba(10, 207, 151, 1); margin-right: 5px;">&#9650;</span>
							5.27%
							<div class="increase-1-1-1-p">Since last week</div>
						</div>
					</div>
					<div class="template-panel dashboard-1-1-1 overflow-wrap">
						<div class="middleTitle">당월 이용자</div>
						<img class="image1-1-1-1" alt="" src="/images/admin/admin-image2.png">
						<div class="userCnt-1-1-1">456</div>
						<div class="increase-1-1-1-red">
							<span style="color: rgba(250, 92, 124, 1); margin-right: 5px;">&#9660;</span>
							5.27%
							<div class="increase-1-1-1-p">Since last month</div>
						</div>
					</div>
					<div class="template-panel dashboard-1-1-1 overflow-wrap" style="margin-bottom: 0;">
						<div class="middleTitle">전체 이용자</div>
						<img class="image1-1-1-1" alt="" src="/images/admin/admin-image1.png">
						<div class="userCnt-1-1-1">1,456</div>
						<div class="increase-1-1-1">
							<span style="color: rgba(10, 207, 151, 1); margin-right: 5px;">&#9650;</span>
							5.27%
							<div class="increase-1-1-1-p">Since last week</div>
						</div>
					</div>
				</div>
				<div class="template-panel dashboard-1-2 overflow-wrap">
					<div class="middleTitle">월별 이용자 통계</div>
					<div style="width: 100%; height: 100%; margin-top: 15px;">
						<canvas id="lineChart"></canvas>
					</div>
				</div>
			</div>
			<div class="dash2Flex">
				<div class="template-panel dashboard-2 overflow-wrap">
					<div class="middleTitle">결제/구독 통계</div>
					<div class="dashboard-2-1">

						<div class="stat-item">
							<div class="stat-title">결제 금액</div>
							<div class="stat-value-wrapper">
								<span class="stat-dot dot-current"></span>
								<span class="stat-value">58,254원</span>
							</div>
						</div>

						<div class="stat-item">
							<div class="stat-title">취소 손익</div>
							<div class="stat-value-wrapper">
								<span class="stat-dot dot-previous"></span>
								<span class="stat-value">69,524원</span>
							</div>
						</div>

					</div>
					<div class="chart-container">
						<canvas id="revenueChart"></canvas>
					</div>
				</div>
				<div class="dashboard-2-2 template-panel">
					<div class="middleTitlePp">뭐넣지</div>
				</div>
			</div>
			
		</div>
	</div>
</body>
<script src="/js/include/admin/dashboard.js"></script>
</html>

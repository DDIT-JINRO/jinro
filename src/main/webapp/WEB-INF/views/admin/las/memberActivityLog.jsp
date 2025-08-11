<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/css/admin/las/memberActivityLog.css">
<h3>로그 및 통계 > 회원 활동 로그</h3>
<div class="template-container">
	<div class="left-column">
		<div class="template-panel">
			<div class="panel-header">회원가입 / 탈퇴 이력</div>
			<div class="chart">
				<canvas id="memberJoinDeleteChart"></canvas>
			</div>
		</div>
		<div class="template-panel">
			<div class="panel-header">기간별 로그인 / 로그아웃 비율</div>
			<div class="chart">
				<canvas id="loginLogoutRatioChart"></canvas>
			</div>
		</div>
		<div class="template-panel">
			<div class="panel-header">사용자별 추천 직업 통계 (대분류)</div>
			<div class="chart">
				<canvas id="recommendationChart"></canvas>
			</div>
		</div>
	</div>

	<div class="right-column">
		<div class="template-panel">
			<div class="panel-header">경고 / 정지 이력</div>
			<div class="table-wrapper">
				<table>
					<thead>
						<tr>
							<th>회원ID</th>
							<th>패널티 유형</th>
							<th>사유</th>
							<th>일시</th>
						</tr>
					</thead>
					<tbody id="penalty-history-list">
					</tbody>
				</table>
			</div>
		</div>
		<div class="template-panel">
			<div class="panel-header">상담 신청 및 완료 이력</div>
			<div class="table-wrapper">
				<table>
					<thead>
						<tr>
							<th>회원ID</th>
							<th>상담유형</th>
							<th>신청일</th>
							<th>상태</th>
						</tr>
					</thead>
					<tbody id="counseling-history-list">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script src="/js/include/admin/las/memberActivityLog.js"></script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/las/usageStats.css">
<h3>로그 및 통계 > 접속/이용 통계</h3>
<div class="template-container">
	<div class="left-column">
		<div class="template-panel">
			<div class="panel-header" id="pannelHeader" style="cursor: pointer; text-decoration: none; display: flex; justify-content: space-between; align-items: center;">
			    일/월별 사용자 조회
			    <div>
			        <button onclick="dailyUserInquiry()">일별</button>
			        <button onclick="monthUserInquiry()">월별</button>
			        <button onclick="getCalendar()" id="getCalendar">달력</button>
			    </div>
			</div>
			<div class="chart">
				<!-- 일주일간의 일별 -->
				<canvas id="dailyUserInquiry"></canvas>
				<!-- 월별 -->
				<canvas id="monthUserInquiry"></canvas>
				<!-- 원하는 일정만큼  -->
				<canvas id="customUserInquiry"></canvas>
			</div>
		</div>
		<div class="template-panel">
			<div class="panel-header" id="pannelHeader" style="cursor: pointer; text-decoration: none; display: flex; justify-content: space-between; align-items: center;">
				페이지별 방문자 수 조회 상위 10
				<div>
					<button onclick="dailyPageVisitCount()">당일</button>
					<button onclick="monthPageVisitCount()">당월</button>
					<button onclick="getPageVisitCountCalendar()" id="getPageCalendar">달력</button>
				</div>
			</div>
			<canvas id="dailyPageVisitCount"></canvas>
			<canvas id="monthlyPageVisitCount"></canvas>
			<canvas id="customPageVisitCount"></canvas>
		</div>
	</div>

	<div class="template-panel right-panel">
		<div class="panel-header" id="faqHeader" onclick="resetData()" style="cursor: pointer; text-decoration: none">실시간 사용자 조회 : <span id="memberCount"></span>명</div>
		<div class="filter-box">
			<form action="/csc/faq/admin/faqList.do" method="get">
				<select name="gubun1">
				    <option value="" selected="selected" style="display: none;">성별</option>
				    <option value="G11001">남</option>
				    <option value="G11002">여</option>
				 </select>
				 <select name="gubun2">
				    <option value="" selected="selected" style="display: none;">로그인 타입</option>
				    <option value="G33001">일반</option>
				    <option value="G33002">네이버</option>
				    <option value="G33003">카카오</option>
				 </select>
 				 <input type="text" name="keyword" placeholder="검색어를 입력하세요" />
 				 <button type="button" onclick="serchList()" class="btn-save">조회</button>
			</form>
		</div>
		<div class="table-wrapper">
			<table>
				<colgroup>
					<col style="width: 15%;">
					<col style="width: 25%;">
					<col style="width: 15%;">
					<col style="width: 25%;">
					<col style="width: 20%;">
				</colgroup>
				<thead>
					<tr>
						<th>아이디</th>
						<th>닉네임</th>
						<th>성 별</th>
						<th>로그인 구분</th>
						<th>학교 구분</th>
					</tr>
				</thead>
				<tbody id="mem-list" >
					</tbody>
			</table>
		</div>
		<div class="card-footer clearfix">
			<div class="panel-footer pagination">
			
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="/js/include/admin/las/accessUsageStatistics.js"></script>
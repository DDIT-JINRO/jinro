<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link
href='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css'
rel='stylesheet' />
<link rel="stylesheet" href="/css/cnsLeader/scm/scheduleManagement.css">
<!-- 스크립트 작성해주시면 됩니다 (유의점 : DOMContentLoaded x) -->
<script>
	
</script>
<!-- 제목입니다 -->
<h3>스케줄 관리</h3>

<div class="topBox" style="margin-bottom: 10px">
	<div class="template-panel topheader">
		<div class="panel-header" id="counselListHeader"
			style="cursor: pointer; text-decoration: none">상담 리스트</div>
		<!-- 리스트 패널 상단: 필터 영역 -->
		<div class="filter-box">
			<form action="/csc/admin/noticeList.do" method="get">
				<select name="status">
					<option value="counselor">상담사명</option>
					<!-- … -->
				</select> <input type="text" name="keyword" placeholder="상담사명을 입력하세요" />
				<button type="button" class="btn-save" id="btn-search">조회</button>
			</form>
		</div>
		<p>
			총 <span id="notice-count"></span>건
		</p>
			<span id="selectedDateText"></span>
		<div class="table-wrapper">
			<table>
				<colgroup>
					<col style="width: 20%;">
					<col style="width: 20%;">
					<col style="width: 20%;">
					<col style="width: 20%;">
					<col style="width: 20%;">
				</colgroup>
				<thead>
					<tr>
						<th>번 호</th>
						<th>상담사</th>
						<th>상담 회원</th>
						<th>상담일시</th>
						<th>상 태</th>
					</tr>
				</thead>
				<tbody id="notice-list">
					<!-- Java 백엔드 렌더링용 -->
				</tbody>
			</table>
		</div>
	</div>
	<div class="template-panel topheader">
		<div class="panel-header" id="calenderHeader"
			style="cursor: pointer; text-decoration: none">달력</div>
		<div id='calendar'></div>
		<div id="timeSlotsContainer">
			<div id="timeSlotButtons"></div>
		</div>
		<input type="hidden" id="counselReqDatetimeInput"
			name="counselReqDatetime">
	</div>
</div>
<div class="template-panel bottomBox">
	<div class="panel-header" id="calenderHeader"
		style="cursor: pointer; text-decoration: none">스케줄 관리</div>
	<div class="bottom-content-wrapper">
		<div class="bottom-panel" style="flex: 1">
			<div class="panel-section-title">상담사</div>
			<div class="panel-section-content">
				<div class="info-item">
					<span class="info-label">이름</span> <span class="info-value"></span>
				</div>
			</div>

			<div class="panel-section-title">상담 신청 기본정보</div>
			<div class="panel-section-content">
				<div class="info-item">
					<span class="info-label">이름</span> <span class="info-value"></span>
				</div>
				<div class="info-item">
					<span class="info-label">나이</span> <span class="info-value"></span>
				</div>
				<div class="info-item">
					<span class="info-label">성별</span> <span class="info-value"></span>
				</div>
				<div class="info-item">
					<span class="info-label">이메일</span> <span class="info-value"></span>
				</div>
				<div class="info-item">
					<span class="info-label">연락처</span> <span class="info-value"></span>
				</div>
			</div>
		</div>

		<div class="bottom-panel" style="flex: 1">
			<div class="panel-section-title">상담 신청 정보</div>
			<div class="panel-section-content">
				<div class="info-item">
					<span class="info-label">상담 분야</span> <span class="info-value"></span>
				</div>
				<div class="info-item">
					<span class="info-label">상담 방법</span> <span class="info-value"></span>
				</div>
				<div class="info-item">
					<span class="info-label">상담 예약일</span> <span class="info-value"></span>
				</div>
				<div class="info-item">
					<span class="info-label">상담 예약시간</span> <span class="info-value"></span>
				</div>
				<div class="info-item">
					<span class="info-label">예약 상태</span> <span class="info-value"></span>
				</div>
			</div>
		</div>

		<div class="bottom-panel" style="flex: 2">
			<div class="panel-section-title">신청동기</div>
			<div class="panel-section-content">
				<div class="info-item">
					<span class="info-label"></span>
				</div>
			</div>
		</div>
	</div>
</div>

<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js'></script>

<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/locales/ko.js'></script>

<script type="text/javascript"
	src="/js/include/cnsLeader/scm/scheduleManagement.js"></script>

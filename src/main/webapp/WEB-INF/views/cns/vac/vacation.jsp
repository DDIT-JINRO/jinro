<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/cns/csl/vacation.css">
<script src="/js/include/cns/vac/vacation.js"></script>
<!-- 스크립트 작성해주시면 됩니다 (유의점 : DOMContentLoaded x) -->
<script>

</script>
<!-- 제목입니다 -->
<h3>연차/휴가 신청 및 목록</h3>
<div class="template-container">
	<div class="template-panel vac-approve-panel">
		<div class="panel-header">휴가 신청 양식</div>
		<div class="vac-approve-container">

			<div class="vac-approve-content-left">
				<h3>날짜 선택</h3>
			  <div id="dateRange"></div>
			  <button type="button" id="dateResetBtn" class="btn btn-reset date-reset">날짜초기화</button>
			</div>

			<div class="vac-approve-content-right">
				<label>시작일</label>
			  <input type="text" id="startDateInput" readonly name="startDate" placeholder="달력에서 시작일 선택">
			  	<label>종료일</label>
			  <input type="text" id="endDateInput" readonly  name="endDate" placeholder="달력에서 종료일 선택">
				<label>총 휴가일수</label>
			  <input type="text" id="totalDateCnt" readonly placeholder="총 휴가일수"/>
			  <h3>사유 작성</h3>
  			  <div class="ck-editor">
			    <!-- CKEditor가 여기에 mount됨 -->
			    <textarea name="vaReason" id="vaReason"></textarea>
			  </div>
			  <input id="attachFile" type="file" name="files" style="display: none;">
			  <div id="existing-files" class="existing-files">
	          	<li>
	            <div id="attachFileDiv">파일 첨부</div>
	          	</li>
	          </div>
			</div>

		</div>

		<div class="panel-footer button-group">
			<button type="button" id="resetAllBtn" class="btn btn-reset">전체초기화</button>
			<button  type="button" onclick="confirmVacation()" class="btn btn-confirm"  id="btn-confirm">제출</button>
		</div>
	</div>
	<div class="template-panel vac-list-panel">
		<div class="panel-header">휴가 신청 내역</div>
		<div class="filter-box">
			<form action="/csc/admin/noticeList.do" method="get">
				<div class="form-group">
					<div class="filter-group">
						<h4>필터</h4>
						<input type="radio" name="filter" value="" checked/>
						<label>전체</label>
						<input type="radio" name="filter" value="S03001"/>
						<label>신청</label>
						<input type="radio" name="filter" value="S03003"/>
						<label>승인</label>
						<input type="radio" name="filter" value="S03002"/>
						<label>반려</label>
					</div>
					<div class="sort-group">
						<h4>정렬</h4>
						<input type="radio" name="sortBy" value="reqDesc" checked/>
						<label>신청일 최신순</label>
						<input type="radio" name="sortBy" value="reqAsc"/>
						<label>신청일 과거순</label>
						<input type="radio" name="sortBy" value="startAsc"/>
						<label>시작일 빠른순</label>
						<input type="radio" name="sortBy" value="startDesc"/>
						<label>시작일 느린순</label>
					</div>
				</div>
			</form>
		</div>
		<p style="width: fit-content;">
			총 <span id="notice-count">0</span>건
		</p>
		<div class="table-wrapper">
			<table>
				<colgroup>
					<col style="width: 5%;">
					<col style="width: 10%;">
					<col style="width: 10%;">
					<col style="width: 10%;">
					<col style="width: 5%;">
					<col style="width: 30%;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
					<tr>
						<th>번 호</th>
						<th>신청일</th>
						<th>시작일</th>
						<th>종료일</th>
						<th>일 수</th>
						<th>사 유</th>
						<th>상 태</th>
					</tr>
				</thead>
				<tbody id="notice-list">
					<tr>
						<td colspan="7" style="text-align: center;">조회된 내역이 없습니다</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div style="margin-top: 10px; text-align: center;">
			<!-- 페이지네이션 자리 -->
			<div class="card-footer clearfix">
				 <div class="panel-footer pagination">

				</div>
			</div>
		</div>
	</div>
</div>


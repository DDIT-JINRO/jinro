<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/cns/csl/counselingLog.css">
<!-- 스크립트 작성해주시면 됩니다 (유의점 : DOMContentLoaded x) -->
<script>
</script>
<!-- 제목입니다 -->
<h3>상담 일지 작성</h3>
<div class="template-container">
	<!-- 리스트 패널 -->
	<div class="template-panel" style="flex: 1">
		<div class="panel-header" id="noticeHeader" style="cursor: pointer; text-decoration: none">상담 리스트</div>

		<!-- 리스트 패널 상단: 필터 영역 -->
		<div class="filter-box">
			<form action="/csc/admin/noticeList.do" method="get">
				<select name="status">
				    <option value="2025">2025</option>
				    <!-- … -->
				  </select>
  		 		  <input type="text" name="keyword" placeholder="검색어를 입력하세요" />
 				  <button type="button" class="btn-save">조회</button>
			</form>
		</div>
		<p>
			총 <span id="notice-count"></span>건
		</p>
		<div class="table-wrapper">
			<table>
				<colgroup>
					<col style="width: 10%;">
					<col style="width: 15%;">
					<col style="width: 10%;">
					<col style="width: 30%;">
					<col style="width: 25%;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
					<tr>
						<th>번 호</th>
						<th>회원명</th>
						<th>나 이</th>
						<th>이메일</th>
						<th>연락처</th>
						<th>상 태</th>
					</tr>
				</thead>
				<tbody id="notice-list">
					<!-- Java 백엔드 렌더링용 -->
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

	<!-- 상세/작성 패널 -->
	<div class="template-panel" style="flex: 1.5">
		<div class="panel-header">상담 상세정보</div>
		<h3>상담일지 작성</h3>
		<table class="info-table" style="display: none;">
			<thead id="info-table-thead">
				<tr>
					<th>번 호</th>
					<th>제 목</th>
					<th>생성일(수정일)</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody id="info-table-tbody">

			</tbody>
		</table>

		<form class="noticeFormGroup" id="form-data" enctype="multipart/form-data">
			<div>
				<label>제 목</label>
				<input type="text" name="noticeTitle" placeholder="제목을 입력하세요" />
			</div>

			<input type="hidden" name="fileGroupNo" id="fileGroupNo"/>
			<input type="hidden" name="noticeId" id="noticeId" value="0"/>
			<div class="ck-row">
			  <label>공지 내용</label>
			  <div class="ck-editor">
			    <!-- CKEditor가 여기에 mount됨 -->
			    <textarea name="noticeContent" id="noticeContent"></textarea>
			  </div>
			</div>

			<div class="noticeFormGroup">
				<label for="noticeFileInput">첨부 파일</label>
				<input id="noticeFileInput" type="file" name="files" multiple/>
			</div>


			<!-- 기존에 업로드된 파일을 뿌릴 곳 -->
			<div class="noticeFormGroup" id="file" style="display: none;">
				<div style="float:left;">
					<label>기존 첨부파일</label>
				</div>
				<div id="existing-files" class="existing-files">

				</div>
			</div>

			<div class="panel-footer button-group">
				<button type="button" onclick="resetDetail()" class="btn btn-reset">초기화</button>
				<button  type="button" onclick="insertOrUpdate()" class="btn btn-save"  id="btn-sav">임시저장</button>
				<button  type="button" onclick="insertOrUpdate()" class="btn btn-confirm"  id="btn-save">제출</button>
			</div>
		</form>
	</div>
</div>

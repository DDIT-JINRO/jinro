<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/css/csc/adminNotice.css">
<script type="text/javascript" src="/js/csc/adminNoticeList.js"></script>
<h3>고객센터 > 공지사항</h3>
<div class="template-container">
	<!-- 리스트 패널 -->
	<div class="template-panel" style="flex: 1.2">
		<div class="panel-header" id="noticeHeader" style="cursor: pointer; text-decoration: none">공지사항</div>

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
				<thead>
					<tr>
						<th>번 호</th>
						<th>제 목</th>
						<th>조회수</th>
						<th>생성일(수정일)</th>
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
		<div class="panel-header">공지사항 등록 / 수정</div>
		<h3>공지사항 기본정보</h3>
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

		<form id="form-data" enctype="multipart/form-data">
			<div>
				<label>제목</label> 
				<input type="text" name="noticeTitle" placeholder="제목을 입력하세요" />
			</div>
			
			<input type="hidden" name="fileGroupNo" id="fileGroupNo">
			<input type="hidden" name="noticeId" id="noticeId">
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
			<div id="file" style="display: none;">
				<label>기존 첨부파일</label>
				<div id="existing-files" class="existing-files" > 
					
				</div>
			</div>

			<div class="panel-footer button-group">
				<button type="button" class="btn btn-delete" style="display: none;"  onclick="deleteNotice()" id="btn-delete">삭제</button>
				<button type="button" onclick="resetDetail()" class="btn btn-reset">초기화</button>
				<button  type="button" onclick="insertOrUpdate()" class="btn btn-save"  id="btn-save">등록</button>
			</div>
		</form>
	</div>
</div>


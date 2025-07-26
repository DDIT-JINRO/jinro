<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/css/admin/test.css">
<link rel="stylesheet" href="/css/csc/adminNotice.css">
<script type="text/javascript" src="/js/csc/adminNoticeList.js" defer></script>

<h3>고객센터 > 공지사항</h3>
<br>
<div class="template-container">
	<!-- 리스트 패널 -->
	<div class="template-panel" style="flex: 1.2">
		<div class="panel-header">공지사항</div>

		<!-- 리스트 패널 상단: 필터 영역 -->
		<div class="filter-box">
			<select name="year">
				<option value="2025">2025</option>
				<!-- … -->
			</select> <input type="text" placeholder="검색어를 입력하세요..." />
			<button type="button" class="btn-save">조회</button>
		</div>


		<p>
			총 <span id="notice-count">0</span>건
		</p>
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

		<div style="margin-top: 10px; text-align: center;">
			<!-- 페이지네이션 자리 -->
			&lt; Previous 1 2 3 4 5 Next &gt;
		</div>
	</div>

	<!-- 상세/작성 패널 -->
	<div class="template-panel" style="flex: 1.5">
		<div class="panel-header">공지사항 등록 / 수정</div>
		<h3>공지사항 기본정보</h3>
		<table class="info-table">
			<thead>
				<tr>
					<th>번</th>
					<th>제목</th>
					<th>생성일</th>
					<th>수정일</th>
					<th>작성자</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${noticeDetail.noticeId}</td>
					<td>${noticeDetail.noticeTitle}</td>
					<td><fmt:formatDate value="${noticeDetail.noticeCreatedAt}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${noticeDetail.noticeUpdatedAt}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${noticeDetail.authorName}</td>
					<td>${noticeDetail.noticeCnt}</td>
				</tr>
			</tbody>
		</table>

		<form method="post" action="/faq/save">
			<div>
				<label>제목</label> 
				<input type="text" name="noticeTitle" placeholder="제목을 입력하세요" />
			</div>

			<div>
				<label>공지 내용 내용</label>
				<textarea id="noticeContent"></textarea>
			</div>

			<div style="margin-top: 10px;">
				<button type="button" class="btn btn-delete">삭제</button>
				<button type="reset" class="btn btn-reset">초기화</button>
				<button type="submit" class="btn btn-save">저장</button>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
console.log(ClassicEditor)
</script>

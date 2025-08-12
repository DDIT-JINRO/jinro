<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/admin/cmg/univManagement.css">
<!-- 스크립트 작성해주시면 됩니다 (유의점 : DOMContentLoaded x) -->
<script>
	
</script>
<!-- 제목입니다 -->
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">대학 관리</h2>
<body>
	<div class="admin-univMng-1" style="margin-bottom: 20px;">
		<div class="template-panel admin-univMng-1-1">
			<div class="middleTitle">대학 리스트</div>
			<div class="filter-box">
				<select name="status">
					<option value="1">전체</option>
					<option value="2">학교명</option>
					<option value="3">학교구분명</option>
				</select>
				<input type="text" name="keyword" placeholder="검색어를 입력하세요" />
				<button type="button" class="btn-save">조회</button>
			</div>
			<div class="listUniv">
				<div class="search-filter-bar">
					<p class="ptag-list" style="margin-bottom: 10px">
						총
						<span id="univList-count"></span>
						건
					</p>

				</div>
				<div class="listUnivBody">
					<table id="univTable">
						<thead>
							<tr>
								<th class="body-id">ID</th>
								<th class="body-memName">회원명</th>
								<th class="body-email">이메일</th>
								<th class="body-nickname">닉네임</th>
							</tr>
						</thead>
						<tbody id="univList">
						</tbody>
					</table>
				</div>
				<div class="card-footer clearfix">
					<div class="panel-footer pagination"></div>
				</div>
			</div>
		</div>
		<div class="template-panel admin-univMng-1-1">
			<div class="middleTitle">대학 상세</div>
		</div>
	</div>
	<div class="template-panel admin-univMng-2">
		<div class="middleTitle">대학 상세 학과</div>
	</div>
</body>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/admin/cmg/enterpriseManagement.css">
<script src="/js/include/admin/cmg/enterpriseManagement.js"></script>
<!-- 제목입니다 -->
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">기업 관리</h2>
<body>
	<div class="admin-EntMng-1" style="margin-bottom: 20px;">
		<div class="template-panel admin-entMng-1-1">
			<div class="middleTitle">기업 리스트</div>
			<div class="filter-box">
				<select name="status">
					<option value="1">전체</option>
					<option value="2">기업명</option>
					<option value="3">지역명</option>
				</select>
				<input type="text" name="keyword" placeholder="기업명으로 검색" />
				<button type="button" class="btn-save">조회</button>
			</div>
			<div class="listEnt">
				<div class="search-filter-bar">
					<p class="ptag-list" style="margin-bottom: 10px">
						총
						<span id="entList-count"></span>
						건
					</p>
				</div>
				<div class="listEntBody">
					<table id="entTable">
						<thead>
							<tr>
								<th class="body-id">ID</th>
								<th class="body-entImg">기업이미지</th>
								<th class="body-entName">기업명</th>
								<th class="body-region">지역</th>
							</tr>
						</thead>
						<tbody id="entList">
						</tbody>
					</table>
				</div>
				<div class="card-footer clearfix">
					<div class="panel-footer pagination entListPage"></div>
				</div>
			</div>
		</div>
		<div class="template-panel admin-entMng-1-1">
			<div class="middleTitle">대학 상세</div>
		</div>
	</div>
	<div class="template-panel admin-entMng-2">
		<div class="middleTitle">대학 상세 학과</div>
	</div>
</body>



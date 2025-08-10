<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/umg/counselorManagement.css">
<script src="/js/include/admin/umg/counselorManagement.js"></script>
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">상담사
	관리</h2>
<body>
	<div class="cnsMng-1">
		<div class="template-panel cnsMng-1-1">
			<div class="middleTitle">상담 예약수</div>
			<img class="image1-1-1-1" alt="" src="/images/admin/admin-image1.png">
			<div class="userCnt-1-1-1">123</div>
			<div class="increase-1-1-1" style="margin-bottom: 10px;">
				<span style="color: rgba(10, 207, 151, 1); margin-right: 5px;">&#9650;</span>
				5.27%
				<div class="increase-1-1-1-p">Since last week</div>
			</div>
			<div class="increase-1-1-1" style="margin-bottom: 10px;">
				<span style="color: rgba(10, 207, 151, 1); margin-right: 5px;">&#9650;</span>
				8.07%
				<div class="increase-1-1-1-p">Since last month</div>
			</div>
			<div class="increase-1-1-1-red">
				<span style="color: rgba(250, 92, 124, 1); margin-right: 5px;">&#9660;</span>
				1.22%
				<div class="increase-1-1-1-p">Since last year</div>
			</div>
		</div>
		<div class="template-panel cnsMng-1-1">
			<div class="middleTitle">월별 상담 통계</div>
			<div class="sparkline-container">
				<canvas id="sparklineChart"></canvas>
			</div>
		</div>
		<div class="template-panel cnsMng-1-1">
			<div class="middleTitle">평균 만족도</div>
		</div>
	</div>
	<div class="cnsMng-2">
		<div class="template-panel cnsMng-2-1">
			<div class="middleTitle">상담사 리스트</div>
			<div class="filter-box">
				<select name="status">
					<option value="1">전체</option>
					<option value="2">이름</option>
					<option value="3">닉네임</option>
					<option value="4">이메일</option>
				</select> <input type="text" name="keyword" placeholder="검색어를 입력하세요" />
				<button type="button" class="btn-save">조회</button>
			</div>
			<div class="search-filter-bar">
				<p class="ptag-list">
					총
					<span id="userList-count"></span>
					건
				</p>

			</div>
			<div class="listMemberBody">
				<table id="userTable">
					<thead>
						<tr>
							<th class="body-id">ID</th>
							<th class="body-memName">회원명</th>
							<th class="body-email">이메일</th>
							<th class="body-nickname">닉네임</th>
						</tr>
					</thead>
					<tbody id="userList">
					</tbody>
				</table>
			</div>
			<div class="card-footer clearfix">
				<div class="panel-footer pagination"></div>
			</div>
		</div>
		<div class="template-panel cnsMng-2-1">
			<div class="middleTitle">상담사 상세</div>
			<div id="member-detail-box">
				<div class="member-header-section">
					<div class="member-profile">
						<img id="member-profile-img" src="/images/defaultProfileImg.png"
							alt="프로필 이미지">
					</div>
					<div class="member-info-grid">
						<span style="display: none">
							<input type="text" id="mem-id" style="display: none">
						</span>
						<div class="info-field">
							<label>이름</label> <input type="text" id="mem-name">
						</div>
						<div class="info-field">
							<label>닉네임</label> <input type="text" id="mem-nickname">
						</div>
						<div class="info-field">
							<label>이메일</label> <input type="text" id="mem-email"
								disabled="disabled">
						</div>
						<div class="info-field">
							<label>전화번호</label> <input type="text" id="mem-phone"
								disabled="disabled">
						</div>
						<div class="info-field">
							<label>생년월일</label> <input type="date" id="mem-birth"
								disabled="disabled">
						</div>
						<div class="info-field">
							<label>성별</label> <input type="text" id="mem-gen"
								disabled="disabled">
						</div>
					</div>
					<div class="member-info-grid">
						<div class="info-field">
							<label>권한</label> <select id="mem-role">
								<option value="R01001">회원</option>
								<option value="R01002">관리자</option>
								<option value="R01003">상담사</option>
								<option value="R01004">상담센터장</option>
							</select>
						</div>
						<div class="info-field">
							<label>로그인 타입</label> <input type="text" id="mem-logType"
								disabled="disabled">
						</div>
					</div>
				</div>

				<div class="member-penalty-section">
					<p>
						<strong class="member-penalty-title">경고 수:</strong>
						<span id="mem-warn-count"></span>
					</p>
					<p>
						<strong class="member-penalty-title">정지 수:</strong>
						<span id="mem-ban-count"></span>
					</p>
				</div>

				<div class="member-interests-section">
					<p>
						<strong class="member-penalty-title">관심 키워드:</strong>
						<span id="mem-interests"></span>
					</p>
				</div>

				<div class="button-group">
					<button class="btn-modify" id="userModify">수정</button>
				</div>
			</div>
		</div>
	</div>
</body>


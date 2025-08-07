<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/admin/umg/memberManagement.css">
<body>
	<h2>회원 관리</h2>
	<div class="template-panel userChart">
		<div class="panel-header tempheader">월간 회원 이용 통계</div>
		<canvas id="memberChart" style="height: 300px;"></canvas>
	</div>
	<div class="flex gap">
		<div class="template-panel listMember">
			<div class="panel-header">회원 리스트</div>
			<div class="search-filter-bar">
				<p class="ptag-list">
					총
					<span id="userList-count"></span>
					건
				</p>
				<div class="filter-box">
					<form action="/csc/admin/noticeList.do" method="get">
						<select name="status">
							<option value="1">전체</option>
							<option value="2">이름</option>
							<option value="3">닉네임</option>
							<option value="4">이메일</option>
							<!-- … -->
						</select>
						<input type="text" name="keyword" placeholder="검색어를 입력하세요" />
						<button type="button" class="btn-save">조회</button>
					</form>
				</div>
			</div>
			<div class="listMemberBody">
				<table id="userTable">
					<thead>
						<tr>
							<th class="body-id">ID</th>
							<th class="body-memName">회원명</th>
							<th class="body-email">이메일</th>
							<th class="body-nickname">닉네임</th>
							<!-- 								<th class="body-phone">연락처</th> -->
							<!-- 								<th class="body-birth">생년월일</th> -->
							<!-- 								<th class="body-logType">로그인타입</th> -->
							<!-- 								<th class="body-point">포인트</th> -->
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
		<div class="template-panel managementMember">
			<div class="panel-header">회원 상세</div>
			<div id="member-detail-box">
				<div class="member-header-section">
					<div class="member-profile">
						<img id="member-profile-img" src="/images/defaultProfileImg.png" alt="프로필 이미지">
					</div>
					<div class="member-info-grid">
						<div class="info-field">
							<label>이름</label>
							<input type="text" id="mem-name">
						</div>
						<div class="info-field">
							<label>닉네임</label>
							<input type="text" id="mem-nickname">
						</div>
						<div class="info-field">
							<label>이메일</label>
							<input type="text" id="mem-email">
						</div>
						<div class="info-field">
							<label>전화번호</label>
							<input type="text" id="mem-phone">
						</div>
						<div class="info-field">
							<label>생년월일</label>
							<input type="text" id="mem-birth">
						</div>
						<div class="info-field">
							<label>성별</label>
							<input type="text" id="mem-gen">
						</div>
					</div>
				</div>

				<div class="member-penalty-section">
					<p>
						<strong>경고 수:</strong>
						<span id="mem-warn-count"></span>
					</p>
					<p>
						<strong>정지 수:</strong>
						<span id="mem-ban-count"></span>
					</p>
				</div>

				<div class="member-interests-section">
					<p>
						<strong>관심 키워드:</strong>
						<span id="mem-interests"></span>
					</p>
				</div>

				<div class="button-group">
					<button class="btn-modify">수정</button>
					<button class="btn-warn">경고</button>
					<button class="btn-ban">정지</button>
				</div>
			</div>
		</div>
		<div class="template-panel insertMember">
			<div class="panel-header">회원 등록</div>
			<div class="member-form">
				<div class="member-form-left">
					<label>회원 ID</label>
					<input type="text">
					<label>회원 프로필</label>
					<div class="profile-wrapper">
						<img src="/images/defaultProfileImg.png" alt="프로필">
						<label class="upload-icon" for="profileUpload">+</label>
						<input type="file" id="profileUpload">
					</div>

					<label>회원 권한</label>
					<select>
						<option>선택</option>
					</select>
				</div>
				<div class="member-form-right">
					<label>회원명</label>
					<input type="text">
					<label>닉네임</label>
					<input type="text">
					<label>이메일</label>
					<input type="email">
					<label>연락처</label>
					<input type="text">
					<label>성별</label>
					<input type="text">
					<label>생년월일</label>
					<input type="date">
				</div>
			</div>
			<button class="add-btn">추가</button>
		</div>

	</div>
	<div class="memberManagementRight"></div>

</body>
<script src="/js/include/admin/umg/memberManagement.js"></script>

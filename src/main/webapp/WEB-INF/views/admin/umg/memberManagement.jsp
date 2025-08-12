<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/admin/umg/memberManagement.css">
<script src="/js/include/admin/umg/memberManagement.js"></script>
	<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">회원 관리</h2>
	<div class="flex">
		<div class="management-left">
			<div class="template-panel searchBox">
				<div class="filter-box">
					<select name="status">
						<option value="1">전체</option>
						<option value="2">이름</option>
						<option value="3">닉네임</option>
						<option value="4">이메일</option>
					</select>
					<input type="text" name="keyword" placeholder="검색어를 입력하세요" />
					<button type="button" class="btn-save">조회</button>
				</div>
			</div>
			<div class="flex gap">
				<div class="template-panel listMember">
					<div class="middleTitle">회원 리스트</div>
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
			</div>
		</div>
		<div class="management-right">
			<div class="template-panel managementMember">
				<div class="middleTitle">회원 상세</div>
				<div id="member-detail-box">
					<div class="member-header-section">
						<div class="member-profile">
							<img id="member-profile-img" src="/images/defaultProfileImg.png" alt="프로필 이미지">
						</div>
						<div class="member-info-grid">
							<span style="display: none">
								<input type="text" id="mem-id" style="display: none">
							</span>
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
								<input type="text" id="mem-email" disabled="disabled">
							</div>
							<div class="info-field">
								<label>전화번호</label>
								<input type="text" id="mem-phone" disabled="disabled">
							</div>
							<div class="info-field">
								<label>생년월일</label>
								<input type="date" id="mem-birth" disabled="disabled">
							</div>
							<div class="info-field">
								<label>성별</label>
								<input type="text" id="mem-gen" disabled="disabled">
							</div>
						</div>
						<div class="member-info-grid">
							<div class="info-field">
								<label>권한</label>
								<select id="mem-role">
									<option value="R01001">회원</option>
									<option value="R01002">관리자</option>
									<option value="R01003">상담사</option>
									<option value="R01004">상담센터장</option>
								</select>
							</div>
							<div class="info-field">
								<label>로그인 타입</label>
								<input type="text" id="mem-logType" disabled="disabled">
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
			<div class="template-panel insertMember">
				<div class="middleTitle">회원 등록</div>
				<div class="member-form">
					<div class="member-form-left">
						<label>회원 이메일</label>
						<input id="insertEmail" type="text">
						<button class="btn-temp" id="emailDoubleCheck">이메일 중복확인</button>
						<label>회원 프로필</label>
						<div class="profile-wrapper">
							<img src="/images/defaultProfileImg.png" alt="프로필">
							<label class="upload-icon" for="profileUpload">+</label>
							<input type="file" id="profileUpload">
						</div>

						<label>회원 권한</label>
						<select id="insertRole">
							<option value="R01001">일반</option>
							<option value="R01002">관리자</option>
							<option value="R01003">상담사</option>
							<option value="R01004">상담센터장</option>
						</select>
					</div>
					<div class="member-form-right">
						<label>회원명</label>
						<input id="insertName" type="text">
						<label>닉네임</label>
						<input id="insertNickname" type="text">
						<button class="btn-temp" id="nicknameDoubleCheck">닉네임 중복확인</button>
						<label>비밀번호</label>
						<input id="insertPassword" type="password">
						<label>연락처</label>
						<input id="insertPhone" type="text">
					</div>
					<div class="member-form-extra">
						<label>성별</label>
						<select id="insertGen" type="text">
							<option value="G11001">남자</option>
							<option value="G11002">여자</option>
						</select>
						<label>생년월일</label>
						<input id="insertBirth" type="date">
					</div>
				</div>
				<button class="add-btn">추가</button>
			</div>
		</div>
	</div>


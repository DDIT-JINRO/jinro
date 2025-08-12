<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/admin/cmg/enterpriseManagement.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<script src="/js/include/admin/cmg/enterpriseManagement.js"></script>
<!-- 제목입니다 -->
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">기업
	관리</h2>
<body>
	<div class="admin-EntMng-1" style="margin-bottom: 20px;">
		<div class="template-panel admin-entMng-1-1">
			<div class="middleTitle">기업 리스트</div>
			<div class="filter-box">
				<select name="status">
					<option value="1">전체</option>
					<option value="2">기업명</option>
					<option value="3">지역명</option>
				</select> <input type="text" name="keyword" placeholder="기업명으로 검색" />
				<button type="button" class="btn-save" id="btnSearch">조회</button>
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

		<div>
			<div class="entDetail-container">
				<div class="ent-profile-container">
					<!-- 좌측 프로필 -->
					<div class="ent-profile-left">
						<div class="profile-card">
							<img id="entLogo" src="http://www.work.go.kr/framework/filedownload/getImage.do?filePathName=tqyczLUvysXSEcOVhPgDCrrcRciqlR8qoTAjsPuEp44sKoGxe9VyqEeBMF5IUTkBaD8EvNXUzF3cvyArjHjQkV5OHVJHQRI4rj9jllpoHbs%3D" alt="기업 로고" class="profile-logo">
							<h3 id="entName" class="profile-name">한국개발연구원국제정책대학원대학교</h3>
							<p id="entRole" class="profile-role">South Korea / 대한민국</p>

							<div class="profile-actions">
								<div class="btn-follow">G30UNK</div>
								<div class="btn-message">미지정</div>
							</div>

							<div class="profile-about">
								<h4>ABOUT</h4>
							</div>

							<div class="profile-info">
								<p>
									<strong>기업ID:</strong>
									<span id="entId">-</span>
								</p>
								<p>
									<strong>기업명:</strong>
									<span id="entName">contact@example.com</span>
								</p>
								<p>
									<strong>기업규모:</strong>
									<span id="entScale">-</span>
								</p>
								<p>
									<strong>주소:</strong>
									<span id="entAddress">서울특별시 강남구</span>
								</p>
							</div>


						</div>
					</div>

					<!-- 우측 상세 내용 -->
					<div>
						<div class="ent-profile-right" style="margin-bottom: 20px;">
							<!-- 탭 메뉴 -->
							<div>
								<div class="tab-menu">
									<button class="tab-btn active" data-tab="about">About</button>
									<button class="tab-btn" data-tab="timeline">Timeline</button>
								</div>
								<!-- 탭 내용 -->
								<div class="tab-content active" id="about">
									<div class="profile-social">
										<a href="" target="_blank">
											<i class="fas fa-link"></i>&nbsp;&nbsp;&nbsp; URL Link
										</a>
									</div>
								</div>

								<div class="tab-content" id="timeline">
									<h4>연혁</h4>
									<p>-</p>
								</div>

							</div>
						</div>
						<div class="template-panel"></div>
					</div>
				</div>
			</div>
			<div class="template-panel entAbout" id="entDetailAbout" style="margin-top: 20px; height: 330px;">

			</div>
		</div>
	</div>

	<div class="template-panel admin-entMng-2">
		<div class="middleTitle">신규 기업 등록</div>
	</div>
</body>



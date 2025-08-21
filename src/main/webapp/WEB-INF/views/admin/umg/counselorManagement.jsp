<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/umg/counselorManagement.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<script src="/js/include/admin/umg/counselorManagement.js"></script>
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">상담사 관리</h2>
<input type="hidden" id="comCalendarInput" style="display: none;" />
<body>
	<div class="flex gap cnsMg-1" style="margin-bottom: 20px;">
		<div class="template-panel public-countCard ">
			<div class="public-card-title">월별 상담 신청 수</div>
			<img class="public-card-icon" alt="" src="/images/admin/admin-image1.png">
			<div class="public-card-count" id="monthlyCnsCount">123</div>
			<div class="public-span-space">
				<span id="monthlyCnsApp" class="public-span-increase">▲13.5%</span>
				<div class=public-span-since>Since last day</div>
			</div>
		</div>
		<div class="template-panel public-countCard back-color-red">
			<div class="public-card-title color-white">일일 대면상담 신청수</div>
			<img class="public-card-icon" alt="" src="/images/admin/admin-offlineCns.png">
			<div class="public-card-count color-white" id="dailyOffCnsCount">123</div>
			<div class="public-span-space">
				<span class="public-span-increase color-white" id="dailyOffCnsRate">▲13.5%</span>
				<div class="public-span-since color-white">Since last day</div>
			</div>
		</div>
		<div class="template-panel public-countCard back-color-green">
			<div class="public-card-title color-white">일일 채팅상담 신청수</div>
			<img class="public-card-icon" alt="" src="/images/admin/admin-chatCns.png">
			<div class="public-card-count color-white" id="dailyChatCnsCount">123</div>
			<div class="public-span-space">
				<span class="public-span-increase color-white" id="dailyChatCnsRate">▲13.5%</span>
				<div class="public-span-since color-white">Since last day</div>
			</div>
		</div>
		<div class="template-panel public-countCard back-color-purple">
			<div class="public-card-title color-white">일일 화상상담 신청수</div>
			<img class="public-card-icon" alt="" src="/images/admin/admin-videoCns.png">
			<div class="public-card-count color-white" id="dailyVideoCnsCount">123</div>
			<div class="public-span-space">
				<span class="public-span-increase color-white" id="dailyVideoCnsRate">▲13.5%</span>
				<div class="public-span-since color-white">Since last day</div>
			</div>
		</div>
	</div>
	<div class="cnsMg-2 flex gap" style="margin-bottom: 20px;">
		<div class="cnsMg-2-1 gap">
			<div class="cnsMg-2-1-1 flex gap" style="margin-bottom: 20px;">
				<div class="template-panel cnsCateChart">
					<div class="middleTitle">상담 유형별 통계</div>
					<div class="flex gap5" style="justify-content: flex-end;">
						<input type="hidden" id="cnsCateChartStartDay" />
						<input type="hidden" id="cnsCateChartEndDay" />
						<select class="public-toggle-select" id="cnsCateChartDateType">
							<option value="daily">일간</option>
							<option value="monthly">월간</option>
							<option value="selectDays">기간</option>
						</select> <select class="public-toggle-select" id="cnsCateChartGender">
							<option value="">성별전체</option>
							<option value="G11001">남자</option>
							<option value="G11002">여자</option>
						</select> <select class="public-toggle-select" id="cnsCateChartAgeGroup">
							<option value="">연령전체</option>
							<option value="">청년</option>
							<option value="">청소년</option>
						</select>
					</div>
				</div>
				<div class="template-panel cnsTop3Chart">
					<div class="middleTitle">상담사 TOP3</div>
					<div class="flex gap5" style="justify-content: flex-end;">
						<select class="public-toggle-select" id="cnsTop3ChartType">
							<option value="">만족도</option>
							<option value="">후기건수</option>
							<option value="">상담건수</option>
						</select> <select class="public-toggle-select" id="cnsTop3ChartDate">
							<option value="">일간</option>
							<option value="">월간</option>
							<option value="">기간</option>
						</select>
					</div>
				</div>
			</div>

			<div class="template-panel cnsTypeChart">
				<div class="middleTitle">상담 종류별 통계</div>
				<div class="flex gap5" style="justify-content: flex-end;">
					<select class="public-toggle-select" id="cnsTypeChartDate">
						<option value="">일간</option>
						<option value="">월간</option>
						<option value="">기간</option>
					</select> <select class="public-toggle-select" id="cnsTop3ChartGen">
						<option value="">성별전체</option>
						<option value="">남자</option>
						<option value="">여자</option>
					</select> <select class="public-toggle-select" id="cnsTop3ChartAgeGroup">
						<option value="">연령전체</option>
						<option value="">청년</option>
						<option value="">청소년</option>
					</select>
				</div>
			</div>
		</div>
		<div class="cnsMg-2-2 template-panel">
			<div class="middleTitle">상담 시간대 통계</div>
			<div class="flex gap5" style="justify-content: flex-end;">
				<select class="public-toggle-select" id="cnsHoursChartDate">
					<option value="">일간</option>
					<option value="">월간</option>
					<option value="">기간</option>
				</select> <select class="public-toggle-select" id="cnsHoursChartGen">
					<option value="">성별전체</option>
					<option value="">남자</option>
					<option value="">여자</option>
				</select> <select class="public-toggle-select" id="cnsHoursChartAgeGroup">
					<option value="">연령전체</option>
					<option value="">청년</option>
					<option value="">청소년</option>
				</select>
			</div>
		</div>
	</div>
	<div class="cnsMg-3 flex gap" style="margin-bottom: 20px;">
		<div class="template-panel cnsCompleteAndReviewList">
			<div class="middleTitle">상담사별 처리 건수 및 만족도 평가</div>
			<div class="public-listSearch">
				<select name="status" id="activityFilter">
					<option value="1">전체</option>
					<option value="2">이름</option>
					<option value="3">이메일</option>
				</select>
				<input id="search" name="keyword" placeholder="검색어를 입력하세요" />
				<button class="btn-save searchUserBtn">조회</button>
			</div>
			<div style="display: flex; justify-content: space-between; margin-top: 60px;">
				<div class="btn-group flex gap5 userListBtnGroup">
					<button class="public-toggle-button active" id="userListId">ID</button>
					<button class="public-toggle-button" id="userListName">이름</button>
					<button class="public-toggle-button" id="userListEmail">이메일</button>
					<select class="public-toggle-select" id="userListSortOrder">
						<option value="asc">오름차순</option>
						<option value="desc">내림차순</option>
					</select>
				</div>
				<select class="public-toggle-select selectUserList-top" id="userListStatus">
					<option value="">전체</option>
					<option value="online">활동중</option>
					<option value="offline">비활동</option>
					<option value="suspended">정지상태</option>

				</select>
			</div>
			<div class="cnsListSpace">
				<div class="search-filter-bar">
					<p class="ptag-list">
						총
						<span id="cnsList-count"></span>
						건
					</p>
				</div>
				<table id="cnsListTable">
					<thead>
						<tr>
							<th class="body-id">ID</th>
							<th class="body-memName">상담사명</th>
							<th class="body-email">상담건수</th>
							<th class="body-status">후기건수</th>
							<th class="body-status">만족도</th>
						</tr>
					</thead>
					<tbody class="cnsList" id="cnsList">
						<tr>
							<td>1</td>
							<td>홍길동</td>
							<td>1</td>
							<td>1</td>
							<td><span class="star-rating">★★★★★</span></td>
						</tr>
						<tr>
							<td>1</td>
							<td>홍길동</td>
							<td>1</td>
							<td>1</td>
							<td><span class="star-rating">★★★★★</span></td>
						</tr>
						<tr>
							<td>1</td>
							<td>홍길동</td>
							<td>1</td>
							<td>1</td>
							<td><span class="star-rating">★★★★★</span></td>
						</tr>
						<tr>
							<td>1</td>
							<td>홍길동</td>
							<td>1</td>
							<td>1</td>
							<td><span class="star-rating">★★★★★</span></td>
						</tr>
						<tr>
							<td>1</td>
							<td>홍길동</td>
							<td>1</td>
							<td>1</td>
							<td><span class="star-rating">★★★★★</span></td>
						</tr>
						<tr>
							<td>1</td>
							<td>홍길동</td>
							<td>1</td>
							<td>1</td>
							<td><span class="star-rating">★★★★★</span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="card-footer clearfix">
				<div class="panel-footer pagination" id="cnsListPagenation"></div>
			</div>
		</div>
		<div>
			<div class="template-panel cns-info-detail">
				<div class="middleTitle">상담사 상세정보</div>
				<div id="cns-detail-box">
					<div class="cns-header-section flex">
						<div class="cns-profile">
							<img id="cns-profile-img" src="/images/defaultProfileImg.png" alt="프로필 이미지">
						</div>
						<div class="cns-info-grid">
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

				</div>
				<div class="cns-detail-info-count">
					<p>
						<span class="info-history2">상담 횟수:</span>
						<span id="counselor-cns-count">1회</span>
					</p>
					<p>
						<span class="info-history2">휴가 횟수:</span>
						<span id="counselor-vac-count"></span>
					</p>
					<p>
						<span class="info-history2">상담 평점:</span>
						<span id="counselor-review-point"></span>
					</p>
				</div>
				<div class="flex" style="justify-content: flex-end; margin-top: auto;">
					<button class="btn-primary" id="cnsModify">
						<i class="fas fa-save"></i>저장
					</button>
				</div>
			</div>
			<div class="template-panel cnsDetailListSpace">
				<div class="middleTitle">상담 내역</div>
				<table id="cnsDetailListTable">
					<thead>
						<tr>
							<th class="body-id">상담ID</th>
							<th class="body-cnsCate">상담유형</th>
							<th class="body-cnsType">상담종류</th>
							<th class="body-cnsDate">상담일시</th>
							<th class="body-reviewPonit">만족도</th>
							<th class="body-cnsStatus">상태</th>
						</tr>
					</thead>
					<tbody class="cnsDetailList" id="cnsDetailList">
						<tr>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td><span class="star-rating">★★★★★</span></td>
							<td>asd</td>
						</tr>
						<tr>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td><span class="star-rating">★★★★★</span></td>
							<td>asd</td>
						</tr>
						<tr>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td><span class="star-rating">★★★★★</span></td>
							<td>asd</td>
						</tr>
						<tr>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td><span class="star-rating">★★★★★</span></td>
							<td>asd</td>
						</tr>
						<tr>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td>asd</td>
							<td><span class="star-rating">★★★★★</span></td>
							<td>asd</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="card-footer clearfix">
				<div class="panel-footer pagination" id="cnsListPagenation"></div>
			</div>
		</div>

	</div>
</body>


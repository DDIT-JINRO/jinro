<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/las/contentUsageStatistics.css">
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">컨텐츠
	이용 통계</h2>

<div class="contentUsage-1 flex gap">
	<div class="contentUsage-1-1">
		<div class="contentUsage-1-1-1 flex gap">
			<div class="template-panel public-countCard">
				<div class="public-card-title">일일 게시글 작성수</div>
				<img class="public-card-icon" alt=""
					src="/images/admin/admin-boardList.png">
				<div class="public-card-count" id="">123</div>
				<div class="public-span-space">
					<span class="public-span-increase" id="">▲&nbsp;3.0%</span>
					<div class=public-span-since>Since last day</div>
				</div>
			</div>
			<div class="template-panel public-countCard">
				<div class="public-card-title">일일 북마크 수</div>
				<img class="public-card-icon" alt=""
					src="/images/admin/admin-bookmark.png">
				<div class="public-card-count" id="">321</div>
				<div class="public-span-space">
					<span class="public-span-increase" id="">▲&nbsp;12.5%</span>
					<div class=public-span-since>Since last day</div>
				</div>
			</div>
			<div class="template-panel public-countCard">
				<div class="public-card-title">일일 채팅방 개설 수</div>
				<img class="public-card-icon" alt=""
					src="/images/admin/admin-chat.png">
				<div class="public-card-count" id="">123</div>
				<div class="public-span-space">
					<span class="public-span-decrease" id="">▼&nbsp;10.0%</span>
					<div class=public-span-since>Since last day</div>
				</div>
			</div>
		</div>
		<div class="contentUsage-1-1-2 template-panel roadmapAndWorldCupSpace">
			<div class="middleTitle">월드컵 로드맵 이용 현황</div>
			<div class="btn-group flex gap5 roadmapAndWorldSelectGroup">
				<input type="hidden" id="" /> <input type="hidden" id="" /> <select
					class="public-toggle-select" name="" id="">
					<option value="daily">일별</option>
					<option value="monthly">월별</option>
					<option value="selectDays">기간선택</option>
				</select> <select class="public-toggle-select" name="" id="">
					<option value="">성별전체</option>
					<option value="male">남자</option>
					<option value="female">여자</option>
				</select> <select class="public-toggle-select" name="" id="">
					<option value="">연령전체</option>
					<option value="teen">청소년</option>
					<option value="youth">청년</option>
				</select>
			</div>
		</div>
	</div>
	<div class="constentUsage-1-2">
		<div class="constentUsage-1-2-1 template-panel back-color-purple">
			<div class="middleTitle constentUsage-1-2-1-title">월드컵 콘텐츠 인기
				순위 TOP5</div>
			<img class="public-card-icon trophyImg" alt=""
				src="/images/admin/admin-worldcup.png">
			<div class="constentUsage-1-2-1-p">
				<p>1. 간호사</p>
				<p>2. 경찰</p>
				<p>3. 소방관</p>
				<p>4. 변호사</p>
				<p>5. 의사</p>
			</div>
			<div class="circleDiv">
				<img class="circleDsImg" alt=""
					src="/images/admin/admin-circleDs.png">
			</div>
		</div>
		<div class="template-panel roadmapStepCount">
			<div class="middleTitle">로드맵 진행단계 분포수</div>
			<div>
				<div class="roadmapStepCount-stepCount">
					<div class="roadmapStepCount-step flex">
						<img alt="" src="/images/admin/admin-roadstep1.png">
						<div class="roadmapStepCount-step">
							<div class="roadmapStepCount-stepTitle">1단계</div>
							<div class="roadmapStepCount-stepCnt">40,300명</div>
						</div>
					</div>
					<div class="borderBottom"></div>
				</div>
				<div class="roadmapStepCount-stepCount">
					<div class="roadmapStepCount-step flex">
						<img alt="" src="/images/admin/admin-roadstep2.png">
						<div class="roadmapStepCount-step">
							<div class="roadmapStepCount-stepTitle">2단계</div>
							<div class="roadmapStepCount-stepCnt">40,300명</div>
						</div>
					</div>
					<div class="borderBottom"></div>
				</div>
				<div class="roadmapStepCount-stepCount">
					<div class="roadmapStepCount-step flex">
						<img alt="" src="/images/admin/admin-roadstep3.png">
						<div class="roadmapStepCount-step">
							<div class="roadmapStepCount-stepTitle">3단계</div>
							<div class="roadmapStepCount-stepCnt">40,300명</div>
						</div>
					</div>
					<div class="borderBottom"></div>
				</div>
				<div class="roadmapStepCount-stepCount">
					<div class="roadmapStepCount-step flex">
						<img alt="" src="/images/admin/admin-roadstep4.png">
						<div class="roadmapStepCount-step">
							<div class="roadmapStepCount-stepTitle">4단계</div>
							<div class="roadmapStepCount-stepCnt">40,300명</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="contentUsage-2 flex gap">
	<div class="template-panel commOnceView">
		<div class="middleTitle">커뮤니티 한눈에 보기</div>
		<div class="commOnceView-box flex">
			<div class="commOnceView-box-text">
				<div class="commOnceView-box-title">총 게시글 수</div>
				<div class="commOnceView-box-cnt color-purple">3,456</div>
			</div>
			<div>
				<img class="commOnceViewImg" alt="" src="/images/admin/admin-circle-board.png">
			</div>
		</div>
		<div class="commOnceView-box flex">
			<div class="commOnceView-box-text">
				<div class="commOnceView-box-title">총 좋아요 수</div>
				<div class="commOnceView-box-cnt color-red">3,456</div>
			</div>
			<div>
				<img class="commOnceViewImg" alt="" src="/images/admin/admin-circle-heart.png">
			</div>
		</div>
		<div class="commOnceView-box flex">
			<div class="commOnceView-box-text">
				<div class="commOnceView-box-title">총 댓글 수</div>
				<div class="commOnceView-box-cnt color-green">3,456</div>
			</div>
			<div>
				<img class="commOnceViewImg" alt="" src="/images/admin/admin-circle-reply.png">
			</div>
		</div>
	</div>
	
	<div class="template-panel newBodrdList">
		<div class="middleTitle">최신 커뮤니티 목록</div>
	</div>
	
</div>








<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />



<div id="cus-root" class="template-container" data-page="content-usage">

	<!-- 좌/우 2열 레이아웃 -->
	<div class="left-column">
		<!-- 좌상단: 북마크 카테고리별(남/여 스택) -->
		<section class="template-panel">
			<div class="panel-header">
				북마크 카테고리별 통계현황
				<div class="btn-group" id="bmGenderGroup">
					<!-- 좌상단 차트 전용 성별 토글 3개 -->
					<button type="button" class="btn-gender active" data-gender="ALL">전체</button>
					<button type="button" class="btn-gender" data-gender="G11001">남</button>
					<button type="button" class="btn-gender" data-gender="G11002">여</button>

					<select id="cusAgeBand" class="compact">
						<option value="ALL">연령 전체</option>
						<option value="U15">~14</option>
						<option value="15-19">15-19</option>
						<option value="20-24">20-24</option>
						<option value="25-29">25-29</option>
						<option value="30+">30+</option>
					</select>
					<button type="button" id="calBtnBMC">달력</button>
					<input type="hidden" id="fromBMC" /> <input type="hidden"
						id="toBMC" />
				</div>
			</div>
			<div class="chart">
				<canvas id="bmCategoryChart"></canvas>
			</div>
		</section>

		<!-- 좌하단: 북마크 상세 TOP5 -->
		<section class="template-panel">
			<div class="panel-header">
				북마크 상세 현황 (TOP)
				<div class="btn-group" id="bmkGroup">
					<button type="button" class="btn-gender active" data-gender="ALL">전체</button>
					<button type="button" class="btn-gender" data-gender="G11001">남</button>
					<button type="button" class="btn-gender" data-gender="G11002">여</button>
					<select id="bmTopCategory" class="compact">
						<option value="ALL">카테고리 전체</option>
						<option value="G03001">대학</option>
						<option value="G03002">기업</option>
						<option value="G03004">직업</option>
						<option value="G03005">이력서템플릿</option>
						<option value="G03006">학과</option>
					</select> <select id="bmtAgeBand" class="compact">
						<option value="ALL">연령 전체</option>
						<option value="U15">~14</option>
						<option value="15-19">15-19</option>
						<option value="20-24">20-24</option>
						<option value="25-29">25-29</option>
						<option value="30+">30+</option>
					</select> <select id="bmTopLimit" class="compact">
						<option>5</option>
						<option>10</option>
					</select>
					<button type="button" id="calBtnBMT">달력</button>
					<input type="hidden" id="fromBMT" /> <input type="hidden"
						id="toBMT" />
				</div>
			</div>
			<div class="chart">
				<canvas id="bmTopChart"></canvas>
			</div>
			<div class="table-wrapper visually-hidden">
				<table>
					<thead>
						<tr>
							<th>이름</th>
							<th>건수</th>
						</tr>
					</thead>
					<tbody id="bmTopTable"></tbody>
				</table>
			</div>
		</section>
	</div>

	<div class="right-column">
		<!-- 우상단: 커뮤니티 이용현황 -->
		<section class="template-panel">
			<div class="panel-header">
				커뮤니티 이용현황
				<div class="btn-group">
					<button type="button" class="btn-tab active" data-tab="post">작성
						추이</button>
					<button type="button" class="btn-tab" data-tab="react">반응
						추이</button>
					<!-- 추가 -->
					<button type="button" class="btn-tab" data-tab="topMembers">활동
						회원 TOP</button>
					<button type="button" class="btn-tab" data-tab="topPosts">반응
						글 TOP</button>

					<select id="cusCcId" class="compact" style="margin-left: 8px">
						<option value="ALL">게시판 전체</option>
						<option value="G09001">청소년 커뮤니티</option>
						<option value="G09005">스터디그룹</option>
						<option value="G09006">청년 커뮤니티</option>
					</select>
				</div>
			</div>

			<div class="chart" id="communityPostWrap">
				<canvas id="communityPostChart"></canvas>
			</div>
			<div class="chart" id="communityReactWrap" style="display: none;">
				<canvas id="communityReactChart"></canvas>
			</div>

			<!-- 추가: 표 2종 -->
			<div class="table-wrap" id="communityTopMembersWrap"
				style="display: none;">
				<div class="table-toolbar">
					<label>표시</label> <select id="topMembersLimit" class="compact"><option>5</option>
						<option>10</option></select>
				</div>
				<table class="rank-table">
					<thead>
						<tr>
							<th>회원</th>
							<th>글</th>
							<th>댓글</th>
							<th>좋아요</th>
							<th>점수</th>
						</tr>
					</thead>
					<tbody id="communityTopMembersBody"></tbody>
				</table>
			</div>

			<div class="table-wrap" id="communityTopPostsWrap"
				style="display: none;">
				<div class="table-toolbar">
					<label>표시</label> <select id="topPostsLimit" class="compact"><option>5</option>
						<option>10</option></select>
				</div>
				<table class="rank-table">
					<thead>
						<tr>
							<th>제목</th>
							<th>게시판</th>
							<th>댓글</th>
							<th>좋아요</th>
							<th>총반응</th>
						</tr>
					</thead>
					<tbody id="communityTopPostsBody"></tbody>
				</table>
			</div>
		</section>

		<!-- 우하단: 북마크 · 월드컵 + 인기직업 -->
		<section class="template-panel">
			<div class="panel-header">
				월드컵 · 로드맵 이용현황
				<div class="btn-group">
					<button type="button" id="btnWorldcupTop">인기 직업 TOP</button>
					<select id="worldcupTopLimit" class="compact"><option>5</option>
						<option>10</option></select>
				</div>
				<!-- 우하단 모드 토글 (월드컵·로드맵 / 로드맵 생성·완료) -->
				<div class="btn-group" id="brModeGroup" style="margin-left: auto">
					<button type="button" class="btn-toggle active" data-mode="WR">월드컵·로드맵</button>
					<button type="button" class="btn-toggle" data-mode="RC">로드맵
						생성·완료</button>
				</div>
			</div>
			<div class="chart two-col">
				<div class="cell">
					<canvas id="bwSummaryChart"></canvas>
				</div>
				<div class="cell">
					<canvas id="bwTrendChart"></canvas>
				</div>
			</div>
			<!-- 추가: 하단 보조 차트 -->
			<div class="chart" id="worldcupTopWrap" style="display: none;">
				<canvas id="worldcupTopChart"></canvas>
			</div>
		</section>
	</div>
</div>

<!-- 페이지 전용 스크립트(사이드바 로더가 body에 붙여줌) -->
<script src="/js/include/admin/las/contentUsageStatistics.js"></script>

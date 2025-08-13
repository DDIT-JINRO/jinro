<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/las/contentUsageStatistics.css">
<h3>로그 및 통계 > 접속/이용 통계</h3>
<!-- 기간 전용 툴바 (filter-bar 삭제 대체) -->
<button type="button" id="cusDateBtn" class="btn btn-range">기간 선택</button>
<input type="text" id="cusDateInput" class="visually-hidden" />
<span id="cusDateLabel" class="date-label"></span>
<button type="button" id="cusDateApply" class="btn btn-apply">기간 적용</button>
<!-- 상단 필터 바 -->
<div id="cus-root" class="template-container" data-page="content-usage">

  <!-- 좌/우 2열 레이아웃 -->
  <div class="left-column">
    <!-- 좌상단: 북마크 카테고리별(남/여 스택) -->
    <section class="template-panel">
      <div class="panel-header">북마크 카테고리별 통계현황
        <div class="btn-group" id="bmGenderGroup">
		  <!-- 좌상단 차트 전용 성별 토글 3개 -->
		  <button type="button" class="btn-gender active" data-gender="ALL">전체</button>
		  <button type="button" class="btn-gender" data-gender="G11001">남</button>
		  <button type="button" class="btn-gender" data-gender="G11002">여</button>

		  <!-- 공통 적용: 성별/연령 -->
		  <select id="cusGender" class="compact">
		    <option value="ALL">성별 전체</option>
		    <option value="G11001">남</option>
		    <option value="G11002">여</option>
		  </select>
		  <select id="cusAgeBand" class="compact">
		    <option value="ALL">연령 전체</option>
		    <option value="U15">~14</option>
		    <option value="15-19">15-19</option>
		    <option value="20-24">20-24</option>
		    <option value="25-29">25-29</option>
		    <option value="30+">30+</option>
		  </select>
		  <button type="button" id="cusDemoApply" class="btn btn-apply">성별/연령 적용</button>
		</div>
      </div>
      <div class="chart">
        <canvas id="bmCategoryChart"></canvas>
      </div>
    </section>

    <!-- 좌하단: 북마크 상세 TOP5 -->
    <section class="template-panel">
      <div class="panel-header">북마크 상세 현황 (TOP5)
        <div class="btn-group">
          <select id="bmTopCategory" class="compact">
            <option value="ALL">전체</option>
            <option value="G03001">대학</option>
            <option value="G03002">기업</option>
            <option value="G03004">직업</option>
            <option value="G03005">이템플릿</option>
            <option value="G03006">학과</option>
          </select>
          <select id="bmTopLimit" class="compact">
            <option>5</option><option>10</option>
          </select>
        </div>
      </div>
      <div class="chart">
        <canvas id="bmTopChart"></canvas>
      </div>
      <div class="table-wrapper visually-hidden">
        <table><thead><tr><th>이름</th><th>건수</th></tr></thead><tbody id="bmTopTable"></tbody></table>
      </div>
    </section>
  </div>

  <div class="right-column">
	<!-- 우상단: 커뮤니티 이용현황 -->
	<section class="template-panel">
	  <div class="panel-header">커뮤니티 이용현황
	    <div class="btn-group">
	      <button type="button" class="btn-tab active" data-tab="post">작성 추이</button>
	      <button type="button" class="btn-tab" data-tab="react">반응 추이</button>
	      <!-- 추가 -->
	      <button type="button" class="btn-tab" data-tab="topMembers">활동 회원 TOP</button>
	      <button type="button" class="btn-tab" data-tab="topPosts">반응 글 TOP</button>
	    </div>
	  </div>

	  <div class="chart" id="communityPostWrap"><canvas id="communityPostChart"></canvas></div>
	  <div class="chart" id="communityReactWrap" style="display:none;"><canvas id="communityReactChart"></canvas></div>

	  <!-- 추가: 표 2종 -->
	  <div class="table-wrap" id="communityTopMembersWrap" style="display:none;">
	    <div class="table-toolbar">
	      <label>표시</label>
	      <select id="topMembersLimit" class="compact"><option>5</option><option>10</option></select>
	    </div>
	    <table class="rank-table">
	      <thead><tr><th>회원</th><th>글</th><th>댓글</th><th>좋아요</th><th>점수</th></tr></thead>
	      <tbody id="communityTopMembersBody"></tbody>
	    </table>
	  </div>

	  <div class="table-wrap" id="communityTopPostsWrap" style="display:none;">
	    <div class="table-toolbar">
	      <label>표시</label>
	      <select id="topPostsLimit" class="compact"><option>5</option><option>10</option></select>
	    </div>
	    <table class="rank-table">
	      <thead><tr><th>제목</th><th>게시판</th><th>댓글</th><th>좋아요</th><th>총반응</th></tr></thead>
	      <tbody id="communityTopPostsBody"></tbody>
	    </table>
	  </div>
	</section>

	<!-- 우하단: 북마크 · 월드컵 + 인기직업 -->
	<section class="template-panel">
	  <div class="panel-header">북마크 · 월드컵 이용현황
	    <div class="btn-group">
	      <button type="button" id="btnWorldcupTop">인기 직업 TOP</button>
	      <select id="worldcupTopLimit" class="compact"><option>5</option><option>10</option></select>
	    </div>
	  </div>
	  <div class="chart two-col">
	    <div class="cell"><canvas id="bwSummaryChart"></canvas></div>
	    <div class="cell"><canvas id="bwTrendChart"></canvas></div>
	  </div>
	  <!-- 추가: 하단 보조 차트 -->
	  <div class="chart" id="worldcupTopWrap" style="display:none;">
	    <canvas id="worldcupTopChart"></canvas>
	  </div>
	</section>
  </div>
</div>

<!-- 페이지 전용 스크립트(사이드바 로더가 body에 붙여줌) -->
<script src="/js/include/admin/las/contentUsageStatistics.js"></script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/js/include/admin/cmg/reviewManagement.js"></script>
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">후기 관리</h2>
<body>
	<div class="scdMng-2">
		<div class="template-panel scdMng-2-1">
			<div class="middleTitle">후기 목록</div>
			
			<div class="public-listSearch">
				<select name="status">
					<option value="">전체</option>
					<option value="target">면접처</option>
					<option value="content">내용</option>
					<option value="writer">작성자</option>
				</select>
				
				<input id="search" name="keyword" placeholder="검색어를 입력하세요">
				<button class="btn-save searchReportBtn">조회</button>
			</div>
			
			<p class="ptag-list">총 <span id="reportList-count">??</span> 건 </p>
			
			<div style="display: flex; justify-content: space-between; margin: 10px 0;">
				<div class="btn-group flex gap5 reportListBtnGroup">
					<button class="public-toggle-button active" id="ReportListSortByMemId" data-sort-by="memId">신고자명</button>
					<button class="public-toggle-button" id="ReportListSortByTargetName" data-sort-by="plTitle">신고대상명</button>
					<button class="public-toggle-button" id="ReportListSortByRpCreatedAt" data-sort-by="plCreatedAt">신고일시</button>
					<select class="public-toggle-select" name="order">
						<option value="desc">내림차순</option>
						<option value="asc">오름차순</option>
					</select>
				</div>
				
				<div>
					<select class="public-toggle-select" name="irStatus">
						<option value="">상태</option>
						<option value="">등록</option>
						<option value="">신청</option>
						<option value="">삭제</option>
						<option value="">반려</option>
					</select>
				</div>
			</div>

			<table id="penaltyTable">
				<thead>
					<tr>
						<th>리뷰번호</th>
						<th>지원분야</th>
						<th>작성일</th>
						<th>구분</th>
						<th>지원처</th>
						<th>작성자</th>
						<th>상태</th>
					</tr>
				</thead>
				<tbody id="reportList">
					<tr>
						<td>42</td>
						<td>이지은</td>
						<td>개똥사</td>
						<td>접수</td>
						<td>2025. 08. 22</td>
						<td>2025. 08. 22</td>
					</tr>
				</tbody>
			</table>
			<div class="card-footer clearfix">
				<div class="panel-footer pagination">
					<a href="#" data-page="0" class="page-link disabled">← Previous</a>
					<a href="#" data-page="1" class="page-link active">1</a>
					<a href="#" data-page="2" class="page-link ">2</a>
					<a href="#" data-page="3" class="page-link ">3</a>
					<a href="#" data-page="4" class="page-link ">4</a>
					<a href="#" data-page="5" class="page-link ">5</a>
					<a href="#" data-page="6" class="page-link disabled">Next →</a>
				</div>
			</div>
		</div>
		<div class="template-panel scdMng-2-2">
			<div class="middleTitle">신고 상세 정보</div>
			<div id="penalty-detail-box" class="penalty-detail-view">
				<div class="detail-item-group">
					<div class="detail-item">
						<span class="detail-label">신고 ID</span>
						<input type="text" id="report-detail-mpId" class="detail-input" readonly="">
					</div>
					<div class="detail-item">
						<span class="detail-label">신고 대상 ID</span>
						<input type="text" id="report-detail-targetId" class="detail-input" readonly="">
					</div>
				</div>
				<div class="detail-item-group">
					<div class="detail-item">
						<span class="detail-label">신고 타입</span>
						<input type="text" id="report-detail-mpType" class="detail-input" readonly="">
					</div>
					<div class="detail-item">
						<span class="detail-label">신고 대상명</span>
						<input type="text" id="report-detail-targetName" class="detail-input" readonly="">
					</div>
				</div>
				<div class="detail-item-group">
					<div class="detail-item">
						<span class="detail-label">신고자 ID</span>
						<input type="text" id="report-detail-memId" class="detail-input" readonly="">
					</div>
					<div class="detail-item">
						<span class="detail-label">신고 상태</span>
						<select id="report-detail-status" class="detail-input-select">
							<option value="S03001">접수</option>
							<option value="S03002">반려</option>
							<option value="S03003" title="승인은 신규 제제 등록으로 처리바랍니다" disabled="">승인</option>
						</select>
					</div>
				</div>
				<div class="detail-item-group">
					<div class="detail-item">
						<span class="detail-label">신고자명</span>
						<input type="text" id="report-detail-memName" class="detail-input" readonly="">
					</div>
					<div class="detail-item">
						<span class="detail-label">신고 일시</span>
						<input type="text" id="report-detail-warnDate" class="detail-input" readonly="">
					</div>
				</div>

				<div class="detail-reason-item">
					<span class="detail-label">신고 사유</span>
					<textarea id="report-detail-reason" class="detail-textarea" readonly=""></textarea>
				</div>

				<div class="detail-item-file">
					<span class="detail-label">증빙 자료</span>
					<a href="#" id="report-detail-file" class="detail-file-link">-</a>
				</div>

			</div>
			<button class="btn-save reportModify" id="reportModify">수정</button>
		</div>
	</div>
</body>

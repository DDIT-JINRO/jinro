<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/admin/umg/sanctionsDescription.css">
<script src="/js/include/admin/umg/sanctionsDescription.js"></script>

<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">제재
	내역</h2>
<body>
	<div class="scdMng-1">
		<div class="template-panel scdMng-1-1">
			<div class="middleTitle">정지 회원 비율</div>
			<canvas id="suspendedRatioChart" height="250"></canvas>
		</div>
		<div class="template-panel scdMng-1-1">
			<div class="middleTitle">제재 유형 분포</div>
			<canvas id="penaltyTypeChart" height="250"></canvas>
		</div>
		<div class="template-panel scdMng-1-1">
			<div class="middleTitle">월별 제재 건수</div>
			<canvas id="monthlyPenaltyChart" height="250"></canvas>
		</div>
	</div>
	<div class="scdMng-2">
		<div class="template-panel scdMng-2-1">
			<div class="middleTitle">제재 이력 리스트</div>
			<div class="filter-box">
				<select name="search_type">
					<option value="memId">회원ID</option>
					<option value="reason">제재사유</option>
				</select> <input type="text" name="keyword" placeholder="검색어를 입력하세요" />
				<button type="button" class="btn-save">조회</button>
				<button type="button" id="openNewPenaltyModalBtn" class="btn-save"
					style="margin-left: auto; background-color: #dc3545;">신규
					제재 등록</button>
			</div>
			<table id="penaltyTable">
				<thead>
					<tr>
						<th>이력ID</th>
						<th>회원ID</th>
						<th>제재유형</th>
						<th>제재사유</th>
						<th>제재일시</th>
					</tr>
				</thead>
				<tbody id="penaltyList">
					<!-- 데이터가 여기에 채워집니다. -->
				</tbody>
			</table>
			<!-- 페이지네이션 컨트롤이 필요하다면 여기에 추가 -->
		</div>
		<div class="template-panel scdMng-2-2">
			<div class="middleTitle">제재 상세 정보</div>
			<div id="penalty-detail-box" class="penalty-detail-view">
				<div class="detail-item">
					<span class="detail-label">이력 ID:</span>
					<span id="detail-mpId">-</span>
				</div>
				<div class="detail-item">
					<span class="detail-label">회원 ID:</span>
					<span id="detail-memId">-</span>
				</div>
				<div class="detail-item">
					<span class="detail-label">제재 유형:</span>
					<span id="detail-mpType">-</span>
				</div>
				<div class="detail-item">
					<span class="detail-label">제재 사유:</span>
					<span id="detail-reason">-</span>
				</div>
				<div class="detail-item">
					<span class="detail-label">제재 일시:</span>
					<span id="detail-warnDate">-</span>
				</div>
				<div class="detail-item">
					<span class="detail-label">정지 시작일:</span>
					<span id="detail-startDate">-</span>
				</div>
				<div class="detail-item">
					<span class="detail-label">정지 종료일:</span>
					<span id="detail-endDate">-</span>
				</div>
			</div>
		</div>
	</div>

	<div id="penaltyModal" class="penalty-modal-overlay">
		<div class="penalty-modal-content">
			<h3 style="margin-top: 0;">신규 제재 등록</h3>
			<!-- 모달 내용은 이전 버전과 유사하게 구성 -->
			<div style="margin-bottom: 1rem;">
				<label>대상 회원 ID</label> <input type="text" id="modalMemId"
					style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
			</div>
			<div style="margin-bottom: 1rem;">
				<label>제재 유형</label>
				<div id="modalPenaltyType">
					<span class="penalty-type-label" data-type="WARN">경고</span>
					<span class="penalty-type-label" data-type="TSUSP">기간정지</span>
					<span class="penalty-type-label" data-type="PBAN">영구제한</span>
				</div>
			</div>
			<div id="suspensionFields"
				style="display: none; margin-bottom: 1rem;">
				<label>정지 기간</label> <input type="datetime-local"
					id="modalStartDate"> ~ <input type="datetime-local"
					id="modalEndDate">
			</div>
			<div style="margin-bottom: 1rem;">
				<label>제재 사유</label> <input type="text" id="modalReason"
					style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
			</div>
			<div class="modal-form-group">
				<label>증빙 자료</label> <input type="file" id="evidenceFile" multiple
					style="display: none;">
				<button type="button" class="file-attach-btn"
					onclick="document.getElementById('evidenceFile').click();">파일
					선택</button>
				<div id="file-list"></div>
			</div>
			<div
				style="margin-top: auto; padding-top: 1rem; border-top: 1px solid #eee; text-align: right;">
				<div>
					<button id="confirmBtn" class="btn-save">확인</button>
					<button id="cancelBtn" class="btn-save"
						style="background-color: #6c757d;">취소</button>
				</div>
			</div>
		</div>
</body>


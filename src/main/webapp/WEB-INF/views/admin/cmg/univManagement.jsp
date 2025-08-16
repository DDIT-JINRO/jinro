<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/admin/cmg/univManagement.css">
<!-- 스크립트 작성해주시면 됩니다 (유의점 : DOMContentLoaded x) -->
<script src="/js/include/admin/cmg/univManagement.js">
	
</script>
<!-- 제목입니다 -->
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">대학 관리</h2>
<body>
	<div class="admin-univMng-1" style="margin-bottom: 20px;">
		<div class="template-panel admin-univMng-1-1">
			<div class="middleTitle">대학 리스트</div>
			<div class="filter-box">
				<select name="status">
					<option value="1">전체</option>
					<option value="2">학교명</option>
					<option value="3">학교구분명</option>
				</select>
				<input type="text" name="keyword" placeholder="검색어를 입력하세요" />
				<button type="button" class="btn-save">조회</button>
			</div>
			<div class="listUniv">
				<div class="search-filter-bar">
					<p class="ptag-list" style="margin-bottom: 10px">
						총
						<span id="univList-count"></span>
						건
					</p>

				</div>
				<div class="listUnivBody">
					<table id="univTable">
						<thead>
							<tr>
								<th class="body-id">ID</th>
								<th class="body-univName">대학명</th>
								<th class="body-univGubun">대학구분</th>
								<th class="body-univType">대학타입</th>
								<th class="body-univRegion">지역</th>
							</tr>
						</thead>
						<tbody id="univList">
						</tbody>
					</table>
				</div>
				<div class="card-footer clearfix">
					<div class="panel-footer pagination univListPage"></div>
				</div>
			</div>
		</div>

		<div class="template-panel admin-univMng-1-1">
			<div class="middleTitle">대학 상세</div>
			<div class="detail-item">
				<span class="detail-label">대학 ID:</span>
				<input id="univ-detail-univId" placeholder="자동 입력" readonly="readonly" />
			</div>
			<div class="detail-item">
				<span class="detail-label">대학명:</span>
				<input id="univ-detail-univName" placeholder="대학명 입력" />
			</div>
			<div class="detail-item">
				<span class="detail-label">대학구분</span>
				<select id="univ-detail-univGubun">
					<option value="G21001">국립</option>
					<option value="G21002">공립</option>
					<option value="G21003">사립</option>
				</select>
			</div>
			<div class="detail-item">
				<span class="detail-label">대학 지역:</span>
				<input id="univ-detail-univRegion" placeholder="대학 지역:" />
			</div>
			<div class="detail-item">
				<span class="detail-label">대학 상세 주소</span>
				<input id="univ-detail-univAddr" placeholder="대학 상세 주소:" />
			</div>
			<div class="detail-item">
				<span class="detail-label">대학 타입</span>
				<select id="univ-detail-univType">
					<option value="G20001">대학(4년제)</option>
					<option value="G20002">전문대학</option>
				</select>
			</div>
			<div class="detail-item">
				<span class="detail-label">URL 주소:</span>
				<input id="univ-detail-univUrl" placeholder="URL 주소:" />
			</div>
			<div class="flex">
				<button id="univReset">초기화</button>
				<button id="univSave">저장</button>
				<button id="univDel">삭제</button>
				<button id="univDepInsert">학과 추가</button>
			</div>
		</div>
	</div>

	<div class="flex" style="gap: 20px; margin-bottom: 20px;">
		<div class="template-panel admin-univMng-2">
			<div class="middleTitle">해당 대학 학과정보</div>
			<table>
				<thead>
					<tr>
						<th>학과명</th>
						<th>평균 등록금</th>
						<th>평균 장학금</th>
						<th>입시 경쟁률</th>
						<th>평균 취업률</th>
						<th style="display: none;"></th>
					</tr>
				</thead>
				<tbody id="tgDepart">
				</tbody>
			</table>
		</div>
		<div class="template-panel admin-univMng-3">
			<div class="middleTitle">학과 정보</div>
			<input id="univ-dept-detail-uddId" style="display: none;" placeholder="자동 입력" readonly="readonly" />
			<div class="detail-item">
				<span class="detail-label">대학 ID:</span>
				<input id="univ-dept-detail-univId" placeholder="자동 입력" readonly="readonly" />
			</div>
			<div class="detail-item">
				<span class="detail-label">대학명:</span>
				<input id="univ-dept-detail-univName" placeholder="자동 입력" readonly="readonly" />
			</div>
			<div class="detail-item">
				<span class="detail-label">학과번호:</span>
				<input id="univ-dept-detail-udId" placeholder="자동 입력" />
			</div>
			<div class="detail-item">
				<span class="detail-label">학과명:</span>
				<input id="univ-dept-detail-udName" />
			</div>
			<div class="detail-item">
				<span class="detail-label">평균 등록금:</span>
				<input id="univ-dept-detail-udTuition" />
			</div>
			<div class="detail-item">
				<span class="detail-label">평균 장학금:</span>
				<input id="univ-dept-detail-udScholar" />
			</div>
			<div class="detail-item">
				<span class="detail-label">입시 경쟁률:</span>
				<input id="univ-dept-detail-udCompetition" />
			</div>
			<div class="detail-item">
				<span class="detail-label">평균 취업률:</span>
				<input id="univ-dept-detail-udEmpRate" />
			</div>
			<div class="flex">
				<button id="univ-udDel">학과 삭제</button>
				<button id="univ-udMod">수정</button>
			</div>
		</div>
	</div>
	<div class="modal-overlay show" id="univDept-modal-overlay" style="display: none;">
		<div class="modal-content">
			<button class="modal-close-btn" id="modal-close-btn" type="button">×</button>
			<h3>대학 학과 추가</h3>

			<div class="modal-form">
				<input type="text" id="showUvName" placeholder="대학명" readonly="readonly">
				<input type="text" id="showUvId" readonly="readonly" style="display: none;">
				<input type="text" id="insertUdName" placeholder="학과명">
				<input type="text" id="showUddId" placeholder="학과계열 ID">
				<input type="text" id="insertUdTuition" placeholder="평균 등록금">
				<input type="text" id="insertUdScholar" placeholder="평균 장학금">
				<input type="text" id="insertUdCompetition" placeholder="입시 경쟁률 예) 8.5:1">
				<input type="text" id="insertUdEmpRate" placeholder="평균 취업률 예) 80.0">
				<span class="modal-error-msg" id="modal-error-msg"></span>
				<button class="btn btn-primary" id="univDept-insert-btn" type="button" style="margin-top: 20px;">등록</button>
			</div>
		</div>
	</div>
</body>

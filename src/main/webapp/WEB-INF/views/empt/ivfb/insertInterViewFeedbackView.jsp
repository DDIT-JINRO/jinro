<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/empt/ivfb/insertInterViewFeedbackView.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">취업 정보</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/empt/ema/employmentAdvertisement.do">채용공고</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/empt/enp/enterprisePosting.do">기업정보</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/empt/ivfb/interViewFeedback.do">면접후기</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/empt/cte/careerTechnicalEducation.do">직업교육</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="public-wrapper-main">
			<div class="section-header">
				<h2>면접 경험 등록</h2>
				<p>작성해주신 면접 후기는 익명으로 등록됩니다.</p>
			</div>
			<div class="Insert-write">
				<div class="info-input-section">
					<div class="input-header">
						<h3 class="file-label">기본정보 입력</h3>
						<span class="required-info-text">※는 필수입력정보입니다</span>
					</div>

					<table class="info-input-table">
						<tbody>
							<tr>
								<th>
									<label for="companyName">
										기업명
										<span class="required">*</span>
									</label>
								</th>
								<td>
									<div class="input-group">
										<input type="text" id="companyName" data-cp-id="" placeholder="기업명을 입력하세요." readonly>
										<button type="button" id="company-search" class="btn-search">입사지원 기업 검색</button>
									</div>
								</td>
							</tr>
							<tr>
								<th>
									<label for="job-position">
										직무직업
										<span class="required">*</span>
									</label>
								</th>
								<td>
									<input type="text" id="job-position" placeholder="직무, 직업을 입력하세요.">
								</td>
							</tr>
							<tr>
								<th>
									면접 일자
									<span class="required">*</span>
								</th>
								<td>
									<div class="input-group">
										<input type="date">
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="editor-container">
					<div id="editor"></div>
				</div>
				<label for="fileInput" class="file-label">증빙자료 첨부</label>
				<p class="form-note">기업명이 포함된 증빙 사진 또는 캡쳐를 첨부해주세요(O)면접 안내 문자, 이메일, 명함 등 (X)회사 건물, 본인 사진 등</p>
				<div class="file-upload-container">
					<input type="file" id="fileInput" multiple>
					<ul id="fileList" class="file-list"></ul>
				</div>
				<div class="button-group">
					<button class="cancel-btn" id="backBtn">목록</button>
					<button class="submit-btn" id="submitBtn">등록</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 기업 검색 모달 -->
	<div class="modal-overlay" id="modal-overlay">
		<div class="modal-content company-search-modal">
			<button class="modal-close-btn" type="button">&times;</button>
			<h3>입사지원 기업 검색</h3>
			<p>입사에 지원하여 면접을 경험한 기업을 선택해주세요.</p>

			<!-- 검색 입력창 -->
			<div class="search-input-container">
				<input type="text" id="company-search-input" placeholder="기업명을 입력하세요" autocomplete="off">
				<button type="button" id="search-btn" class="btn btn-primary">검색</button>
			</div>

			<!-- 기업 목록 -->
			<div class="company-list-container">
				<ul id="company-list" class="company-list">
					<!-- 기업 목록이 동적으로 추가됩니다 -->
				</ul>
			</div>

			<!-- 페이징 -->
			<div class="pagination-container">
				<button type="button" id="prev-page" class="pagination-btn" disabled>이전</button>
				<span id="page-info">1 / 1</span>
				<button type="button" id="next-page" class="pagination-btn" disabled>다음</button>
			</div>

			<div class="modal-button-group">
				<button class="btn btn-secondary" id="modal-cancel-btn">취소</button>
				<button class="btn btn-primary" id="modal-confirm-btn" disabled>선택</button>
			</div>
		</div>
	</div>

</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script type="text/javascript" src="/js/empt/ivfb/insertInterViewFeedbackView.js"></script>
</html>
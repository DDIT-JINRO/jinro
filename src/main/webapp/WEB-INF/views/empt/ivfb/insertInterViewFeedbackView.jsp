<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/empt/ivfb/insertInterviewFeedbackView.css">
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
										<input type="text" id="company-name" data-cp-id="" placeholder="기업명을 입력하세요." readonly required="required">
										<button type="button" id="company-search" class="btn-search">입사지원 기업 검색</button>
									</div>
								</td>
							</tr>
							<tr>
								<th>
									<label for="interview-position">
										직무직업
									</label>
								</th>
								<td>
									<input type="text" id="interview-position" placeholder="직무, 직업을 입력하세요." maxlength="25">
								</td>
							</tr>
							<tr>
								<th>
									면접 일자
									<span class="required">*</span>
								</th>
								<td>
									<div class="input-group">
										<input type="date" id="interview-date" required="required">
									</div>
								</td>
							</tr>
							<tr>
							    <th>
							        기업 평가
							        <span class="required">*</span>
							    </th>
							    <td>
							        <div class="star-rating-container">
							            <div class="star-rating" id="company-rating" data-rating="0">
							                <span class="star" data-value="1">★</span>
							                <span class="star" data-value="2">★</span>
							                <span class="star" data-value="3">★</span>
							                <span class="star" data-value="4">★</span>
							                <span class="star" data-value="5">★</span>
							            </div>
							            <span class="rating-text" id="rating-text">평가해주세요</span>
							        </div>
							        <div class="rating-descriptions">
							            면접 과정, 분위기, 기업 대응 등을 종합적으로 평가해주세요
							        </div>
							    </td>
							</tr>
							<tr>
								<th>
									면접 후기
									<span class="required">*</span>
								</th>
							    <td>
							        <div class="input-group textarea-container">
							            <textarea id="interview-detail" 
							                      placeholder="서류 합격 후 어떤 전형과 면접을 경험하셨나요?&#13;&#10;(사실이 아닌 비방이나 개인적인 의견은 등록이 거절될 수 있습니다.)" 
							                      rows="5"
							                      maxlength="100"
							                      required="required"></textarea>
							        </div>
							    </td>
							</tr>
							<tr>
								<th>
									증빙자료 첨부
									<span class="required">*</span>
								</th>
								<td>
	                                <p class="file-notice">
	                                	기업명이 포함된 증빙 사진 또는 캡쳐를 첨부해주세요
	                                	<span class="ex">(O)면접 안내 문자, 이메일, 명함 등 (X)회사 건물, 본인 사진 등</span>
	                                </p>
	                                <div class="wrap_attached">
	                                    <div class="btn_attach">
	                                        <input type="file" name="file-input" id="file-input" accept="image/*" required="required">
	                                        <label for="file-input">첨부하기</label>
	                                    </div>
	                                    <p class="txt_filename" style="display: none;">선택 파일 : <b></b></p>
	                                </div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="button-group">
					<button class="cancel-btn" id="back-btn">목록</button>
					<button class="submit-btn" id="submit-btn">등록</button>
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
<script type="text/javascript" src="/js/empt/ivfb/insertInterviewFeedbackView.js"></script>
</html>
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
				<p>등록된 이력서의 최근 3년 이내의 인사 경력을 불러옵니다. 작성해주신 면접 후기는 익명으로 등록됩니다.</p>
			</div>
			<div class="Insert-write">
				<div class="info-input-section">
					<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
						<h3 class="file-label">기본정보 입력</h3>
						<span class="required-info-text">※는 필수입력정보입니다</span>
					</div>

					<table class="info-input-table">
						<colgroup>
							<col style="width: 180px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th>
									<label for="companyName">기업명<span class="required">*</span></label>
								</th>
								<td>
									<div class="input-group">
										<input type="text" id="companyName" placeholder="기업명을 입력하세요.">
										<button type="button" class="btn-search">입사지원 기업 검색</button>
									</div>
								</td>
							</tr>
							<tr>
								<th>
									<label for="job-position">직무직업<span class="required">*</span></label>
								</th>
								<td>
									<input type="text" id="job-position" placeholder="직무, 직업을 입력하세요.">
								</td>
							</tr>
							<tr>
								<th>
									면접 당시 경력<span class="required">*</span>
								</th>
								<td>
                                    <div class="input-group" style="gap: 0;"> <input type="radio" id="newcomer" name="experienceType" value="newcomer">
										<label for="newcomer">신입</label>
										<input type="radio" id="experienced" name="experienceType" value="experienced" checked>
										<label for="experienced">경력</label>
                                        
                                        <div style="margin-left: 10px; display: flex; gap: 10px;">
                                            <select name="jobLevel" class="form-select">
                                                <option value="staff" selected>사원</option>
                                                <option value="assistant_manager">대리</option>
                                                <option value="manager">과장</option>
                                                <option value="deputy_general_manager">차장</option>
                                            </select>
                                            <select name="experienceYears" class="form-select">
                                                <option value="" disabled selected>연차 선택</option>
                                                <option value="1">1년차</option>
                                                <option value="2">2년차</option>
                                                <option value="3">3년차</option>
                                                <option value="4">4년차</option>
                                                <option value="5">5년차 이상</option>
                                            </select>
                                        </div>
									</div>
								</td>
							</tr>
							<tr>
								<th>
									면접 일자<span class="required">*</span>
								</th>
								<td>
									<div class="input-group">
										<select name="interviewYear" class="form-select">
											<option>2025년</option>
											<option>2024년</option>
											<option>2023년</option>
											<option>2022년</option>
										</select>
										<select name="interviewMonth" class="form-select">
											<option>01월</option>
											<option>02월</option>
											<option>03월</option>
											<option>04월</option>
											<option>05월</option>
											<option>06월</option>
											<option>07월</option>
											<option>08월</option>
											<option>09월</option>
											<option>10월</option>
											<option>11월</option>
											<option>12월</option>
										</select>
										
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
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script type="text/javascript" src="/js/empt/ivfb/insertInterViewFeedbackView.js"></script>
</html>
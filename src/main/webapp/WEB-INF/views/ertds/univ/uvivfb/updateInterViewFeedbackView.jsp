<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/univ/uvivfb/updateInterviewFeedbackView.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진학 정보</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn">
			<a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab" href="/ertds/univ/uvsrch/selectUnivList.do">대학 검색</a>
			<a class="tab" href="/ertds/univ/dpsrch/selectDeptList.do">학과 정보</a>
			<a class="tab active" href="/ertds/univ/uvivfb/selectInterviewList.do">면접 후기</a>
		</div>
	</div>
</div>
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
					<input type="text" value="${interviewReview.irId}" hidden="hidden" id="ir-id">
					<table class="info-input-table">
						<tbody>
							<tr>
								<th>
									<label for="companyName">
										대학명
									</label>
								</th>
								<td>
									<span>${interviewReview.targetName}</span>
								</td>
							</tr>
							<tr>
								<th>
									<label for="interview-position">
										학과
									</label>
								</th>
								<td>
									<span>${interviewReview.irApplication}</span>
								</td>
							</tr>
							<tr>
								<th>
									면접 일자
								</th>
								<td>
									<span><fmt:formatDate value="${interviewReview.irInterviewAt}" pattern="yyyy.MM.dd"/></span>
								</td>
							</tr>
							<tr>
							    <th>
							        대학 평가
							        <span class="required">*</span>
							    </th>
							    <td>
									<div class="star-rating-container">
							            <div class="star-rating" id="university-rating" data-rating="${interviewReview.irRating}">
							                <span class="star" data-value="1">★</span>
							                <span class="star" data-value="2">★</span>
							                <span class="star" data-value="3">★</span>
							                <span class="star" data-value="4">★</span>
							                <span class="star" data-value="5">★</span>
							            </div>
							            <span class="rating-text" id="rating-text">평가해주세요</span>
							        </div>
							        <div class="rating-descriptions">
							            면접 과정, 분위기, 대학 대응 등을 종합적으로 평가해주세요
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
							                      maxlength="300"
							                      required="required"
							                      >${interviewReview.irContent}</textarea>
							        </div>
							    </td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="button-group">
					<button class="cancel-btn" id="back-btn">목록</button>
					<button class="submit-btn" id="submit-btn">수정</button>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script type="text/javascript" src="/js/ertds/univ/uvivfb/updateInterviewFeedbackView.js"></script>
</html>
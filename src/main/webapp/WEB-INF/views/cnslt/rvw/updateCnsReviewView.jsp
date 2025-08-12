<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cnslt/rvw/updateCnsReviewView.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">상담</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/cnslt/resve/crsv/reservation.do">상담 예약</a></div>
		<div class="channel-sub-section-itemIn"><a href="/cnslt/rvw/cnsReview.do">상담 후기</a></div>		
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="public-wrapper-main">
			<div class="section-header">
				<h2>상담 후기 수정</h2>
				<p>작성해주신 상담 후기는 닉네임으로 등록됩니다.</p>
			</div>
			<div class="Insert-write">
				<div class="info-input-section">
					<div class="input-header">
						<h3 class="file-label">상담정보 입력</h3>
						<span class="required-info-text">※는 필수입력정보입니다</span>
					</div>
					<input type="text" value="" hidden="hidden" id="cr-id">
					<table class="info-input-table">
						<tbody>
							<tr>
								<th>
									<label for="companyName">
										상담사
										<span class="required">*</span>
									</label>
								</th>
								<td>
									<div class="input-group">
										<span>${counselingReview.counselName}</span>
									</div>
								</td>
							</tr>
							<tr>
								<th>
									<label for="interview-position">
										상담분야
									</label>
								</th>
								<td>
									<span>${counselingReview.counselCategory}</span>
								</td>
							</tr>
							<tr>
								<th>
									<label for="interview-position">
										상담방법
									</label>
								</th>
								<td>
									<span>${counselingReview.counselMethod}</span>
								</td>
							</tr>
							<tr>
								<th>
									상담 일자
									<span class="required">*</span>
								</th>
								<td>
									<span><fmt:formatDate value="${counselingReview.counselReqDatetime}" pattern="yyyy.MM.dd HH시 mm분"/></span>
								</td>
							</tr>
							<tr>
							    <th>
							        상담 평가
							        <span class="required">*</span>
							    </th>
							    <td>
									<div class="star-rating-container">
							            <div class="star-rating" id="company-rating" data-rating="${counselingReview.crRate}">
							                <span class="star" data-value="1">★</span>
							                <span class="star" data-value="2">★</span>
							                <span class="star" data-value="3">★</span>
							                <span class="star" data-value="4">★</span>
							                <span class="star" data-value="5">★</span>
							            </div>
							            <span class="rating-text" id="rating-text">평가해주세요</span>
							        </div>
							        <div class="rating-descriptions">
							            상담 과정, 분위기, 상담사 대응 등을 종합적으로 평가해주세요
							        </div>
							    </td>
							</tr>
							<tr>
								<th>
									상담 후기
									<span class="required">*</span>
								</th>
							    <td>
							        <div class="input-group textarea-container">
							            <textarea id="interview-detail" 
							                      placeholder="상담 이후 어떤 경험을 하셧나요?&#13;&#10;(사실이 아닌 비방이나 개인적인 의견은 등록이 거절될 수 있습니다.)" 
							                      rows="5"
							                      maxlength="300"
							                      required="required">${counselingReview.crContent}
							            </textarea>
							        </div>
							    </td>
							</tr>
							<tr>
								<th>
									<span>공개여부</span>
								</th>
								<td>
									<input type="radio" value="Y" ${counselingReview.crPublic == 'Y' ? 'checked' : ''}>
									<label>Y</label>
									<input type="radio" value="N" ${counselingReview.crPublic == 'N' ? 'checked' : ''}>
									<label>N</label>
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
<script type="text/javascript" src="/js/cnslt/rvw/updateCnsReviewView.js"></script>
</html>
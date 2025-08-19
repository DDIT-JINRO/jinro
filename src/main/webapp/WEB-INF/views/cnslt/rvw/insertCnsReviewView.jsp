<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cnslt/rvw/insertCnsReviewView.css">
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
				<h2>상담 후기 등록</h2>
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
									<label for="counsel-name">
										상담사
										<span class="required">*</span>
									</label>
								</th>
								<td>
									<div class="input-group">
										<input type="text" id="counsel-name" data-cr-id="" placeholder="상담사명을 입력하세요." readonly required="required">
										<button type="button" id="counsel-search" class="btn-search">과거 상담내역 검색</button>
									</div>
								</td>
							</tr>
							<tr>
								<th>
									<label for="counsel-category">
										상담분야
									</label>
								</th>
								<td>
									<input type="text" id="counsel-category" placeholder="상담분야" maxlength="25" readonly="readonly">
								</td>
							</tr>
							<tr>
								<th>
									<label for="counsel-method">
										상담방법
									</label>
								</th>
								<td>
									<input type="text" id="counsel-method" placeholder="상담방법" maxlength="25" readonly="readonly">
								</td>
							</tr>
							<tr>
								<th>
									<label for="counsel-req-datetime">
										상담 일자
									</label>
								</th>
								<td>
									<div class="input-group">
										<input type="datetime-local" id="counsel-req-datetime" required="required" readonly="readonly">
									</div>
								</td>
							</tr>
							<tr>
							    <th>
							        상담 평가
							        <span class="required">*</span>
							    </th>
							    <td>
							        <div class="star-rating-container">
							            <div class="star-rating" id="cr-rate" data-rating="0">
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
									상담 후기
									<span class="required">*</span>
								</th>
							    <td>
							        <div class="input-group textarea-container">
							            <textarea id="cr-content" 
							                      placeholder="상담 이후 어떤 경험을 하셧나요?&#13;&#10;(사실이 아닌 비방이나 개인적인 의견은 등록이 거절될 수 있습니다.)" 
							                      rows="5"
							                      maxlength="300"
							                      required="required"></textarea>
							        </div>
							    </td>
							</tr>
							<tr>
								<th>
									<span>공개여부</span>
								</th>
								<td>
									<label>공개</label>
									<input type="radio" name="cr-public" value="Y" checked>
									<label>비공개</label>
									<input type="radio" name="cr-public" value="N">
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
	        <button id="autoCompleteBtn" type="button" class="btn-submit" 
			style="position: absolute; top: 15px; right: 15px;">자동완성</button>
		</div>
	</div>

	<!-- 기업 검색 모달 -->
	<div class="modal-overlay" id="modal-overlay">
		<div class="modal-content counsel-search-modal">
			<button class="modal-close-btn" type="button">&times;</button>
			<h3>내 상담 내역 검색</h3>
			<p>후기를 작성 할 상담 내역을 선택해주세요.</p>

			<!-- 검색 입력창 -->
			<div class="search-input-container">
				<input type="text" id="counsel-search-input" placeholder="상담사명을 입력하세요" autocomplete="off">
				<button type="button" id="search-btn" class="btn btn-primary">검색</button>
			</div>

			<!-- 기업 목록 -->
			<div class="counsel-list-container">
				<ul id="counsel-list" class="counsel-list">
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
<script type="text/javascript" src="/js/cnslt/rvw/insertCnsReviewView.js"></script>
</html>
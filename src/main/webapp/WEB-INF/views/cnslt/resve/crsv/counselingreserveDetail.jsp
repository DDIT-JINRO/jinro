<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cnslt/resve/crsv/counselingreserveDetail.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">상담</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/cnslt/resve/crsv/reservation.do">상담 예약</a></div>
		<div class="channel-sub-section-item"><a href="/cnslt/rvw/cnsReview.do">상담 후기</a></div>		
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab active" href="/cnslt/resve/crsv/reservation.do">상담 예약</a>
		    <a class="tab" href="/cnslt/resve/cnsh/counselingReserveHistory.do">상담 내역</a>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
			<div class="reserveHeader">
				<div class="dept-asterisk"></div>
				<h3>정보 입력</h3>
			</div>
			
	        <form id="detailForm" action="/cnslt/resve/reserve" method="post">
	            <input type="hidden" name="counsel" value="${counselingVO.counsel}">
	            <input type="hidden" name="memId" value="${counselingVO.memId}">
	            <input type="hidden" name="counselMethod" value="${counselingVO.counselMethod}">
	            <input type="hidden" name="counselCategory" value="${counselingVO.counselCategory}">
				<input type="hidden" name="counselReqDatetime" 
						value="<fmt:formatDate value='${counselingVO.counselReqDatetime}'
						 pattern='yyyy-MM-dd HH:mm' />">
				<input type="hidden" name="payId" id="payId" value="${payId}">

				<div class="input-section">
					<div class="input-group">
						<label>이름</label> <input type="text" name="memName"
							value="<c:out value='${memberVO.memName}' />" readonly>
					</div>
					<div class="input-group">
						<label>성별</label> <input type="text" name="memGen"
							value="<c:out value='${memberVO.memGen}' />" readonly>
					</div>
					<div class="input-group">
						<label>나이</label> <input type="text" name="memAge"
							value="<c:out value='${memberVO.memAge}' />세" readonly>
					</div>
					<div class="input-group">
						<label>전화번호</label> <input type="tel" name="memPhoneNumber"
							value="<c:out value='${memberVO.memPhoneNumber}' />" readonly>
					</div>
					<div class="input-group">
						<label>이메일</label> <input type="email" name="memEmail"
							value="<c:out value='${memberVO.memEmail}' />" readonly>
					</div>
				</div>

				<div class="inputDescription">
	                <label>신청 동기</label>
	                <textarea name="counselDescription" ></textarea>
                </div>
	            
	            <button type="submit" id="submitBtn">예약 확정</button>
	        </form>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script src=""></script>
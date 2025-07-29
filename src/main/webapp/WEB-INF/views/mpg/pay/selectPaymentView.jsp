<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/mpg/pay/payment.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">마이페이지</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item"><a href="/mpg/mif/inq/selectMyInquiryView.do">내 정보</a></div>
		<div class="channel-sub-section-item"><a href="/mpg/mat/bmk/selectBookMarkList.do">나의 활동</a></div>
		<div class="channel-sub-section-itemIn"><a href="/mpg/pay/selectPaymentView.do">결제 구독내역</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
  		<div class="public-wrapper-main">
  			이곳은 결제/구독 내역 페이지 입니다.
  			<%-- ================= 구독 등급 목록 ================= --%>
            <div class="subscription-section">
                <div class="section-header">
                    <h2>구독 등급 목록</h2>
                    <%-- 현재 구독 중일 때만 취소 버튼 표시 --%>
                    <c:if test="${currentSub != null && currentSub.subStatus == 'Y'}">
                        <button id="cancel-sub-btn" class="btn-cancel">구독 취소</button>
                    </c:if>
                </div>

                <div class="subscription-plans-container">
                    <c:forEach var="product" items="${subProducts}">
                        <div class="subscription-plan ${currentSub.subId == product.subId ? 'active' : ''}">
                            <h3>${product.subName}</h3>
                            <p class="price">₩ <fmt:formatNumber value="${product.subPrice}" pattern="#,###" />/month</p>
                            <ul class="benefits">
                                <%-- 구독 혜택을 '\n' 기준으로 나눠서 표시 (JSTL fn 사용 필요) --%>
                                <li>${product.subBenefit}</li>
                            </ul>
                            
                            <c:choose>
                                <c:when test="${currentSub != null && currentSub.subId == product.subId && currentSub.subStatus == 'Y'}">
                                    <button class="btn-main" disabled>구독중</button>
                                </c:when>
                                <c:when test="${currentSub != null && currentSub.subStatus == 'Y'}">
                                    <button class="change-sub-btn btn-secondary" data-sub-id="${product.subId}">변경</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="subscribe-btn btn-main" 
                                            data-sub-id="${product.subId}" 
                                            data-name="${product.subName}" 
                                            data-price="${product.subPrice}">가입</button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </div>
            </div>
            
            <%-- ================= 구독 결제 내역 ================= --%>
            <div class="payment-history-section">
                <h2>구독 결제 내역</h2>
                <table class="payment-history-table">
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>상품명</th>
                            <th>결제일</th>
                            <th>결제 금액</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="payment" items="${paymentHistory}" varStatus="status">
                            <tr>
                                <td>${payment.payId}</td>
                                <td>${payment.subName}</td>
                                <td><fmt:formatDate value="${payment.payDate}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatNumber value="${payment.payAmount}" pattern="#,###" /> 원</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty paymentHistory}">
                            <tr><td colspan="4">결제 내역이 없습니다.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
            
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>

<%-- ================= 스크립트 영역 ================= --%>

<%-- ✅ 1. 결제/취소 로직이 담긴 JS 파일 포함 --%>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="/js/mpg/pay/payment.js"></script>

<%-- ✅ 2. 마이페이지의 버튼과 JS 함수를 연결하는 코드 --%>
<script>
document.addEventListener('DOMContentLoaded', () => {
    // 컨트롤러가 전달한 사용자 정보를 JS 변수로 저장
    const userInfo = {
        email: "<c:out value='${loginUser.memEmail}' />",
        name: "<c:out value='${loginUser.memName}' />",
        tel: "<c:out value='${loginUser.memPhoneNumber}' />"
    };

    // '구독하기' 버튼 이벤트
    document.querySelectorAll('.subscribe-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const subInfo = {
                id: parseInt(btn.dataset.subId),
                name: btn.dataset.name,
                price: parseInt(btn.dataset.price)
            };
            requestSubscription(subInfo, userInfo); 
        });
    });

    // '구독 취소' 버튼 이벤트
    const cancelButton = document.getElementById('cancel-sub-btn');
    if (cancelButton) {
        cancelButton.addEventListener('click', () => {
            cancelSubscription(); 
        });
    }
    
    // '구독 변경' 버튼 이벤트
    document.querySelectorAll('.change-sub-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const nextSubId = btn.dataset.subId;
            alert(`다음 결제부터 구독 상품을 ID ${nextSubId}로 변경하는 기능을 구현해야 합니다.`);
            // TODO: '/pay/change-subscription' API 호출 로직 추가
        });
    });
});
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>포트원 결제 테스트</title>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script type="text/javascript">
document.addEventListener('DOMContentLoaded', ()=>{
	
	const IMP = window.IMP;
	IMP.init('imp55613015') //가맹점 식별코드
	
	const nextPayId = "<%= request.getAttribute("nextPayId") %>";
	const loginUser = {
		email: "<%= loginUser.getMemEmail() %>",
		name: "<%= loginUser.getMemName() %>",
		tel: "<%= loginUser.getMemPhoneNumber() %>"
	};
	
	function requestPay(){
		
		const customerUid = "test_customer_" + new Date().getTime(); 
		const merchantUid = "" + nextPayId;
		
		IMP.request_pay(
			{
				customer_uid: customerUid,
				channelKey: "channel-key-c07f6500-7039-49dd-8ea4-359da1f6ea77",
				//pg: "kcp_billing",
				pay_method: "card",
				merchant_uid: merchantUid,
				name: "BASIC",
				amount: 100,
				buyer_email:loginUser.email,
				buyer_name: loginUser.name,
				buyer_tel: loginUser.tel,
				digital: true,
			},
			function(rsp){
				//callback
				if(rsp.success ) {
					//결제 성공 시 , 서버로 검증요청
					fetch('/pay/verify',{
						method: 'POST',
						headers: { 'Content-Type': 'application/json' },
						body: JSON.stringify({
							impUid: rsp.imp_uid,	// 아임포트 결제 고유번호
							customerUid: rsp.customer_uid,	// 빌링키
							merchantUid: rsp.merchant_uid,	// 주문번호
							amount: rsp.paid_amount	// 클리이언트가 결제한 금액
						})
					})
					.then(response => response.json())
					.then(data => {
						if(data.status === 'success'){
							alert('구독 신청이 완료되었습니다!' + data.message);
						} else {
							alert('구독 신청이 실패하였습니다!' + data.message);
						}
					}) 
	            } else { // 실패시
	                var msg = '결제에 실패하였습니다.';
	                msg += '에러내용 : ' + rsp.error_msg;
	             	alert(msg);
	            }
			},
		);
	}
	
	//"결제하기" 버튼 클릭시 함수 호출
	const payButton = document.getElementById("payButton");
	if(payButton){
		payButton.addEventListener('click', requestPay);
	}
	
});
</script>
</head>
<body>
	<button id="payButton">결제하기</button>
</body>
</html>
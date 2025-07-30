/**
 * 
 */

/*전역변수로 IMP 초기화*/
IMP = window.IMP;
IMP.init('imp55613015') //가맹점 식별코드

/**
 * 구독 신청 결제창 열기
 * @param {object} subInfo - 구독 상품 정보 {id, name, price}
 * @param {object} userInfo - 로그인 사용자 정보 {email, name, tel}
 */

function requestSubscription(subInfo, userInfo) {
	const customerUid = "userInfo.id" + new Date().getTime();
	const merchantUid = "order_" + new Date().getTime();

	IMP.request_pay(
		{
			customer_uid: customerUid,
			channelKey: "channel-key-c07f6500-7039-49dd-8ea4-359da1f6ea77",
			pay_method: "card",
			merchant_uid: merchantUid,
			name: subInfo.name,
			amount: subInfo.price,
			buyer_email: userInfo.email,
			buyer_name: userInfo.name,
			buyer_tel: userInfo.tel,
		},
		function(rsp) {
			//callback
			if (rsp.success) {
				//결제 성공 시 , 서버로 검증요청
				fetch('/mpg/verify', {
					method: 'POST',
					headers: { 'Content-Type': 'application/json' },
					body: JSON.stringify({
						impUid: rsp.imp_uid,	// 아임포트 결제 고유번호
						customerUid: rsp.customer_uid,	// 빌링키
						merchantUid: rsp.merchant_uid,	// 주문번호
						amount: rsp.paid_amount,	// 클리이언트가 결제한 금액
						subId: subInfo.id // 어떤 상품을 구독했는지 전송
					})
				})
					.then(response => response.json())
					.then(data => {
						if (data.status === 'success') {
							alert('처리 신청이 완료되었습니다!' + data.message);
							location.reload();
						} else {
							alert('처리 신청이 실패하였습니다!' + data.message);
						}
					})
			} else { // 실패시
				alert('결제에 실패하였습니다. 에러: ' + rsp.error_msg);
			}
		},
	);
}
/**
 * 구독 취소 요청
 */
function cancelSubscription() {
	if (!confirm("정말로 구독을 취소하시겠습니까?")) {
		return;
	}
	// 백엔드의 /mpg/cancel-subscription 주소로 POST 요청을 보냄
	fetch('/mpg/cancel-subscription', { method: 'POST' }) // 취소는 기존 PayController 사용
		.then(response => {
			if (response.ok) {
				return response.text();
			}
			// 서버에서 4xx, 5xx 에러 응답 시
			return Promise.reject('구독 취소에 실패했습니다. 잠시 후 다시 시도해주세요.');
		})
		.then(message => {
			alert('구독 취소 신청이 완료되었습니다!' + message);
			location.reload(); // 성공 시 페이지 새로고침
		})
		.catch(error => alert(error));

}
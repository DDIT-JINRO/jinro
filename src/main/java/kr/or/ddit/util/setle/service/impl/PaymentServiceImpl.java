package kr.or.ddit.util.setle.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.util.setle.service.IamportApiClient;
import kr.or.ddit.util.setle.service.PaymentRequestDto;
import kr.or.ddit.util.setle.service.PaymentResponseDto;
import kr.or.ddit.util.setle.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private IamportApiClient iamportApiClient;

	@Override
	public PaymentResponseDto verifyAndProcessPayment(PaymentRequestDto requestDto) {

		try {
            // 1. 아임포트 서버로부터 실제 결제 정보 조회
			Map<String, Object> paymentData = iamportApiClient.getPaymentInfo(requestDto.getImpUid());
			
			if(paymentData ==null) {
				return new PaymentResponseDto("failure", "결제 정보 조회에 실패했습니다.", requestDto.getMerchantUid());
			}

            // 2. 실제 결제 금액과 우리 시스템이 알아야 할 금액이 일치하는지 검증
            // 지금은 테스트로 100원으로 가정합니다.
//			double amountToBePaid = 100.0;
//			double paidAmount = ((Number) paymentData.get("amount")).doubleValue();
//			
//			if(paidAmount != amountToBePaid) {
//                // 금액이 일치하지 않으면 실패 응답 반환
//                return new PaymentResponseDto("failure", "결제 금액이 일치하지 않습니다.", requestDto.getMerchantUid());
//			}

            // 3. 결제 상태가 '결제완료(paid)' 상태인지 검증
			if(!"paid".equals(paymentData.get("status"))) {
                // 금액이 일치하지 않으면 실패 응답 반환
				return new PaymentResponseDto("failure", "결제가 완료되지 않았습니다. 현재 상태: " + paymentData.get("status"), requestDto.getMerchantUid());
			}
			
			// 4. 모든 검증을 통과했으므로 성공 응답을 반환
			// requestDto에서 빌링키(customer_uid)를 꺼내옵니다.
			String billingKey = requestDto.getCustomerUid();
			String successMessage = "서버 검증 성공! 발급된 빌링키 : " + billingKey;
			
			System.out.println("imp_uid: " + requestDto.getImpUid());
			System.out.println("customer_uid: " + requestDto.getCustomerUid());
			System.out.println("merchant_uid: " + requestDto.getMerchantUid());
			System.out.println("결제 상태: " + paymentData.get("status"));
			System.out.println("결제 금액: " + paymentData.get("amount"));
			
			return new PaymentResponseDto("success", "서버 검증에 성공했습니다.", requestDto.getMerchantUid());
			
		} catch (Exception e) {
			// API 호출 중 예외 발생 시
            return new PaymentResponseDto("failure", "서버 처리 중 오류가 발생했습니다: " + e.getMessage(), requestDto.getMerchantUid());
		}
	}

	@Override
	public PaymentResponseDto handleWebhook(Map<String, Object> webhookData) {
		// TODO Auto-generated method stub
		return null;
	}
}

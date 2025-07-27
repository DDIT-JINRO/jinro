package kr.or.ddit.util.setle.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.setle.service.IamportApiClient;
import kr.or.ddit.util.setle.service.MemberSubscriptionVO;
import kr.or.ddit.util.setle.service.PaymentRequestDto;
import kr.or.ddit.util.setle.service.PaymentResponseDto;
import kr.or.ddit.util.setle.service.PaymentService;
import kr.or.ddit.util.setle.service.PaymentVO;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private IamportApiClient iamportApiClient;
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	@Autowired
	private MemberSubscriptionMapper memberSubscriptionMapper;
	
	@Autowired
	private PayMemberMapper payMemberMapper;

	//merchant_uid를 생성하기 위해 pay_id의 다음 시퀀스 값을 조회
	@Override
	public int selectNextPayId() {
		return paymentMapper.selectNextPayId();
	}

	// 이메일을 기반으로 회원정보 조회
	@Override
	public MemberVO selectMemberByEmail(String email) {
		return payMemberMapper.selectMemberByEmail(email);
	}

	@Override
	@Transactional
	public PaymentResponseDto verifyAndProcessPayment(PaymentRequestDto requestDto, String loginId) {

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
			
			// 💡 1. 구독 정보 먼저 생성 (회원 ID, 상품 ID는 예시로 1)
			MemberVO loginUser = payMemberMapper.selectMemberByEmail(loginId);
			
			MemberSubscriptionVO sub = new MemberSubscriptionVO();
	        sub.setMemId(loginUser.getMemId()); // TODO: 실제 로그인 사용자로 대체
	        sub.setSubId(1); // TODO: BASIC 상품 ID
	        sub.setIamportCustomerUid(requestDto.getCustomerUid());
	        memberSubscriptionMapper.insertMemberSubscription(sub);

	        // 💡 2. 결제 정보 생성
	        PaymentVO payment = new PaymentVO();
	        payment.setImpUid(requestDto.getImpUid());
	        payment.setPayAmount(requestDto.getAmount());
	        payment.setMsId(sub.getMsId());
	        paymentMapper.insertPayment(payment);
			
			
			return new PaymentResponseDto("success", successMessage, requestDto.getMerchantUid());
			
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

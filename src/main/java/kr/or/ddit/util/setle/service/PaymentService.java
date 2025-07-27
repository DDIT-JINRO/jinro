package kr.or.ddit.util.setle.service;

import java.util.Map;

import kr.or.ddit.main.service.MemberVO;

public interface PaymentService {

	/**
	 * merchant_uid를 생성하기 위해 pay_id의 다음 시퀀스 값을 조회
	 */
	public int selectNextPayId();

	/**
	 * 이메일로 회원조회
	 */
	public MemberVO selectMemberByEmail(String email);

	/**
	 * 클라이언트로부터 전달받은 결제 정보를 검증하고 DB에 저장하는 등 후처리합니다.
	 * 
	 * @param requestDto 클라이언트로부터 받은 결제 요청 데이터
	 * @return 결제 처리 결과 응답 DTO
	 */
	public PaymentResponseDto verifyAndProcessPayment(PaymentRequestDto requestDto, String loginId);

	/**
	 * 아임포트 웹훅 알림을 받아 결제 상태를 업데이트하는 등 처리합니다.
	 * 
	 * @param webhookData 아임포트 웹훅으로부터 받은 데이터
	 * @return 웹훅 처리 결과 응답 DTO
	 */
	public PaymentResponseDto handleWebhook(Map<String, Object> webhookData);

	// 필요하다면 결제 취소나 환불 관련 메서드를 추가할 수 있습니다.
	// boolean cancelPayment(String impUid);
}

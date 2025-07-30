package kr.or.ddit.util.setle.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.main.service.MemberVO;

public interface PaymentService {

	/**
	 * merchant_uid를 생성하기 위해 pay_id의 다음 시퀀스 값을 조회
	 */
	public int selectNextPayId();
	
	/**
	 * 회원id로 회원조회
	 */
	public MemberVO selectMemberById(int memId);

	/**
	 * 
	 * 클라이언트로부터 전달받은 결제 정보를 검증하고 DB에 저장하는 등 후처리합니다.
	 * @param requestDto 클라이언트로부터 받은 결제 요청 데이터
	 * @return 결제 처리 결과 응답 DTO
	 */
	public PaymentResponseDto verifyAndProcessPayment(PaymentRequestDto requestDto, String loginId);

	// 결제해야 할 구독 목록을 DB에서 조회
	public List<MemberSubscriptionVO> findSubscriptionsDueForToday();

	// 구독 정보 업데이트 (다음 결제일, 결제 횟수 등)
	public int updateAfterRecurringPayment(int msId);

	//스케줄러에서 사용할 결제 내역 저장 
	public int insertPayment(PaymentVO payment);

	//구독 취소
	public boolean cancelSubscription(int memId);
	
	// 마이페이지에서 사용할 사용자의 구독정보 조회
	public MemberSubscriptionVO selectByMemberId(int memid);

	//전체 결제 내역 조회 (구독 결제 내역 표시)
	public List<PaymentVO> selectPaymentHistory(int memId);
	
	//구독 변경
	public boolean changeSubscription(int int1, int subId);
	
	//예약정보 조회
	public MemberSubscriptionVO findReservedSubscription(int memId);
	
	//예약구독 취소
    boolean cancelSubscriptionChange(int memId);
    
    // 구독 월간 기능 횟수 초기화
    public void resetMonthlyUsageCounts(); 

}
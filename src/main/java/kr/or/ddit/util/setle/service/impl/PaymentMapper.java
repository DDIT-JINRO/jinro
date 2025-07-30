package kr.or.ddit.util.setle.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.util.setle.service.PaymentVO;

/**
 * PAYMENT 테이블에 대한 데이터베이스 작업을 정의하는 매퍼 인터페이스입니다.
 */
@Mapper // 스프링 부트에서 MyBatis 매퍼를 스캔하고 빈으로 등록할 수 있도록 돕습니다.
public interface PaymentMapper {

	// merchant_uid를 생성하기 위해 pay_id의 다음 시퀀스 값을 조회
	public int selectNextPayId();

	/**
	 * 새로운 결제 정보를 데이터베이스에 삽입합니다.
	 * 
	 * @param payment 삽입할 Payment VO/엔티티 객체
	 * @return 삽입된 행의 수
	 */
	public int insertPayment(PaymentVO paymentVO);

	/**
	 * impUid를 기반으로 결제 정보를 조회합니다.
	 * 
	 * @param impUid 아임포트 결제 고유 번호
	 * @return 조회된 Payment VO/엔티티 객체 또는 null
	 */
	public PaymentVO selectPaymentByImpUid(String impUid);

	/**
	 * merchantUid를 기반으로 결제 정보를 조회합니다.
	 * 
	 * @param merchantUid 상점 주문 번호
	 * @return 조회된 Payment VO/엔티티 객체 또는 null
	 */
	public PaymentVO selectPaymentByMerchantUid(String merchantUid);

	//전체 결제 내역 조회 (구독 결제 내역 표시)
	public List<PaymentVO> selectPaymentHistory(int memId);
	
	// 구독 월간 기능 횟수 초기화
	public int resetUsageCounts();

}

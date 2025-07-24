package kr.or.ddit.util.setle.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.util.setle.service.SubscriptionVO;

/**
 * SUBSCRIBE 테이블에 대한 데이터베이스 작업을 정의하는 매퍼 인터페이스입니다.
 * SubscriptionVO 객체를 사용하여 DB와 데이터를 주고받습니다.
 */
@Mapper
public interface SubscriptionMapper {
    /**
     * 새로운 구독 정보를 데이터베이스에 삽입합니다.
     * @param subscriptionVO 삽입할 SubscriptionVO 객체
     * @return 삽입된 행의 수
     */
	public int insertSubscription(SubscriptionVO subscriptionVO);


    /**
     * memId를 기반으로 구독 정보를 조회합니다. (회원 ID로 해당 회원의 구독 정보 조회)
     * @param memId 회원 ID
     * @return 조회된 SubscriptionVO 객체 또는 null
     */
	public SubscriptionVO selectSubscriptionByMemId(Long memId);

    /**
     * 구독 정보를 업데이트합니다.
     * (예: 구독 상품 변경, 빌링키 업데이트, 다음 결제일 업데이트 등)
     * @param subscriptionVO 업데이트할 SubscriptionVO 객체
     * @return 업데이트된 행의 수
     */
	public int updateSubscription(SubscriptionVO subscriptionVO);

    /**
     * 다음 결제일이 특정 날짜인 활성 구독 목록을 조회합니다.
     * 이 메서드는 주로 정기 결제 스케줄러가 결제 대상 목록을 가져올 때 사용됩니다.
     * @param nextPaymentDate 조회할 다음 결제일
     * @return 해당 날짜에 결제해야 할 활성 구독 목록
     */
	public List<SubscriptionVO> selectSubscriptionsByNextPaymentDate(LocalDate nextPaymentDate);
	

    /**
     * 구독 상태를 업데이트합니다. (예: 'ACTIVE', 'CANCELED', 'INACTIVE' 등 공통 코드 ID)
     * @param subId 업데이트할 구독의 subId
     * @param status 변경할 구독 상태 (공통 코드 ID)
     * @return 업데이트된 행의 수
     */
	public int updateSubscriptionStatus(@Param("subId") Long subId, @Param("status") String status);
	
	
}

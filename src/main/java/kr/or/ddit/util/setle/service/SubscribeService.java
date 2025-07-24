package kr.or.ddit.util.setle.service;

import java.util.List;

/**
 * 구독 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다. 구독의 시작/갱신, 다음 결제일 도래 구독 조회, 구독 상태 변경 등의 기능을
 * 제공합니다.
 */
public interface SubscribeService {
	/**
	 * 새로운 구독을 시작하거나 기존 구독을 갱신합니다. 이 메서드는 빌링키 발급(최초 결제)이 성공한 후 호출되어, 사용자
	 * 정보(customer_uid)를 업데이트하고 구독 정보를 저장/갱신합니다.
	 *
	 * @param customerUid 아임포트에 등록된 고객의 고유 식별자 (빌링키)
	 * @param userId      구독을 시작하는 사용자(회원)의 고유 ID
	 * @param productId   구독할 상품의 고유 ID (어떤 상품을 구독하는지 식별)
	 * @return 구독 시작 또는 갱신 성공 여부 (true: 성공, false: 실패)
	 */
	boolean startSubscription(String customerUid, Long memId, Long productId);

	/**
	 * 다음 결제일이 도래한 활성 구독 목록을 조회합니다. 이 메서드는 주로 정기 결제 스케줄러가 결제 대상 목록을 가져올 때 사용됩니다.
	 *
	 * @return 결제해야 할 SubscriptionVO 객체 목록
	 */
	List<SubscriptionVO> getSubscriptionsDueForPayment();

	/**
	 * 특정 구독의 상태를 업데이트합니다. 예를 들어, 결제 성공 후 'ACTIVE'로, 결제 실패 시 'INACTIVE'로 변경할 수 있습니다.
	 *
	 * @param subId  업데이트할 구독의 고유 ID
	 * @param status 변경할 구독 상태 (예: "ACTIVE", "CANCELED", "INACTIVE" 등 공통 코드 ID)
	 * @return 상태 업데이트 성공 여부 (true: 성공, false: 실패)
	 */
	public boolean updateSubscriptionStatus(Long subId, String status);

	// TODO: 필요하다면 구독 취소, 등급 변경 등 다른 비즈니스 메서드를 추가할 수 있습니다.
	// 예: boolean cancelSubscription(Long userId); // 구독 취소 기능
	// 예: boolean changeSubscriptionPlan(Long userId, Long newProductId); // 구독 등급
	// 변경 기능

}

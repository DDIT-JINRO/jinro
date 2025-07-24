package kr.or.ddit.util.setle.service;

import java.time.LocalDate;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SUBSCRIBE 테이블과 매핑되는 엔티티 클래스입니다. 사용자 구독 정보를 담고 있습니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionVO {
	private Long subId; // SUB_ID (구독 고유 ID, PK)
	private Long memId; // MEM_ID (회원 ID)
	private String subName; // SUB_NAME (구독 상품명)
	private String subRole; // SUB_ROLE (해당 역할)
	private String subBenefit; // SUB_BENEFIT (구독 혜택)
	private String subActiveYn; // SUB_ACTIVE_YN (구독 활성화 여부 Y/N)
	private Double subPrice; // SUB_PRICE (구독 가격)
	private Long fileGroupId; // FILE_GROUP_ID (파일 그룹 ID)
	private String iamportCustomerUid; // PORT_CUSTOMER_UID (아임포트 빌링키)
	private String subscribeStatus; // SUBSCRIBE_STATUS (구독 상태: ACTIVE, CANCELED, INACTIVE 등)
	private LocalDate nextPaymentDate; // NEXT_PAYMENT_DATE (다음 결제 예정일)

}

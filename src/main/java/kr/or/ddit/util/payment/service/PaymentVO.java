package kr.or.ddit.util.payment.service;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PAYMENT 테이블과 매핑되는 엔티티 클래스입니다. 결제 정보를 담고 있습니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVO {
	private Long payId; // PAY_ID (결제 고유 ID, PK)
	private Long memId; // MEM_ID (회원 ID)
	private Long subId; // SUB_ID (구독 상품 ID)
	private String impUid; // IMP_UID (아임포트 결제 고유 번호)
	private String merchantUid; // MERCHANT_UID (상점 주문 번호)
	private Double payAmount; // PAY_AMOUNT (결제 금액)
	private LocalDateTime payDate; // PAY_DATE (결제 일시)
	private String payStatus; // PAY_STATUS (결제 상태: COMPLETED, FAILED, CANCELED 등)
	private Integer payResumeCnt; // PAY_RESUME_CNT (결제 재개 횟수)
	private Integer payCoverCnt; // PAY_COVER_CNT (결제 커버 횟수)
	private Integer payConsultCnt; // PAY_CONSULT_CNT (결제 상담 횟수)
	private Integer payMockCnt; // PAY_MOCK_CNT (모의면접 횟수)


}

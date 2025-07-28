package kr.or.ddit.util.setle.web;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.or.ddit.util.setle.service.IamportApiClient;
import kr.or.ddit.util.setle.service.MemberSubscriptionVO;
import kr.or.ddit.util.setle.service.PaymentService;
import kr.or.ddit.util.setle.service.PaymentVO;
import kr.or.ddit.util.setle.service.impl.MemberSubscriptionMapper;
import kr.or.ddit.util.setle.service.impl.PaymentMapper;

@Component
public class SubscriptionScheduler {

    @Autowired
    private IamportApiClient iamportApiClient;
    @Autowired
    private PaymentService paymentService;

    // 매일 새벽 4시에 실행 (서버 부하가 적은 시간)
    @Scheduled(cron = "0 0 4 * * *") 
    //@Scheduled(cron = "0 */1 * * * *") //테스트를 위해 '매 1분마다' 실행
    public void scheduleDailyPayments() {
        System.out.println("----------- 정기결제 스케줄러 시작 -----------");
        
        // 1. 오늘 결제해야 할 구독 목록을 DB에서 조회
        List<MemberSubscriptionVO> dueSubscriptions = paymentService.findSubscriptionsDueForToday();
        
        for (MemberSubscriptionVO memberSubscriptionVO : dueSubscriptions) {
            try {
                // 2. 이번 결제를 위한 새로운 주문번호 생성
            	int nextPayId = paymentService.selectNextPayId();
                String newMerchantUid = String.valueOf(nextPayId);
                double amount = 100.0; // TODO: sub 정보에 맞는 실제 상품 가격 조회
                String productName = "월간 구독 자동결제";

                // 3. IamportApiClient의 payAgain 메서드 호출
                Map<String, Object> result = iamportApiClient.payAgain(
                    memberSubscriptionVO.getCustomerUid(), // DB에 저장된 빌링키
                    newMerchantUid,
                    amount,
                    productName
                );

                if (result != null && "paid".equals(result.get("status"))) {
                    // 4. 결제 성공 시 DB 업데이트
                    System.out.println("구독 ID " + memberSubscriptionVO.getMsId() + " 정기결제 성공.");
                    
                    // 새 결제 내역 저장
                    PaymentVO payment = new PaymentVO();
                    payment.setPayId(nextPayId); 
                    payment.setMsId(memberSubscriptionVO.getMsId());
                    payment.setImpUid((String) result.get("imp_uid"));
                    payment.setPayAmount(amount);
                    int insertResult = paymentService.insertPayment(payment);

                    if (insertResult > 0) {
                        System.out.println("새 결제 내역 저장 성공");
                    } else {
                        System.err.println("새 결제 내역 저장 실패: 0 rows affected"); 
                    }

                    // 구독 정보 업데이트 (다음 결제일, 결제 횟수 등)
                    int updateResult = (int) paymentService.updateAfterRecurringPayment(memberSubscriptionVO.getMsId());

                    if (updateResult > 0) {
                        System.out.println("구독 정보 갱신 완료: msId=" + memberSubscriptionVO.getMsId());
                    } else {
                        System.err.println("구독 정보 갱신 실패: msId=" + memberSubscriptionVO.getMsId());
                    }
                    
                } else {
                    // 5. 결제 실패 시
                    System.err.println("구독 ID " + memberSubscriptionVO.getMsId() + " 정기결제 실패.");
                    // 구독 상태를 '미납(UNPAID)' 등으로 변경하는 로직 추가
                }

            } catch (Exception e) {
                System.err.println("구독 ID " + memberSubscriptionVO.getMsId() + " 처리 중 예외 발생: " + e.getMessage());
            }
        }
        System.out.println("----------- 정기결제 스케줄러 종료 -----------");
    }
}
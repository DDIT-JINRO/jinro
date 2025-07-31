package kr.or.ddit.util.setle.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.or.ddit.mpg.pay.service.PaymentService;

@Component
public class SubscriptionScheduler {

	@Autowired
	private PaymentService paymentService;

	// 정기결제를 위한 스케줄러
	// 매일 새벽0시에 실행 (서버 부하가 적은 시간)
	@Scheduled(cron = "0 0 0 * * *") // 매일0시
	// @Scheduled(cron = "0 0/1 * * * *") //1분마다
	public void scheduleDailyPayments() {
		paymentService.processScheduledPayments();

	}

	// 구독기간이 지난 기능 초기화를 위한 스케줄러
	@Scheduled(cron = "0 0 1 * * *") // 매일 1시
	// @Scheduled(cron = "0 0/1 * * * *") //1분마다
	public void resetMonthlyCounts() {

		paymentService.resetMonthlyUsageCounts();

	}
}
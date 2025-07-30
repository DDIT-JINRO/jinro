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
package kr.or.ddit.admin.las.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.las.service.PaymentStatsService;

@Service
public class PaymentStatsServiceImpl implements PaymentStatsService {
	
	@Autowired
	private PaymentStatsMapper paymentStatsMapper;

	// 당일 기준 총 구독자 수
	@Override
	public int getTotalSubscriberCount() {
		
		return paymentStatsMapper.selectTotalSubscriberCount();
	}

    // 당일 새로운 구독자 수
	@Override
	public int getNewSubscriberCountToday() {

        return paymentStatsMapper.selectNewSubscriberCountToday();
	}

    // 구독 결제 매출 
	@Override
	public List<Map<String, Object>> getRevenueStats(Map<String, Object> params) {

        return paymentStatsMapper.selectRevenueStats(params);
	}

    // 구독자 수 
	@Override
	public List<Map<String, Object>> getSubscriberCountStats(Map<String, Object> params) {

        return paymentStatsMapper.selectSubscriberCountStats(params);
	}

    // 상품별 인기 통계 
	@Override
	public List<Map<String, Object>> getProductPopularityStats(Map<String, Object> params) {

        return paymentStatsMapper.selectProductPopularityStats(params);
	}

	// 일일 구독 결제 매출
	@Override
	public List<Map<String, Object>> getDailyRevenueForDashboard() {

		return paymentStatsMapper.selectDailyRevenueForDashboard();
	}
}

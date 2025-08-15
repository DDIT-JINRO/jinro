package kr.or.ddit.admin.las.service;

import java.util.List;
import java.util.Map;

public interface PaymentStatsService {
	
	// 당일 기준 총 구독자 수
    public int getTotalSubscriberCount();

    // 당일 새로운 구독자 수
    public int getNewSubscriberCountToday();

    // 구독 결제 매출 
    public List<Map<String, Object>> getRevenueStats(Map<String, Object> params);
    
    // 구독자 수 
    public List<Map<String, Object>> getSubscriberCountStats(Map<String, Object> params);

    // 상품별 인기 통계 
    public List<Map<String, Object>> getProductPopularityStats(Map<String, Object> params);
    
	// 일일 구독 결제 매출
    public List<Map<String, Object>> getDailyRevenueForDashboard();

    // AI 기능 이용 내역
    List<Map<String, Object>> getAiServiceUsageStats(Map<String, Object> params);
}

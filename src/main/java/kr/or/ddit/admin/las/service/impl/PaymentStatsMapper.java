package kr.or.ddit.admin.las.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentStatsMapper {

	// 당일 기준 총 구독자 수
	public int selectTotalSubscriberCount();

    // 당일 새로운 구독자 수
	public int selectNewSubscriberCountToday();

    // 구독 결제 매출 
	public List<Map<String, Object>> selectRevenueStats(Map<String, Object> params);

    // 구독자 수 
	public List<Map<String, Object>> selectSubscriberCountStats(Map<String, Object> params);

    // 상품별 인기 통계 
	public List<Map<String, Object>> selectProductPopularityStats(Map<String, Object> params);

	// 일일 구독 결제 매출
	public List<Map<String, Object>> selectDailyRevenueForDashboard();

    // AI 기능 이용 내역
	public List<Map<String, Object>> selectAiServiceUsageStats(Map<String, Object> params);

}

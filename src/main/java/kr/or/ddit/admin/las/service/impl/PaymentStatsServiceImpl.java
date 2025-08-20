package kr.or.ddit.admin.las.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.las.service.PaymentStatsService;

@Service
public class PaymentStatsServiceImpl implements PaymentStatsService {

    @Autowired
    private PaymentStatsMapper paymentStatsMapper;

    // 총 구독자 수와 신규 구독자 수 통합
    @Override
    public Map<String, Object> getSubscriberSummary() {
        Map<String, Object> result = new HashMap<>();
        result.put("totalSubscribers", paymentStatsMapper.selectTotalSubscriberCount());
        result.put("newSubscribersToday", paymentStatsMapper.selectNewSubscriberCountToday());
        return result;
    }

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

    // 상품별 인기 통계 (기간별, 성별, 나이별 필터링 지원)
    @Override
    public List<Map<String, Object>> getProductPopularityStats(Map<String, Object> params) {
        return paymentStatsMapper.selectProductPopularityStats(params);
    }

    // AI 기능 이용 내역 (기간별, 성별, 나이별 필터링 지원)
    @Override
    public List<Map<String, Object>> getAiServiceUsageStats(Map<String, Object> params) {
        return paymentStatsMapper.selectAiServiceUsageStats(params);
    }

    // 일일 구독 결제 매출 - 대시보드
    @Override
    public List<Map<String, Object>> getDailyRevenueForDashboard() {
        return paymentStatsMapper.selectDailyRevenueForDashboard();
    }

    // 회원 가입 수 대비하여 구독 비율 - 대시보드용
    @Override
    public List<Map<String, Object>> selectNewUserRevenueRate(Map<String, Object> params) {
        return paymentStatsMapper.selectNewUserRevenueRate(params);
    }

    // 총 구독 결제 대비하여 신규 구독 결제 비율 - 대시보드용
    @Override
    public List<Map<String, Object>> selectNewRevenueRateStats(Map<String, Object> params) {
        return paymentStatsMapper.selectNewRevenueRateStats(params);
    }

    // 대시보드 상단 숫자 통계 (평균매출 vs 예상매출)
    @Override
    public Map<String, Object> getRevenueSummaryForDashboard() {
        return paymentStatsMapper.getRevenueSummaryForDashboard();
    }

    // 대시보드 하단 그래프 통계 (전체사용자 vs 구독자)
    @Override
    public List<Map<String, Object>> getMonthlyUserStatsForDashboard() {
        return paymentStatsMapper.getMonthlyUserStatsForDashboard();
    }
}
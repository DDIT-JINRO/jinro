package kr.or.ddit.admin.las.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.las.service.PaymentStatsService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/admin/las/payment")
public class PaymentStatsController {
	
	@Autowired
	private PaymentStatsService paymentStatsService;

	// 당일 기준 총 구독자 수
    @GetMapping("/total-subscribers")
    public int totalSubscribers() {
        return paymentStatsService.getTotalSubscriberCount();
    }

    // 당일 새로운 구독자 수
    @GetMapping("/new-subscribers-today")
    public int newSubscribersToday() {
        return paymentStatsService.getNewSubscriberCountToday();
    }

    // 구독 결제 매출 
    @GetMapping("/revenue-stats")
	public List<Map<String, Object>> revenueStats(@RequestParam Map<String, Object> params) {
        return paymentStatsService.getRevenueStats(params);
    }

    // 구독자 수 
    @GetMapping("/subscriber-stats")
    public List<Map<String, Object>> subscriberStats(@RequestParam Map<String, Object> params) {
        return paymentStatsService.getSubscriberCountStats(params);
    }

    // 상품별 인기 통계 
    @GetMapping("/product-popularity")
    public List<Map<String, Object>> productPopularity(@RequestParam Map<String, Object> params) {
        return paymentStatsService.getProductPopularityStats(params);
    }
    
	// AI 기능 이용 내역
    @GetMapping("/ai-service-usage")
    public List<Map<String, Object>> aiServiceUsage(@RequestParam Map<String, Object> params) {
        return paymentStatsService.getAiServiceUsageStats(params);
    }

	// 일일 구독 결제 매출 - 대시보드용
    @GetMapping("/daily-revenue")
    public List<Map<String, Object>> dailyRevenueForDashboard() {
        return paymentStatsService.getDailyRevenueForDashboard();
    }
    
	// 회원 가입 수 대비하여 구독 비율 - 대시보드용
    @GetMapping("/newUser-revenueRate")
    public List<Map<String, Object>> newUserRevenueRate(){
    	return paymentStatsService.selectNewUserRevenueRate();
    }

	// 총 구독 결제 대비하여 신규 구독 결제 비율 - 대시보드용
    @GetMapping("/newRevenue-rateStats")
    public List<Map<String, Object>> newRevenueRateStats(){
    	return paymentStatsService.selectNewRevenueRateStats();
    }
}

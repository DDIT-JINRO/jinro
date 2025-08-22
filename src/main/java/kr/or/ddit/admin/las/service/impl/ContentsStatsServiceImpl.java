package kr.or.ddit.admin.las.service.impl;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.or.ddit.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.las.service.ContentStatsService;
import kr.or.ddit.comm.peer.teen.service.TeenCommService;

@Service
public class ContentsStatsServiceImpl implements ContentStatsService{

    private final SecurityConfig securityConfig;

	@Autowired
	private ContentStatsMapper contentStatsMapper;

	@Autowired
	private TeenCommService teenCommService;

    ContentsStatsServiceImpl(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }
    
    /**
     * 일일 콘텐츠 활동 통계 (증감률 포함)
     * @return 게시글 작성, 북마크, 채팅방 개설 수의 오늘/어제 비교 데이터
     */
    @Override
    public Map<String, Object> getDailySummary() {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 일일 게시글 작성 수 (어제 대비 증감률)
        int dailyPostsToday = contentStatsMapper.selectDailyPostCountToday();
        int dailyPostsYesterday = contentStatsMapper.selectDailyPostCountYesterday();
        
        Map<String, Object> postsGrowth = calculateGrowthRate(dailyPostsToday, dailyPostsYesterday);
        
        // 2. 일일 북마크 수 (어제 대비 증감률)
        int dailyBookmarksToday = contentStatsMapper.selectDailyBookmarkCountToday();
        int dailyBookmarksYesterday = contentStatsMapper.selectDailyBookmarkCountYesterday();
        
        Map<String, Object> bookmarksGrowth = calculateGrowthRate(dailyBookmarksToday, dailyBookmarksYesterday);
        
        // 3. 일일 채팅방 개설 수 (어제 대비 증감률)
        int dailyChatRoomsToday = contentStatsMapper.selectDailyChatRoomCountToday();
        int dailyChatRoomsYesterday = contentStatsMapper.selectDailyChatRoomCountYesterday();
        
        Map<String, Object> chatRoomsGrowth = calculateGrowthRate(dailyChatRoomsToday, dailyChatRoomsYesterday);
        
        // 결과 설정
        result.put("dailyPosts", dailyPostsToday);
        result.put("dailyPostsRate", postsGrowth.get("percentage"));
        result.put("dailyPostsStatus", postsGrowth.get("status"));
        
        result.put("dailyBookmarks", dailyBookmarksToday);
        result.put("dailyBookmarksRate", bookmarksGrowth.get("percentage"));
        result.put("dailyBookmarksStatus", bookmarksGrowth.get("status"));
        
        result.put("dailyChatRooms", dailyChatRoomsToday);
        result.put("dailyChatRoomsRate", chatRoomsGrowth.get("percentage"));
        result.put("dailyChatRoomsStatus", chatRoomsGrowth.get("status"));
        
        return result;
    }

    // 증감률 계산 메서드
    private Map<String, Object> calculateGrowthRate(int currentCount, int previousCount) {
        double percentageChange = 0.0;
        String status = "equal";

        if (previousCount > 0) {
            percentageChange = ((double) (currentCount - previousCount) / previousCount) * 100;

            if (percentageChange > 0) {
                status = "increase";
            } else if (percentageChange < 0) {
                status = "decrease";
            }
        } else if (currentCount > 0) {
            status = "new_entry";
            percentageChange = 100.0;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedPercentage = df.format(Math.abs(percentageChange));

        Map<String, Object> result = new HashMap<>();
        result.put("percentage", formattedPercentage);
        result.put("status", status);

        return result;
    }
    
    /**
     * 로드맵 진행단계별 분포 통계
     * @return 각 단계별 회원 수와 퍼센트 분포
     */
    @Override
    public Map<String, Object> getRoadmapStepDistribution() {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 로드맵 참여 회원의 단계별 분포
        List<Map<String, Object>> stepDistribution = contentStatsMapper.selectRoadmapStepDistribution();
        
        // 2. 로드맵 미참여 회원 통계
        Map<String, Object> nonParticipating = contentStatsMapper.selectNonParticipatingMembers();
        
        // 3. 월드컵 결과 Top 5 직업
        List<Map<String, Object>> worldcupTop5Jobs = contentStatsMapper.worldcupTopJobs(null);
        
        // 4. 결과 조합
        result.put("stepDistribution", stepDistribution);
        result.put("nonParticipatingCount", nonParticipating.get("nonParticipatingCount"));
        result.put("nonParticipatingPercentage", nonParticipating.get("nonParticipatingPercentage"));
        result.put("totalMembers", nonParticipating.get("totalMembers"));
        result.put("worldcupTopJobs", worldcupTop5Jobs);
        
        return result;
    }
    
    /**
     * 커뮤니티 활동 통계 (주간/월간 증감률 포함)
     * @param period 'week' 또는 'month'
     * @return 각 활동별 현재/이전 기간 데이터와 증감률
     */
    @Override
    public Map<String, Object> getCommunityActivityStats(String period) {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 전체 활동 통계 조회
        List<Map<String, Object>> activityStats = contentStatsMapper.selectCommunityActivityStats(null);
        
        // 2. 기간별 데이터 분류 및 증감률 계산
        Map<String, Map<String, Object>> processedStats = processCommunityActivityStats(activityStats, period);
        
        result.put("period", period);
        result.put("stats", processedStats);
        
        return result;
    }

    /**
     * 커뮤니티 활동 통계 데이터 처리 및 증감률 계산
     * @param rawStats 원본 통계 데이터
     * @param period 분석할 기간 ('week' 또는 'month')
     * @return 처리된 통계 데이터
     */
    private Map<String, Map<String, Object>> processCommunityActivityStats(List<Map<String, Object>> rawStats, String period) {
        Map<String, Map<String, Object>> result = new HashMap<>();
        
        // 활동 타입별로 그룹화
        Map<String, Map<String, Integer>> groupedStats = new HashMap<>();
        
        for (Map<String, Object> stat : rawStats) {
            String activityType = (String) stat.get("activityType");
            String periodType = (String) stat.get("periodType");
            Integer count = ((Number) stat.get("count")).intValue();
            
            groupedStats.computeIfAbsent(activityType, k -> new HashMap<>())
                       .put(periodType, count);
        }
        
        // 각 활동 타입별로 증감률 계산
        for (String activityType : groupedStats.keySet()) {
            Map<String, Integer> periodData = groupedStats.get(activityType);
            Map<String, Object> activityResult = new HashMap<>();
            
            if ("week".equals(period)) {
                int currentWeek = periodData.getOrDefault("current_week", 0);
                int previousWeek = periodData.getOrDefault("previous_week", 0);
                
                Map<String, Object> growthData = calculateGrowthRate(currentWeek, previousWeek);
                
                activityResult.put("currentPeriod", currentWeek);
                activityResult.put("previousPeriod", previousWeek);
                activityResult.put("growthRate", growthData.get("percentage"));
                activityResult.put("growthStatus", growthData.get("status"));
                activityResult.put("periodLabel", "이번 주 vs 지난 주");
                
            } else if ("month".equals(period)) {
                int currentMonth = periodData.getOrDefault("current_month", 0);
                int previousMonth = periodData.getOrDefault("previous_month", 0);
                
                Map<String, Object> growthData = calculateGrowthRate(currentMonth, previousMonth);
                
                activityResult.put("currentPeriod", currentMonth);
                activityResult.put("previousPeriod", previousMonth);
                activityResult.put("growthRate", growthData.get("percentage"));
                activityResult.put("growthStatus", growthData.get("status"));
                activityResult.put("periodLabel", "이번 달 vs 지난 달");
            }
            
            // 활동 타입별 한글명 설정
            String activityName = getActivityTypeName(activityType);
            activityResult.put("activityName", activityName);
            
            result.put(activityType, activityResult);
        }
        
        return result;
    }
    
    /**
     * 활동 타입별 한글명 반환
     * @param activityType 활동 타입 코드
     * @return 영문명
     */
    private String getActivityTypeName(String activityType) {
        switch (activityType) {
            case "POST": return "총 게시글 수";
            case "POST_LIKE": return "총 게시글 좋아요 수";
            case "REPLY": return "총 댓글 수";
            case "REPLY_LIKE": return "총 댓글 좋아요 수";
            default: return activityType;
        }
    }

	@Override
	public List<Map<String, Object>> bookmarkCountsStatistic(Map<String, Object> param) {
		return contentStatsMapper.bookmarkCountsStatistic(param);
	}

	@Override
	public List<Map<String, Object>> bookmarkTopN(Map<String, Object> param) {
		return contentStatsMapper.bookmarkTopN(param);
	}

	@Override
	public List<Map<String, Object>> communityPostDailyTrend(Map<String, Object> param) {
		return contentStatsMapper.communityPostDailyTrend(param);
	}

	@Override
	public List<Map<String, Object>> communityReactionDailyTrend(Map<String, Object> param) {
		return contentStatsMapper.communityReactionDailyTrend(param);
	}

	@Override
	public List<Map<String, Object>> communityTopActiveMembers(Map<String, Object> param) {
		return contentStatsMapper.communityTopActiveMembers(param);
	}

	@Override
	public List<Map<String, Object>> communityTopPostsByEngage(Map<String, Object> param) {
		return contentStatsMapper.communityTopPostsByEngage(param);
	}

	@Override
	public List<Map<String, Object>> worldcupTopJobs(Map<String, Object> param) {
		return contentStatsMapper.worldcupTopJobs(param);
	}

	@Override
	public List<Map<String, Object>> wrSummary(Map<String, Object> param) {
		return contentStatsMapper.wrSummary(param);
	}

	@Override
	public List<Map<String, Object>> wrDailyTrend(Map<String, Object> param) {
		return contentStatsMapper.wrDailyTrend(param);
	}

	@Override
	public List<Map<String, Object>> roadmapCreateCompleteSummary(Map<String, Object> param) {
		return contentStatsMapper.roadmapCreateCompleteSummary(param);
	}

	@Override
	public List<Map<String, Object>> roadmapCreateCompleteDaily(Map<String, Object> param) {
		return contentStatsMapper.roadmapCreateCompleteDaily(param);
	}

	@Override
	public List<Map<String, Object>> selectCommunityTop5PostsByMemBirth(String memId) {

		Map<String, Object> param = new HashMap<>();
		LocalDate to = LocalDate.now();
		LocalDate from = to.minusDays(7);

		param.put("from", from.toString());
		param.put("to", to.toString());
		if(memId == null || "anonymousUser".equals(memId)) {
			return contentStatsMapper.selectCommunityTop5PostsByMemBirth(param);
		}
		// 로그인이 되어있는경우 청년인지 청소년인지 확인해서 해당 커뮤니티 게시글로 제공
		boolean isTeen = teenCommService.isTeen(memId);
		if(isTeen) {
			param.put("ccId", "G09001");
		}else {
			param.put("ccId", "G09006");
		}
		return contentStatsMapper.selectCommunityTop5PostsByMemBirth(param);
	}

}

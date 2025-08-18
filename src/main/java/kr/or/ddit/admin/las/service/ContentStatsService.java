package kr.or.ddit.admin.las.service;

import java.util.List;
import java.util.Map;

public interface ContentStatsService {
	List<Map<String, Object>> bookmarkCountsStatistic(Map<String, Object> param);

	List<Map<String,Object>> bookmarkTopN(Map<String, Object> param);

	List<Map<String,Object>> communityPostDailyTrend(Map<String,Object> param);

	List<Map<String,Object>> communityReactionDailyTrend(Map<String,Object> param);

	List<Map<String,Object>> communityTopActiveMembers(Map<String,Object> param);

	List<Map<String,Object>> communityTopPostsByEngage(Map<String,Object> param);

	List<Map<String,Object>> worldcupTopJobs(Map<String,Object> param);

	List<Map<String,Object>> wrSummary(Map<String,Object> param);

	List<Map<String,Object>> wrDailyTrend(Map<String,Object> param);

	List<Map<String,Object>> roadmapCreateCompleteSummary(Map<String,Object> param);

	List<Map<String,Object>> roadmapCreateCompleteDaily(Map<String,Object> param);

	List<Map<String, Object>> selectCommunityTop5PostsByMemBirth(String memId);

}

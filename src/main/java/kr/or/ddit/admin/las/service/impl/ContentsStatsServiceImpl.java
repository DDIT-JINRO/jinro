package kr.or.ddit.admin.las.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.las.service.ContentStatsService;

@Service
public class ContentsStatsServiceImpl implements ContentStatsService{

	@Autowired
	private ContentStatsMapper contentStatsMapper;

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

}

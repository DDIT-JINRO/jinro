package kr.or.ddit.admin.las.service.impl;

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

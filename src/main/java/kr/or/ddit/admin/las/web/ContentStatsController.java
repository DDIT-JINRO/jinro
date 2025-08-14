package kr.or.ddit.admin.las.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.las.service.ContentStatsService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/admin/las/cont")
public class ContentStatsController {

	@Autowired
	private ContentStatsService contentStatsService;

	@GetMapping("/bookmark/category-stacked")
	public List<Map<String, Object>> bookmarkCategoryCounts(@RequestParam Map<String, Object> param){
		return contentStatsService.bookmarkCountsStatistic(param);
	}

	@GetMapping("/bookmark/top")
	public List<Map<String, Object>> bookmarkTopN(@RequestParam Map<String, Object> param){
		return contentStatsService.bookmarkTopN(param);
	}
	//============
	@GetMapping("/community/postDaily")
	public List<Map<String, Object>> communityPostDaily(Map<String, Object> param){
		return contentStatsService.communityPostDailyTrend(param);
	}

	@GetMapping("/community/reactionDaily")
	public List<Map<String, Object>> communityReactionDaily(Map<String, Object> param){
		return contentStatsService.communityReactionDailyTrend(param);
	}

	@GetMapping("/worldcup-roadmap/summary")
	public List<Map<String, Object>> communityTopMem(Map<String, Object> param){
		return contentStatsService.communityTopActiveMembers(param);
	}

	@GetMapping("/worldcup-roadmap/daily")
	public List<Map<String, Object>> communityTopPost(Map<String, Object> param){
		return contentStatsService.communityTopPostsByEngage(param);
	}

	@GetMapping("/community/top-members")
	public List<Map<String, Object>> worldcupTopJobs(Map<String, Object> param){
		return contentStatsService.worldcupTopJobs(param);
	}

	@GetMapping("/community/top-posts")
	public List<Map<String, Object>> wrSummary(Map<String, Object> param){
		return contentStatsService.wrSummary(param);
	}

	@GetMapping("/worldcup/top-jobs")
	public List<Map<String, Object>> wrDailyTrend(Map<String, Object> param){
		return contentStatsService.wrDailyTrend(param);
	}

	@GetMapping("/roadmap/create-complete/summary")
	public List<Map<String, Object>> roadmapCreateCompleteSummary(Map<String, Object> param){
		return contentStatsService.roadmapCreateCompleteSummary(param);
	}

	@GetMapping("/roadmap/create-complete/daily")
	public List<Map<String, Object>> roadmapCreateCompleteDaily(Map<String, Object> param){
		return contentStatsService.roadmapCreateCompleteDaily(param);
	}

}

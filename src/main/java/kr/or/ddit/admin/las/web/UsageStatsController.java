package kr.or.ddit.admin.las.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.las.service.UsageStatsService;
import kr.or.ddit.admin.las.service.UsageStatsVO;
import kr.or.ddit.admin.las.service.VisitVO;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

/*
 * 접속/이용 통계 컨트롤러
 * */
@RestController
@Slf4j
@RequestMapping("/admin/las")
public class UsageStatsController {

	@Autowired
	UsageStatsService usageStatsService;
	

	// 일별 사용자
	@GetMapping("/dailyUserInquiry.do")
	public List<UsageStatsVO> dailyUserInquiry() {
			
		return usageStatsService.dailyUserInquiry();
	}

	// 월별 사용자
	@GetMapping("/monthlyUserInquiry.do")
	public List<UsageStatsVO> monthlyUserInquiry() {

		return usageStatsService.monthlyUserInquiry();
	}
	
	// 실시간 사용자 조회
	@GetMapping("/liveUserList.do")
	public ArticlePage<MemberVO> liveUserList(@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(value="size",required=false,defaultValue="10") int size,
			@RequestParam(value="keyword",required=false) String keyword,
			//성별 구분
			@RequestParam(value="gen",required = false)String gen,
			//로그인 구분
			@RequestParam(value="loginType",required = false)String loginType){
		
		ArticlePage<MemberVO> memList = usageStatsService.liveUserList(currentPage, size, keyword, gen, loginType);
		
		return memList;
	}
	
	// 일별 페이지 별 방문자 수 
	@GetMapping("/pageVisitCount.do")
	public List<VisitVO> pageVisitCount(){
		return usageStatsService.pageVisitCount();
	}
	
	
}
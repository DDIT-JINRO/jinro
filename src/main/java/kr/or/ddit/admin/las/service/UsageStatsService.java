package kr.or.ddit.admin.las.service;

import java.util.List;

import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.ArticlePage;

public interface UsageStatsService {

	// 일별 사용자 수 구하기
	public List<UsageStatsVO> dailyUserInquiry();

	// 월별 사용자 수 구하기
	public List<UsageStatsVO> monthlyUserInquiry();

	// 현재 로그인한 사용자 정보 조회
	public ArticlePage<MemberVO> liveUserList(int currentPage, int size, String keyword, String gen, String loginType);

	// 일별 페이지 방문자 수 top10
	public List<VisitVO> pageVisitCount();
	
	// 해달월 페이지 방문자 수 top10
	public List<VisitVO> monthPageVisitCount();
	
	// 원하는 기간별 방문자 수
	public List<UsageStatsVO> customUserInquiry(String startDate, String endDate);

	// 원하는 기간별 페이지 방문자 수
	public List<VisitVO> getPageCalendar(String startDate, String endDate);

}

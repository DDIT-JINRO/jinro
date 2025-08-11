package kr.or.ddit.admin.las.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.las.service.UsageStatsService;
import kr.or.ddit.admin.las.service.UsageStatsVO;
import kr.or.ddit.admin.las.service.VisitVO;
import kr.or.ddit.csc.faq.service.FaqVO;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsageStatsServiceImpl implements UsageStatsService{
	
	@Autowired
	UsageStatsMapper usageStatsMapper;
	
	// 일별 사용자 구하기
	@Override
	public List<UsageStatsVO> dailyUserInquiry() {
		
		return this.usageStatsMapper.dailyUserInquiry();
	}

	// 월별 사용자 구하기
	@Override
	public List<UsageStatsVO> monthlyUserInquiry() {
		
		return  this.usageStatsMapper.monthlyUserInquiry();
	}

	@Override
	public ArticlePage<MemberVO> liveUserList(int currentPage, int size, String keyword, String gen, String loginType) {
		// 파라미터
		int startNo = (currentPage - 1) * size + 1;
		int endNo = currentPage * size;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("gen", gen);
		map.put("loginType", loginType);
		map.put("currentPage", currentPage);
		map.put("startNo", startNo);
		map.put("endNo", endNo);
		
		// 리스트 불러오기
		List<MemberVO> memList =  usageStatsMapper.liveUserList(map);
		
		// 건수
		int total = usageStatsMapper.liveUserListCount(map);
		// 페이지 네이션
		ArticlePage<MemberVO> articlePage = new ArticlePage<MemberVO>(total, currentPage, size, memList, keyword);
		
		return articlePage;
	}

	// 일별 페이지 방문자 수 top10
	@Override
	public List<VisitVO> pageVisitCount() {
		return usageStatsMapper.pageVisitCount();
	}

	


	
}

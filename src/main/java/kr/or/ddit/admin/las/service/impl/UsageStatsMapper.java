package kr.or.ddit.admin.las.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.admin.las.service.UsageStatsVO;
import kr.or.ddit.admin.las.service.VisitVO;
import kr.or.ddit.main.service.MemberVO;

@Mapper
public interface UsageStatsMapper {

	// 일별 사용자 구하기
	public List<UsageStatsVO> dailyUserInquiry();

	// 월별 사용자 구하기
	public List<UsageStatsVO> monthlyUserInquiry();

	// 로그인한 사용자 정보 조회
	public List<MemberVO> liveUserList(Map<String, Object> map);

	// 건수 조회
	public int liveUserListCount(Map<String, Object> map);

	// 일별 페이지 방문자 수 TOP10
	public List<VisitVO> pageVisitCount();

}

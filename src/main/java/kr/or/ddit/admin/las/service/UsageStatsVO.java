package kr.or.ddit.admin.las.service;

import java.util.Date;

import lombok.Data;

// 접속 이용 통계 VO
@Data
public class UsageStatsVO {
	
	// 일별 사용자 VO
	private String loginDate;
	private String loginMonth;
	private int userCount;
}

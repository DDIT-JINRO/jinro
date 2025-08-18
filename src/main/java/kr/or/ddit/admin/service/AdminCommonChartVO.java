package kr.or.ddit.admin.service;

import lombok.Data;

@Data
public class AdminCommonChartVO {
	
	// 당월 이용자 전체 수
	int thisMonthUserCount;
	// 저번달 이용자 전체 수
	int lastMonthUserCount;
	
	// 당월 기준 전체 인원수
	int thisMonthTotalCount;
	// 지난달 전체 인원수
	int lastMonthTotalCount;
	
}

package kr.or.ddit.ertds.univ.dpsrch.service;

import lombok.Data;

@Data
public class UnivDeptVO {
	private int uddId;
	private String uddLClass;   // 계열(대분류)
	private String uddMClass;   // 학과분류(중분류)
	private String uddSum;      // 학과 개요
	private String uddInterest; // 흥미, 적성
	private String uddProperty; // 학과 특성
	private String uddJobList;  // 관련 직업
	private String uddLiList;   // 관련 자격
}

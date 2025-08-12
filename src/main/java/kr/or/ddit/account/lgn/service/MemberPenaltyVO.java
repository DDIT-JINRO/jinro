package kr.or.ddit.account.lgn.service;

import java.util.Date;

import lombok.Data;

@Data
public class MemberPenaltyVO {
	
	private int mpId;
	private int reportId;
	private String mpWarnReason;
	private Date mpWarnDate;
	private int memId;
	private String mpType;
	private Date mpStartedAt;
	private Date mpCompleteAt;
	private Long fileGroupNo;
	
	// 신고대상자 이름 조회
	private String memName;
	
	private String keyword;	 
	private String status;

	private int currentPage;
	private int size;
	private int startNo;
	private int endNo;

	public int getStartNo() {
		return (this.currentPage - 1) * size;
	}

	public int getEndNo() {
		return this.currentPage * size;
	}
	
}

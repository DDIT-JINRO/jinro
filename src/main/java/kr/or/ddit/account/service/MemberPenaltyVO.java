package kr.or.ddit.account.service;

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
	private Date mpStartdeAt;
	private Date mpCompleteAt;
	private int fileGroupNo;
	
}

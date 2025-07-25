package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service;

import java.util.Date;

import lombok.Data;

@Data
public class InterviewDetailListVO {
	private int idlId;
	private int memId;
	private String idlTitle;
	private Date idlCreatedAt;
	private String idlDelYN;
	
	private int questionCount;
}

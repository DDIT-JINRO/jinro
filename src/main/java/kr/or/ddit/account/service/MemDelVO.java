package kr.or.ddit.account.service;

import java.util.Date;

import lombok.Data;

@Data
public class MemDelVO {
	
	private int mdId;
	private int memId;
	private String memEmail;
	private String mdReason;
	private Date mdReqAt;
	private String mdStatus;
	private Date mdDeletedAt;
	
}

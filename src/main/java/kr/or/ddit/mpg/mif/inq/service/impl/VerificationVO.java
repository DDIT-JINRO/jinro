package kr.or.ddit.mpg.mif.inq.service.impl;

import java.util.Date;

import lombok.Data;

@Data
public class VerificationVO {
	private int veriId;
	private int memId;
	private Date veriCreatedAt;
	private Long fileGroupId;
	private String veriStatus;
	private String veriReason;
	private String veriCategory;
}

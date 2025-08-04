package kr.or.ddit.mpg.mif.inq.service.impl;

import java.util.Date;

import lombok.Data;

@Data
public class StudentVerificationVO {
	private int svId;
	private int memId;
	private Date svCreatedAt;
	private Long fileGroupId;
	private String svStatus;
	private String svReason;
}

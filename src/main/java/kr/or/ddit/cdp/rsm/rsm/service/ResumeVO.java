package kr.or.ddit.cdp.rsm.rsm.service;

import java.util.Date;

import lombok.Data;
@Data
public class ResumeVO {
	private int resumeId;
	private int memId;
	private String resumeTitle;
	private String resumeIsTemp;
	private Date createdAt;
	private Date updatedAt;
	private int fileGroupNo;
}

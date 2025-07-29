package kr.or.ddit.worldcup.service;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class JobsVO {
	private String jobCode;
	private String jobName;
	private String jobLcl;
	private String jobMcl;
	private String jobSal;
	private int jobSatis;
	private String jobWay;
	private String jobRelatedMajor;
	private String jobRelatedCert;
	private String jobMainDuty;
	private Date jobCreatedAt;
	private int fileGroupNo;
	private int edubgMgraduUndr;
	private int edubgHgradu;
	private int edubgCgraduUndr;
	private int edubgUgradu;
	private int edubgGgradu;
	private int edubgDgradu;
	private int outlookIncrease;
	private int outlookSlightIncrease;
	private int outlookStable;
	private int outlookSlightDecrease;
	private int outlookDecrease;
	
	private List<String> jobsRel;
}

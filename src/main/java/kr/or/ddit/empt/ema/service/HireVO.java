package kr.or.ddit.empt.ema.service;

import java.util.Date;

import lombok.Data;

@Data
public class HireVO {
	private String hireClassCode;
	private int hireId;
	private int cpId;
	private String hireTitle;
	private String hireDescription;
	private String hireType;
	private Date hireStartDate;
	private Date hireEndDate;
	private String hireUrl;
}

package kr.or.ddit.mpg.mat.csh.service;

import java.util.Date;

import lombok.Data;

@Data
public class CounselingVO {
	private int counselId;
	private int memId;
	private int counsel;
	private String counselCategory;
	private String counselMethod;
	private String counselTitle;
	private String counselDescription;
	private String counselStatus;
	private Date counselReqDate;
	private Date counselReqTime;
	private String counselUrl;
	private String counselReviewd;
	private Date counselCreatedAt;
	private Date counselUpdatedAt;
	
	private String memName;
	private String counselName;
	
	// 필터조건
	private String keyword;
	private String status;

	// 페이징
	private int currentPage = 1;
	private int size = 5;
	
	public int getStartRow() {
		return (this.currentPage - 1) * this.size + 1;
	}
	
	public int getEndRow() {
		return this.currentPage * this.size;
	}
}

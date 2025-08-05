package kr.or.ddit.cns.service;

import java.util.Date;

import lombok.Data;

@Data
public class CounselingVO {
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
	private int counselId;

	// 검색 키워드
	private String keyword;
	private String stauts;

	// 필터링 연도
	private String year;

	// 페이징
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

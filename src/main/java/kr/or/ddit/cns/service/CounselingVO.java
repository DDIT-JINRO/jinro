package kr.or.ddit.cns.service;

import java.util.Date;
import java.util.List;

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
	private String counselUrlCou;
	private String counselReviewd;
	private Date counselCreatedAt;
	private Date counselUpdatedAt;
	private int counselId;
	private String counselUrlUser;

	private CounselingLogVO counselingLog;

	private String memName;
	private String memEmail;
	private String memPhoneNumber;
	private String memBirth;
	private String memGen;

	// 검색 키워드
	private String keyword;
	private String status;

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

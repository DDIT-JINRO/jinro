package kr.or.ddit.csc.not.service;

import java.util.Date;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
//관리자 공지사항 VO
public class NoticeVO {
	private int fileGroupNo;
	private int noticeCnt;
	private String noticeContent;
	private Date noticeCreatedAt;
	private String noticeDelYn;
	private int noticeId;
	private String noticeTitle;
	private Date noticeUpdatedAt;

	// 검색 키워드
	private String keyword;
	
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

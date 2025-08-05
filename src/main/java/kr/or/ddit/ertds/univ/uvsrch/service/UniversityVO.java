package kr.or.ddit.ertds.univ.uvsrch.service;

import lombok.Data;

@Data
public class UniversityVO {
	private int univId;
	private String univName;
	private String univCampus;
	private String univType;
	private String univGubun;
	private String univUrl;
	private String univRegion;
	private String univAddr;
	
	private String keyword;
	// 목록 조회시 페이징 처리를 위한 필드 추가
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

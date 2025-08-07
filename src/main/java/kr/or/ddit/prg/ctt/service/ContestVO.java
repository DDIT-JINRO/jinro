package kr.or.ddit.prg.ctt.service;

import java.util.Date;

import lombok.Data;

@Data
public class ContestVO {

	private String contestId; // 공모전번호
	private String contestTitle; // 제목
	private String activityGubunCode; // 활동구분코드 (G32001 등)
	private String contestDescription; // 설명
	private String contestType; // 공모전분류 (G35001 등)
	private String contestTarget; // 모집 대상 (G34001 등)
	private Date contestStartDate; // 시작일
	private Date contestEndDate; // 종료일
	private Date contestCreatedAt; // 게시일
	private int contestRecruitCount; // 조회수
	private String contestUrl; // 원본 URL
	private String fileGroupId; // 이미지 파일 그룹 ID

	// 필터조건
	// 검색
	private String keyword;

	// 페이징
	private int currentPage;
	private int size;
	private int startRow;
	private int endRow;

	public int getStartNo() {
		return (this.currentPage - 1) * size;
	}

	public int getEndNo() {
		return this.currentPage * size;
	}
}

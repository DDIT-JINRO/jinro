package kr.or.ddit.prg.ctt.service;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ContestVO {

	private String contestId;               // 공모전번호
	private String contestTitle;            // 제목
	private String contestGubunCode;        // 공모전구분코드
	private String contestGubunName;        // 공모전구분명
	private String contestDescription;      // 설명
	private String contestType;             // 공모전분류 코드 (G35001 등)
	private String contestTypeName;         // 공모전분류명 (건축, 게임/소프트웨어 등)
	private String contestTarget;           // 모집 대상 코드 (G34001 등)
	private String contestTargetName;       // 모집 대상명 (전체, 청소년, 청년 등)
	private Date contestStartDate;          // 시작일
	private Date contestEndDate;            // 종료일
	private Date contestCreatedAt;          // 게시일
	private int contestRecruitCount;        // 조회수
	private String contestUrl;              // 원본 URL
	private Long fileGroupId;             // 이미지 파일 그룹 ID

	// 필터조건
	private String keyword;

	// HighSchoolVO의 필터 방식을 참고하여 List<String> 타입의 필터 변수 추가
    private List<String> contestGubunFilter;
    private List<String> contestTargetFilter;
    private List<String> contestTypeFilter;

	// 페이징
	private int currentPage;
	private int size;
	private int startRow;
	private int endRow;
}
package kr.or.ddit.empt.ema.service;

import java.util.Date;
import java.util.List;

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
	
	//새로 받는 값
	private String cpName; //회사이름
	private String cpRegion; //지역이름
	private String hireTypename;//고용형태 값
	private String hireClassCodeName; //직업대분휴
	
	
	
	//필터링
    private String keyword; // 검색어 (제목 또는 기업명)
    private List<String> hireTypeNames; // 채용 유형 (체크박스)
    private List<String> hireClassCodeNames; // 채용 구분 (체크박스)
    private List<String> regions; // 지역 (체크박스)
    
    
    //페이징
	private int currentPage;
	private int size=5;
	private int startNo;
	private int endNo;
	
	public int getStartNo() {
		int validatedCurrentPage = (this.currentPage < 1) ? 1 : this.currentPage;
		return (this.currentPage - 1) * size;
	}
	
	public int getEndNo() {
		int validatedCurrentPage = (this.currentPage < 1) ? 1 : this.currentPage;
		return this.currentPage * size;
	}
}

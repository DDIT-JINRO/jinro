package kr.or.ddit.cdp.sint.service;

import lombok.Data;

@Data
public class SelfIntroQVO {
	private int siqId;
	private String siqJob;
	private String siqContent;
	
    // 검색 조건
    private String keyword;
    private String siqJobFilter;

    // 페이징
    private int startRow;
    private int endRow;
}

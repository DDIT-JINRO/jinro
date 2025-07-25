package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service;

import lombok.Data;

@Data
public class InterviewQuestionVO {
	private Long iqId;
	private String iqGubun;
	private String iqContent;
	private String iqSuggest;
	
    private String industryName;
}

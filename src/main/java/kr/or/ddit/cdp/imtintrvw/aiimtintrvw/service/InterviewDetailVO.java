package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service;

import lombok.Data;

@Data
public class InterviewDetailVO {
	private int idId;
	private int iqId;
	private int idlId;
	private String idCustum;
	private String idAnswer;
	private int idOrder;
	// 조회용 편의 필드
	private String  questionText; // NVL(iq_content, id_custom)
}
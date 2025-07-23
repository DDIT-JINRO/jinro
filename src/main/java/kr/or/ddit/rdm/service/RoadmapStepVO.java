package kr.or.ddit.rdm.service;

import lombok.Data;

@Data
public class RoadmapStepVO {

	private int rsId;
	private String rsStep;
	private String rsType;
	
	// 단계별 미션 이름
	private String stepName;
}

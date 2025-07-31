package kr.or.ddit.util.report.service;

import kr.or.ddit.util.alarm.service.AlarmType;

public enum ReportTargetType {
	REPORT_BOARD("G10001","게시글"),
	REPORT_REPLY("G10002","댓글");

	private final String code;
	private final String name;

	ReportTargetType(String code, String name){
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public static ReportTargetType fromCode(String code) {
		for(ReportTargetType type : values()) {
			if(type.code.equals(code)) {
				return type;
			}
		}
		throw new IllegalArgumentException("잘못된 코드 입력값 : "+code);
	}


}

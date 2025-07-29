package kr.or.ddit.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;


import org.springframework.http.HttpStatus;

public enum ErrorCode {
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "잘못된 요청입니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_ERROR", "서버 오류가 발생했습니다."),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "이 자원에 접근할 권한이 없습니다."),
	INVALID_USER(HttpStatus.BAD_REQUEST, "INVALID_USER", "로그인을 해주세요."),
	POINT_UPDATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "POINT_UPDATE_ERROR", "포인트 업데이트에 실패했습니다."),
	MISSION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "MISSION_UPDATE_ERROR", "미션 상태 업데이트에 실패했습니다."),
	FORBIDDEN_OPERATION(HttpStatus.FORBIDDEN, "FORBIDDEN_OPERATION", "허용되지 않는 작업입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}

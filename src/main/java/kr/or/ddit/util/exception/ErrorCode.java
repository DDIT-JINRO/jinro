package kr.or.ddit.util.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
		INVALID_INPUT("E400", "잘못된 요청입니다.");

	
	  private final String code;
	  private final String message;
}

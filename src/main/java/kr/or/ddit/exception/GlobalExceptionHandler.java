package kr.or.ddit.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	// 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = new ErrorResponse(
                errorCode.getStatus().value(),
                errorCode.getStatus().name(),
                errorCode.getCode(),
                errorCode.getMessage()
        );

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    // 예상치 못한 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        ErrorResponse response = new ErrorResponse(
                errorCode.getStatus().value(),
                errorCode.getStatus().name(),
                errorCode.getCode(),
                e.getMessage() != null ? e.getMessage() : errorCode.getMessage()
        );

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}

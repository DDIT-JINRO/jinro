package kr.or.ddit.util.setle.service;

import java.util.Objects;

/**
 * 서버(Controller)에서 클라이언트(JSP)로 결제 처리 결과를 응답할 때 사용되는 DTO입니다.
 */
public class PaymentResponseDto {
	private String status;  // "success" 또는 "failure"
    private String message; // 사용자에게 보여줄 상세 메시지
    private String orderId; // 처리된 주문의 고유 ID
    
    public PaymentResponseDto() {
        // 기본 생성자
    }

    // 모든 필드를 인자로 받는 생성자 (롬복의 @AllArgsConstructor 역할)
    public PaymentResponseDto(String status, String message, String orderId) {
    	this.status = status;
    	this.message = message;
    	this.orderId = orderId;
    }

    // Getter 메서드
	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getOrderId() {
		return orderId;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "PaymentResponseDto [status=" + status + ", message=" + message + ", orderId=" + orderId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(message, orderId, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentResponseDto other = (PaymentResponseDto) obj;
		return Objects.equals(message, other.message) && Objects.equals(orderId, other.orderId)
				&& Objects.equals(status, other.status);
	}

}

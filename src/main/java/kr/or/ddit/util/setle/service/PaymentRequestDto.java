package kr.or.ddit.util.setle.service;

import java.util.Objects;

/**
 * 클라이언트(JSP)에서 서버(Controller)로 결제 요청 시 전달되는 데이터를 담는 DTO입니다.
 */
public class PaymentRequestDto {
	private String impUid; // 아임포트 고유 결제 ID
	private String merchantUid; // 상점 주문 번호
	private String customerUid; // 고객 고유 식별자 (빌링키 용)
	private double amount; // 클라이언트가 요청한 결제 금액 (서버 검증용)

	// 기본 생성자 (필수)
	public PaymentRequestDto() {
	};

	// 모든 필드를 포함하는 생성자 (선택, 편리성을 위해 추가)
	public PaymentRequestDto(String impUid, String merchantUid, String customerUid, double amount) {
		this.impUid = impUid;
		this.merchantUid = merchantUid;
		this.customerUid = customerUid;
		this.amount = amount;
	}

	// Getter와 Setter 메서드
	public String getImpUid() {
		return impUid;
	}

	public void setImpUid(String impUid) {
		this.impUid = impUid;
	}

	public String getMerchantUid() {
		return merchantUid;
	}

	public void setMerchantUid(String merchantUid) {
		this.merchantUid = merchantUid;
	}

	public String getCustomerUid() {
		return customerUid;
	}

	public void setCustomerUid(String customerUid) {
		this.customerUid = customerUid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PaymentRequestDto [impUid=" + impUid + ", merchantUid=" + merchantUid + ", customerUid=" + customerUid
				+ ", amount=" + amount + "]";
	}

	@Override
	public boolean equals(Object object) {
		// 1. 자기 자신과의 비교: 같은 객체(동일한 메모리 주소)라면 true 반환
		if (this == object) {
			return true;
		}

		// 2. null 체크 및 클래스 타입 비교:
		// - 비교 대상이 null이거나, 현재 객체와 클래스 타입이 다르면 false 반환
		// - getClass() != o.getClass()는 상속 관계에서 엄격한 타입 비교를 수행합니다.
		// (instanceof는 상속 관계에서 느슨한 타입 비교를 수행)
		if (object == null || getClass() != object.getClass()) {
			return false;
		}

		// 3. 타입 캐스팅: 비교 대상 객체를 현재 클래스 타입(PaymentRequestDto)으로 캐스팅
		PaymentRequestDto that = (PaymentRequestDto) object;

		// 4. 필드 값 비교: 객체의 모든 핵심 필드 값이 같은지 비교
		// - 기본 타입 (double): Double.compare()를 사용하여 부동 소수점 오차 없이 비교합니다.
		// (== 연산자는 부동 소수점에서 정확하지 않을 수 있습니다.)
		// - 참조 타입 (String): Objects.equals()를 사용하여 null-safe하게 비교합니다.
		// (null.equals(다른값)으로 인한 NullPointerException 방지)
		return Double.compare(amount, that.amount) == 0 && // amount 필드 비교
				Objects.equals(impUid, that.impUid) && // impUid 필드 비교
				Objects.equals(merchantUid, that.merchantUid) && // merchantUid 필드 비교
				Objects.equals(customerUid, that.customerUid); // customerUid 필드 비교
	}

	// equals()를 오버라이드했다면,
	// 반드시 hashCode()도 함께 오버라이드해야 합니다.
	// 이는 equals()가 true를 반환하는 두 객체는 반드시 동일한
	// hashCode() 값을 반환해야 한다는 자바의 규약(Contract) 때문입니다.
	@Override
	public int hashCode() {
		// Objects.hash()를 사용하여 모든 핵심 필드의 해시 코드를 조합하여 하나의 int 값으로 반환합니다.
		// 이는 각 필드의 해시 코드를 효율적으로 결합해줍니다.
		return Objects.hash(impUid, merchantUid, customerUid, amount);
	}

}

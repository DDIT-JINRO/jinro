package kr.or.ddit.util.setle.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.mpg.mat.sih.web.SelfIntroHistoryController;
import kr.or.ddit.util.setle.service.IamportApiClient;
import kr.or.ddit.util.setle.service.MemberSubscriptionVO;
import kr.or.ddit.util.setle.service.PaymentRequestDto;
import kr.or.ddit.util.setle.service.PaymentResponseDto;
import kr.or.ddit.util.setle.service.PaymentService;
import kr.or.ddit.util.setle.service.PaymentVO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

	// private final SelfIntroHistoryController selfIntroHistoryController;

	@Autowired
	private IamportApiClient iamportApiClient;

	@Autowired
	private PaymentMapper paymentMapper;

	@Autowired
	private MemberSubscriptionMapper memberSubscriptionMapper;

	@Autowired
	private PayMemberMapper payMemberMapper;

//    PaymentServiceImpl(SelfIntroHistoryController selfIntroHistoryController) {
//        this.selfIntroHistoryController = selfIntroHistoryController;
//    }

	// merchant_uid를 생성하기 위해 pay_id의 다음 시퀀스 값을 조회
	@Override
	public int selectNextPayId() {
		return paymentMapper.selectNextPayId();
	}

	// 회원id을 기반으로 회원정보 조회
	@Override
	public MemberVO selectMemberById(int memId) {
		return payMemberMapper.selectMemberById(memId);
	}

	@Override
	@Transactional
	public PaymentResponseDto verifyAndProcessPayment(PaymentRequestDto requestDto, String loginId) {

		try {
			// 1. 아임포트 서버로부터 실제 결제 정보 조회
			Map<String, Object> paymentData = iamportApiClient.getPaymentInfo(requestDto.getImpUid());

			// 로그
			System.out.println("검증 요청: " + requestDto);
			System.out.println("paymentData: " + paymentData);

			if (paymentData == null) {
				System.err.println("paymentData is null - impUid: " + requestDto.getImpUid());
				return new PaymentResponseDto("failure", "결제 정보 조회에 실패했습니다.", requestDto.getMerchantUid());
			}

			System.out.println("결제 상태: " + paymentData.get("status"));

			// 2. 실제 결제 금액과 우리 시스템이 알아야 할 금액이 일치하는지 검증
			// 지금은 테스트로 100원으로 가정합니다.
//			double amountToBePaid = 100.0;
//			double paidAmount = ((Number) paymentData.get("amount")).doubleValue();
//			
//			if(paidAmount != amountToBePaid) {
//                // 금액이 일치하지 않으면 실패 응답 반환
//                return new PaymentResponseDto("failure", "결제 금액이 일치하지 않습니다.", requestDto.getMerchantUid());
//			}

			// 3. 결제 상태가 '결제완료(paid)' 상태인지 검증
			if (!"paid".equals(paymentData.get("status"))) {
				// 금액이 일치하지 않으면 실패 응답 반환
				return new PaymentResponseDto("failure", "결제가 완료되지 않았습니다. 현재 상태: " + paymentData.get("status"),
						requestDto.getMerchantUid());
			}

			// 4. 모든 검증을 통과했으므로 성공 응답을 반환
			// requestDto에서 빌링키(customer_uid)를 꺼내옵니다.
			String billingKey = requestDto.getCustomerUid();
			String successMessage = "서버 검증 성공! 발급된 빌링키 : " + billingKey;

			System.out.println("imp_uid: " + requestDto.getImpUid());
			System.out.println("customer_uid: " + requestDto.getCustomerUid());
			System.out.println("merchant_uid: " + requestDto.getMerchantUid());
			System.out.println("결제 상태: " + paymentData.get("status"));
			System.out.println("결제 금액: " + paymentData.get("amount"));

			// 디비저장
			// 1. loginId(문자열 "1")를 숫자로 변환합니다.
			int memId = Integer.parseInt(loginId);

			// 2. ID로 회원을 조회합니다. (selectMemberByEmail -> selectMemberById)
			MemberVO loginUser = payMemberMapper.selectMemberById(memId);

			// 혹시 모를 경우를 대비해 Null 체크를 추가하면 더 좋습니다.
			if (loginUser == null) {
				return new PaymentResponseDto("failure", "회원 정보를 찾을 수 없습니다. ID: " + memId, requestDto.getMerchantUid());
			}

			// 3. 구독 정보 먼저 생성 (회원 ID, 상품 ID는 예시로 1)
			MemberSubscriptionVO sub = new MemberSubscriptionVO();
			sub.setMemId(loginUser.getMemId()); // TODO: 실제 로그인 사용자로 대체
			sub.setSubId(1); // TODO: BASIC 상품 ID
			sub.setCustomerUid(requestDto.getCustomerUid());
			memberSubscriptionMapper.insertMemberSubscription(sub);

			// 4. 결제 정보 생성
			PaymentVO payment = new PaymentVO();
			payment.setImpUid(requestDto.getImpUid());
			payment.setPayAmount(requestDto.getAmount());
			payment.setMsId(sub.getMsId());
			paymentMapper.insertPayment(payment);

			// 정기결제예약

			// 다음 결제에 사용할 PAY_ID를 DB에서 다시 조회 및 String으로 형변환
			int nextPayID = paymentMapper.selectNextPayId();
			String nextMerchantUid = String.valueOf(nextPayID);

			// 다음 결제일 설정 (한달 후 )
			Calendar cal = Calendar.getInstance();
			// cal.add(Calendar.MONTH, 1); // 실제
			cal.add(Calendar.MINUTE, 1); // 테스트용
			Date nextPayDate = cal.getTime();

			// Unix Timestamp로 변환 (getTime()은 밀리초이므로 1000으로 나눠 초 단위로 변경)
			long nextPayTimestamp = nextPayDate.getTime() / 1000;

			// 다음 결제를 위한 새로운 주문번호 생성
			// 테스트용
			// String nextMerchantUid = "sub_"+ loginUser.getMemId() + "_" +
			// nextPayTimestamp;

			// 예약에 필요한 데이터 준비
			Map<String, Object> scheduleData = new HashMap<>();
			List<Map<String, Object>> schedules = new ArrayList<>();
			Map<String, Object> schedule = new HashMap<>();

			schedule.put("merchant_uid", nextMerchantUid);
			schedule.put("schedule_at", nextPayTimestamp);
			schedule.put("amount", 100.0);
			schedule.put("name", "BASIC");
			schedule.put("buyer_name", loginUser.getMemName());
			schedule.put("buyer_email", loginUser.getMemEmail());
			schedule.put("buyer_tel", loginUser.getMemPhoneNumber());
			schedules.add(schedule);

			scheduleData.put("schedules", schedules);
			scheduleData.put("customer_uid", requestDto.getCustomerUid());

			ObjectMapper mapper = new ObjectMapper();
			String jsonBody = mapper.writeValueAsString(scheduleData);
			System.out.println("전송할 예약 JSON: " + jsonBody);

			// 아임포트 API 호출
			List<Map<String, Object>> scheduleResult = iamportApiClient
					.scheduleSubscriptionPayment(requestDto.getCustomerUid(), scheduleData);

			if (scheduleResult == null) {
				log.error("정기결제 예약 실패 - 재시도 시도");

				// 🔁 scheduleData 다시 세팅 후 재시도
				scheduleData.put("customer_uid", requestDto.getCustomerUid());
				scheduleData.put("schedules", schedules);

				try {
					List<Map<String, Object>> retryResult = iamportApiClient.scheduleSubscriptionPayment(
							requestDto.getCustomerUid(), scheduleData
					);

			        if (retryResult == null) {
			            throw new RuntimeException("예약 실패 후 재시도도 실패했습니다.");
			        } else {
			            log.info("재시도 성공");
			        }
			    } catch (Exception ex) {
			    	throw new RuntimeException("예약 실패 후 재시도 중 예외 발생: " + ex.getMessage());
			    }
			} 

			
			
			
			return new PaymentResponseDto("success", successMessage, requestDto.getMerchantUid());

		} catch (Exception e) {
			// API 호출 중 예외 발생 시
			return new PaymentResponseDto("failure", "서버 처리 중 오류가 발생했습니다: " + e.getMessage(),
					requestDto.getMerchantUid());
		}
	}

	@Override
	public PaymentResponseDto handleWebhook(Map<String, Object> webhookData) {
		// 응답 DTO 객체를 생성하여 최종 결과를 담을 준비
		PaymentResponseDto responseDto = new PaymentResponseDto();

		try {
			// 아임포트에서 전달된 웹훅 데이터 중 imp_uid, merchant_uid 추출
			String impUid = (String) webhookData.get("imp_uid");
			String merchantUid = (String) webhookData.get("merchant_uid");

			// 둘 중 하나라도 없으면 실패 응답 처리
			if (impUid == null || merchantUid == null) {
				responseDto.setStatus("failure");
				responseDto.setMessage("imp_uid 또는 merchant_uid가 누락되었습니다.");
				responseDto.setOrderId(null);
				return responseDto;
			}

			// 아임포트 서버에 imp_uid를 이용해 결제 정보 조회
			Map<String, Object> paymentData = iamportApiClient.getPaymentInfo(impUid);

			// 조회 실패하거나 상태가 paid가 아니면 실패 처리
			if (paymentData == null || !"paid".equals(paymentData.get("status"))) {
				responseDto.setStatus("failure");
				responseDto.setMessage("결과가 유효하지 않습니다");
				responseDto.setOrderId(merchantUid);
				return responseDto;
			}

			// 정상적인 결제 정보인 경우, 후처리 진행을 위해 DTO 생성
			double amount = Double.parseDouble(paymentData.get("amount").toString());
			PaymentRequestDto requestDto = new PaymentRequestDto();
			requestDto.setImpUid(impUid);
			requestDto.setMerchantUid(merchantUid);
			requestDto.setCustomerUid((String) paymentData.get("customer_uid"));
			requestDto.setAmount(amount);

			// 기존의 검증 및 DB 저장 메서드 재사용 (loginId는 null: webhook이기 때문)
			PaymentResponseDto process = this.verifyAndProcessPayment(requestDto, null);

			// 처리된 결과 반환
			return process;

		} catch (Exception e) {
			// 예외 발생 시 실패 응답 처리
			e.printStackTrace();
			responseDto.setStatus("failure");
			responseDto.setMessage("웹훅 처리 중 예외 발생: " + e.getMessage());
			responseDto.setOrderId(null);
			return responseDto;
		}

	}
}

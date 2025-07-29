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

			// 3. 구독 정보 생성 및 DB 저장
			MemberSubscriptionVO sub = new MemberSubscriptionVO();
			sub.setMemId(loginUser.getMemId());
			sub.setSubId(1); // BASIC 상품 ID
			sub.setCustomerUid(requestDto.getCustomerUid()); // 빌링키
			sub.setSubStatus("Y"); // 구독 상태 활성화
			sub.setSubStrtDt(new Date()); // 구독 시작일
			sub.setLastPayDt(new Date()); // 마지막 결제 성공일
			sub.setRecurPayCnt(1); // 첫 결제이므로 1회차

			// ✅ 다음 결제일(nextPayDt)을 한 달 뒤로 설정하여 저장합니다.
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			// cal.add(Calendar.MINUTE, 3); 테스트
			sub.setSubEndDt(cal.getTime());

			memberSubscriptionMapper.insertMemberSubscription(sub);

			// 4. 결제 정보 생성
			PaymentVO payment = new PaymentVO();
			payment.setImpUid(requestDto.getImpUid());
			payment.setMerchantUid(requestDto.getMerchantUid());
			payment.setPayAmount(requestDto.getAmount());
			payment.setMsId(sub.getMsId());
			paymentMapper.insertPayment(payment);

			successMessage = "서버 검증 및 구독 정보 저장 성공! 발급된 빌링키: " + requestDto.getCustomerUid();
			return new PaymentResponseDto("success", successMessage, requestDto.getMerchantUid());

		} catch (Exception e) {
			return new PaymentResponseDto("failure", "서버 처리 중 오류: " + e.getMessage(), null);
		}
	}

	// 결제해야 할 구독 목록을 DB에서 조회
	@Override
	public List<MemberSubscriptionVO> findSubscriptionsDueForToday() {
		// TODO Auto-generated method stub
		return memberSubscriptionMapper.findSubscriptionsDueForToday();
	}

	// 구독 정보 업데이트 (다음 결제일, 결제 횟수 등)
	@Override
	public int updateAfterRecurringPayment(int msId) {

		return memberSubscriptionMapper.updateAfterRecurringPayment(msId);

	}

	@Override
	public int insertPayment(PaymentVO paymentVO) {
		return paymentMapper.insertPayment(paymentVO);

	}

	// 구독취소
	@Override
	@Transactional
	public boolean cancelSubscription(int memId) {
		// 1. 현재 사용자의 활성화된 구독 정보를 DB에서 조회
		MemberSubscriptionVO activeSub = memberSubscriptionMapper.findActiveSubscriptionByMemberId(memId);

		if (activeSub != null) {
			// 2. 구독 정보가 있다면, 상태를 '취소'로 변경
			int updateResult = memberSubscriptionMapper.updateStatusToCancelled(activeSub.getMsId());
			return updateResult > 0;
		}

		// 취소할 구독이 없는 경우
		return false;
	}

}

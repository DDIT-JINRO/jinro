package kr.or.ddit.util.setle.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.setle.service.IamportApiClient;
import kr.or.ddit.util.setle.service.PaymentRequestDto;
import kr.or.ddit.util.setle.service.PaymentResponseDto;
import kr.or.ddit.util.setle.service.PaymentService;

@Controller
@RequestMapping("/pay")
public class PayController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private IamportApiClient iamportApiClient;

	
	//구독 첫결제
	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, @AuthenticationPrincipal String loginId) {
		
		
	    System.out.println("### PayController가 받은 loginId: " + loginId);
	    
	    //받은 loginId를 실제 숫자(int)로 변환
	    int memId = Integer.parseInt(loginId);
		
		int nextPayId = paymentService.selectNextPayId();
		model.addAttribute("nextPayId", nextPayId);
		

		MemberVO loginUser = paymentService.selectMemberById(memId);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("memId", memId);
		
		return "setle/setle";
	}
	
	//결제 검증
	@PostMapping("/verify")
	@ResponseBody
	public ResponseEntity<PaymentResponseDto> verifyPayment(
			@RequestBody PaymentRequestDto requestDto,
			@AuthenticationPrincipal String loginId){
		System.out.println("requestDto : start");
		System.out.println(requestDto);
		System.out.println("requestDto : end");
		PaymentResponseDto response = paymentService.verifyAndProcessPayment(requestDto, loginId);
		
		if("success".equals(response.getStatus())) {
			return ResponseEntity.ok(response);
		} else {
			// 실패 시, HTTP 400 Bad Request 와 함께 실패 메시지 반환
            return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/test")
	@ResponseBody
	public String testSchedule() {
		try {
			String testCustomerUid ="test_customer_1753686773710";
			
			 Calendar cal = Calendar.getInstance();
	            cal.add(Calendar.MINUTE, 2); // 넉넉하게 2분 뒤로 예약
	            long nextPayTimestamp = cal.getTime().getTime() / 1000;
	            
	            String testMerchantUid = "test_schedule_" + UUID.randomUUID().toString().replace("-", "");;

	            Map<String, Object> scheduleData = new HashMap<>();
	            List<Map<String, Object>> schedules = new ArrayList<>();
	            Map<String, Object> schedule = new HashMap<>();
	            
	            schedule.put("merchant_uid", testMerchantUid);
	            schedule.put("schedule_at", nextPayTimestamp);
	            schedule.put("amount", 100.0);
	            schedule.put("name", "테스트 예약 결제");
	            
	            schedules.add(schedule);
	            scheduleData.put("schedules", schedules);
	            System.out.println("testCustomerUid : 시작");
                System.out.println(testCustomerUid);
                System.out.println("testCustomerUid : 끝");

                System.out.println("schedules : 시작");
                System.out.println(schedules);
                System.out.println("schedules : 끝");
	            List<Map<String, Object>> result = iamportApiClient.scheduleSubscriptionPayment(testCustomerUid, scheduleData);

	            if (result == null || result.isEmpty()) {
	                return "결제 예약 실패. 서버 콘솔 로그를 확인하세요.";
	            } else {
	                System.out.println("테스트 예약 성공: " + result);
	                return "결제 예약 성공! 포트원 관리자 페이지를 확인하세요. 응답: " + result;
	            }
		} catch (HttpClientErrorException e) {
		    System.err.println("💥 API 오류 상태코드: " + e.getStatusCode());
		    System.err.println("💥 오류 본문: " + e.getResponseBodyAsString());
		    return "테스트 중 예외 발생: " + e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
            return "테스트 중 예외 발생: " + e.getMessage();
		}
	}
}

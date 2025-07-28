package kr.or.ddit.util.setle.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import kr.or.ddit.util.setle.service.MemberSubscriptionVO;
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
	
	@PostMapping("/cancel-subscription")
	@ResponseBody
	public ResponseEntity<String> cancelSubscription(@AuthenticationPrincipal String loginId) {
		//받은 loginId를 실제 숫자(int)로 변환
	    int memId = Integer.parseInt(loginId);
	    try {
	        boolean isCancelled = paymentService.cancelSubscription(memId);
	        if (isCancelled) {
	            return ResponseEntity.ok("구독이 정상적으로 취소되었습니다.");
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("취소할 구독 정보가 없거나, 이미 취소된 상태입니다.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("구독 취소 처리 중 오류가 발생했습니다: " + e.getMessage());
	    }
	}
}

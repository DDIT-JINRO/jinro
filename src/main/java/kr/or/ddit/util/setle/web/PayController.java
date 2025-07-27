package kr.or.ddit.util.setle.web;

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

import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.setle.service.PaymentRequestDto;
import kr.or.ddit.util.setle.service.PaymentResponseDto;
import kr.or.ddit.util.setle.service.PaymentService;

@Controller
@RequestMapping("/pay")
public class PayController {
	
	@Autowired
	private PaymentService paymentService;

	
	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, @AuthenticationPrincipal String loginId) {
		
		int nextPayId = paymentService.selectNextPayId();
		model.addAttribute("nextPayId", nextPayId);
		

		MemberVO loginUser = paymentService.selectMemberByEmail(loginId);
		int memId = loginUser.getMemId();
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("memId", memId);
		
		return "setle/setle";
	}
	
	@PostMapping("/verify")
	@ResponseBody
	public ResponseEntity<PaymentResponseDto> verifyPayment(
			@RequestBody PaymentRequestDto requestDto,
			@AuthenticationPrincipal String loginId){
		PaymentResponseDto response = paymentService.verifyAndProcessPayment(requestDto, loginId);
		
		if("success".equals(response.getStatus())) {
			return ResponseEntity.ok(response);
		} else {
			// 실패 시, HTTP 400 Bad Request 와 함께 실패 메시지 반환
            return ResponseEntity.badRequest().body(response);
		}
	}
}

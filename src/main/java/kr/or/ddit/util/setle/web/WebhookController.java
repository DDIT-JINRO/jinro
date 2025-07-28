package kr.or.ddit.util.setle.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.util.setle.service.PaymentRequestDto;
import kr.or.ddit.util.setle.service.PaymentResponseDto;
import kr.or.ddit.util.setle.service.PaymentService;

//@Controller
@RequestMapping("/webhook")
public class WebhookController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/handleScheduledPayment")
	@ResponseBody
	public ResponseEntity<String> handleScheduledPayment(@RequestBody Map<String, Object> payload){
		
		try {
			String impUid = (String) payload.get("imp_uid");
			String merchantUid = (String) payload.get("merchant_uid");
			
			/// billingKey는 Webhook에서 안 오기 때문에 null로 넘김 (검증에는 영향 없음)
			PaymentResponseDto responseDto = paymentService.handleWebhook(payload);

				if ("success".equals(responseDto.getStatus())) {
					return ResponseEntity.ok("success: " + responseDto.getMessage());
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("검증 실패: " + responseDto.getMessage());
				}
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error: " + e.getMessage());
        }
        
	}
}

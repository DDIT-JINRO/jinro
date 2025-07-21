package kr.or.ddit.mpg.pay.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class PaymentController {

	@GetMapping("/pay/selectPaymentView.do")
	public String selectPaymentView () {
		return "mpg/pay/selectPaymentView";
	}
	
}

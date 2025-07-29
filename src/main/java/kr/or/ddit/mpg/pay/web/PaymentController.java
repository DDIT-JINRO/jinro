package kr.or.ddit.mpg.pay.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.setle.service.MemberSubscriptionVO;
import kr.or.ddit.util.setle.service.PaymentService;
import kr.or.ddit.util.setle.service.PaymentVO;
import kr.or.ddit.util.setle.service.SubscriptionService;
import kr.or.ddit.util.setle.service.SubscriptionVO;
import kr.or.ddit.util.setle.web.SubscriptionScheduler;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private SubscriptionService subscriptionService;

	@GetMapping("/pay/selectPaymentView.do")
	public String selectPaymentView(Model model, @AuthenticationPrincipal String loginId) {

		if (loginId != null) {
			int memId = Integer.parseInt(loginId);

			// 1. 현재 사용자의 구독 정보조회
			MemberSubscriptionVO currentSub = paymentService.selectByMemberId(memId);
			model.addAttribute("currentSub", currentSub);

			// 2. JS에서 사용할 로그인 사용자 정보 조회
			MemberVO loginUser = paymentService.selectMemberById(memId);
			model.addAttribute("loginUser", loginUser);
			
			//3. 이 사용자의 전체 결제 내역 조회 (구독 결제 내역 표시)
			List<PaymentVO> paymentHistory = paymentService.selectPaymentHistory(memId);
			model.addAttribute("paymentHistory", paymentHistory);
		}
		
		//화면에 보여줄 전체 구독 상품 목록 조회
		List<SubscriptionVO> subProducts = subscriptionService.selectAllProducts();
		model.addAttribute("subProducts", subProducts);

		return "mpg/pay/selectPaymentView";
	}

}

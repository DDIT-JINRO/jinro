package kr.or.ddit.cnslt.aicns.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.cnslt.resve.crsv.service.CounselingReserveService;
import kr.or.ddit.mpg.pay.service.MemberSubscriptionVO;
import kr.or.ddit.mpg.pay.service.PaymentService;
import kr.or.ddit.mpg.pay.service.PaymentVO;

@Controller
@RequestMapping("/cnslt/aicns")
public class AICounselingController {

	@Autowired
	private CounselingReserveService counselingReserveService;

	@Autowired
	private PaymentService paymentService;

	@GetMapping("/aicns.do")
	public String aicns() {
		return "cnslt/aicns/aicns";
	}

	@PostMapping("/aicnsPopUpStart")
	@ResponseBody
	public Map<String, Object> aicnsPopUpStart(HttpSession session, @RequestBody Map<String, Object> param){
		String topic = (String) param.get("topic");

		String topicKr = null;
		switch (topic) {
		case "MIND": {
			topicKr = "심리상담";
			break;
		}
		case "JOB": {
			topicKr = "취업상담";
			break;
		}
		case "STUDY": {
			topicKr = "학업상담";
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + topic);
		}

		session.setAttribute("topic", topic);
		session.setAttribute("topicKr", topicKr);
		Map<String, Object> responseData = new HashMap<>();
		String sessionId = session.getId();
		responseData.put("ok", true);
		responseData.put("message", "success");
		responseData.put("sessionId", sessionId);
		responseData.put("popupUrl", "/cnslt/aicns/aicnsPopUp?sid="+sessionId);
		return responseData;
	}

	@GetMapping("/aicnsPopUp")
	public String aicnsPopUp(Model model
						, @RequestParam(required = false) String sid
						, @AuthenticationPrincipal String memId
						, HttpSession session
						, HttpServletRequest req) {
		String sessionId = session.getId();
		// 세션아이디 비교, 회원로그인 비교, 횟수체크 .....
		if(!sessionId.equals(sid) || memId==null || "anonymousUser".equals(memId)) {
			model.addAttribute("errorMessage", "잘못된 접근입니다");
		}

		// 요청받은 url체크. AI상담페이지에서 버튼클릭으로 보낸 요청이 아니면 에러발생
		String referer = req.getHeader("Referer");
		if(referer==null || !referer.contains("/cnslt/aicns/aicns.do")) {
			model.addAttribute("errorMessage", "비정상적인 경로입니다");
			return "cnslt/aicns/aicnsPopUp";
		}

		// 횟수 차감. 차감실패시 에러 전송
		MemberSubscriptionVO currentSub = paymentService.selectByMemberId(Integer.parseInt(memId));
		PaymentVO paymentVO = counselingReserveService.selectLastSubcription(currentSub);
		int cnt = counselingReserveService.minusPayConsultCnt(paymentVO.getPayId());
		if(cnt <= 0) {
			model.addAttribute("errorMessage", "상담가능 잔여횟수가 없습니다");
		}

		// 로그 기록
		return "cnslt/aicns/aicnsPopUp";
	}

}

package kr.or.ddit.cnslt.aicns.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cnslt/aicns")
public class AICounselingController {

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
	public String aicnsPopUp(HttpSession session, @RequestParam String sid) {
		String sessionId = session.getId();
		// 세션아이디 비교, 회원로그인 비교, 횟수체크 .....

		// 다 정상이면 팝업창 포워딩
		return "cnslt/aicns/aicnsPopUp";
	}

}

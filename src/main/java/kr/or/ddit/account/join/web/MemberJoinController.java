package kr.or.ddit.account.join.web;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import kr.or.ddit.account.join.service.MemberJoinService;
import kr.or.ddit.account.join.service.impl.EmailService;
import kr.or.ddit.main.service.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/join")
@Slf4j
public class MemberJoinController {

	@Autowired
	private EmailService emailService;

	@Autowired
	MemberJoinService memberJoinService;

	@GetMapping("/joinpage.do")
	public String viewMemberJoinPage() {
		return "account/join";
	}

	@PostMapping("/emailCheck.do")
	@ResponseBody
	public String emailCheck(@RequestBody Map<String, Object> inputEmail) {
		String email = (String) inputEmail.get("email");
		MemberVO member = memberJoinService.selectUserEmail(email);

		if (member != null) {
			return "failed";
		} else {
			return "access";
		}
	}

	// 1. 인증 메일 전송
	@PostMapping("/sendMail.do")
	public ResponseEntity<?> sendEmail(@RequestBody Map<String, String> body, HttpSession session) {
		log.info("메일전송 도착");
		String email = body.get("email");
		String authCode = emailService.sendAuthCode(email);

		session.setAttribute("authCode", authCode);
		session.setAttribute("authCodeLimit", System.currentTimeMillis() + 3 * 60 * 1000); // 3분 제한

		return ResponseEntity.ok().body("sendOk");
	}

	// 2. 인증 코드 확인
	@ResponseBody
	@PostMapping("/verify.do")
	public String verifyCode(@RequestBody Map<String, Object> body, HttpSession session) {
		String inputCode = (String) body.get("code");
		String savedCode = (String) session.getAttribute("authCode");
		Long limitTime = (Long) session.getAttribute("authCodeLimit");

		if (savedCode == null || limitTime == null) {
			return "인증을 다시 시도해주세요";
		}

		if (System.currentTimeMillis() > limitTime) {
			return "인증 시간이 만료되었습니다.";
		}

		if (!savedCode.equals(inputCode)) {
			return "인증 코드가 일치하지 않습니다.";
		}

		// 인증 성공 처리
		session.removeAttribute("authCode");
		session.removeAttribute("authCodeLimit");

		return "success";
	}

	@ResponseBody
	@PostMapping("/checkNickname.do")
	public Map<String, Boolean> checkNickname(@RequestBody Map<String, String> request) {
		String nickname = request.get("nickname");
		boolean duplicate = memberJoinService.isNicknameExists(nickname);

		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("duplicate", duplicate);
		return response;
	}

	@PostMapping("/identityCheck.do")
	@ResponseBody
	public Map<String, Object> identityCheck() {
		/*
		 * @RequestBody Map<String, Object> requestBody
		 * String imp_uid = (String) requestBody.get("imp_uid");
		 * log.info("@@@@ imp_uid: " + imp_uid);
		 * 
		 * if (imp_uid == null || imp_uid.isEmpty()) { Map<String, Object> result = new
		 * HashMap<>(); result.put("success", false); result.put("message",
		 * "imp_uid가 누락되었습니다."); return result; }
		 */

		RestTemplate restTemplate = new RestTemplate();

        // 1. 인증 토큰 발급
        String tokenUrl = "https://api.iamport.kr/users/getToken";

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Map<String, String> tokenBody = new HashMap<>();
        tokenBody.put("imp_key", "1483263617088817"); // REST API 키
        tokenBody.put("imp_secret", "ZcbZ47ZHF536xGHAj4FVCNDTp2OZYhy6zW6nNwVNGSwq4Z7tATU5CUxGGRZtWfRgBE57PIFkL7kMovVs"); // REST API Secret

        HttpEntity<Map<String, String>> tokenRequest = new HttpEntity<>(tokenBody, tokenHeaders);

        ResponseEntity<Map> tokenResponse = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                tokenRequest,
                Map.class
        );

        // 응답에서 access_token 추출
        Map tokenRes = (Map) tokenResponse.getBody().get("response");
        String accessToken = (String) tokenRes.get("access_token");
        
        
        log.info("accessToken @@@@" + accessToken);

		// 2. imp_uid로 본인 인증 정보 조회
		/*
		 * String certificationUrl = "https://api.iamport.kr/certifications/" + imp_uid;
		 * 
		 * HttpHeaders authHeaders = new HttpHeaders();
		 * authHeaders.setBearerAuth(accessToken); // Bearer 토큰 설정
		 * 
		 * HttpEntity<String> authEntity = new HttpEntity<>(authHeaders);
		 * 
		 * ResponseEntity<Map> certificationResponse =
		 * restTemplate.exchange(certificationUrl, HttpMethod.GET, authEntity,
		 * Map.class);
		 * 
		 * Map<String, Object> finalResult = new HashMap<>(); if
		 * (certificationResponse.getStatusCode().is2xxSuccessful()) { Map<String,
		 * Object> responseBody = (Map<String, Object>)
		 * certificationResponse.getBody().get("response");
		 * 
		 * // 본인 인증 성공 시 finalResult.put("success", true); finalResult.put("message",
		 * "본인 인증 정보 조회 성공"); finalResult.put("data", responseBody); // 인증 정보 데이터
		 * log.info("@@@@ 인증 정보: " + responseBody);
		 * 
		 * } else { // 본인 인증 실패 시 finalResult.put("success", false);
		 * finalResult.put("message", "본인 인증 정보 조회 실패"); log.error("@@@@ 인증 정보 조회 실패: "
		 * + certificationResponse.getBody()); }
		 */

		return null;
	}
}
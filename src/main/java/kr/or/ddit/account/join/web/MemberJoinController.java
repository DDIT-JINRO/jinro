package kr.or.ddit.account.join.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		
		String email = (String)inputEmail.get("email");
		MemberVO member = memberJoinService.selectUserEmail(email);
		
		if(member != null) {
			return "failed";
		}else {
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
        String inputCode = (String)body.get("code");
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
}

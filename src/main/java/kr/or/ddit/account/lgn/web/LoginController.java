package kr.or.ddit.account.lgn.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.account.lgn.service.LoginService;
import kr.or.ddit.main.service.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	// 로그인 페이지 이동
	@GetMapping("/login")
	public String login() {
		
		log.info("login arrive");
		
		return "account/login";
	}
	
	@GetMapping("/test")
	public String testPg() {
		return "account/test";
	}
	
	@GetMapping("/error/logReq")
	public String errorLogReq() {
		
		return ("account/loginReq");
	}
	
	@GetMapping("lgn/findId.do")
	public String viewFindIdPage() {
		
		return "account/findId";
	}
	
	@PostMapping("/lgn/findId.do")
	@ResponseBody
	public Map<String, Object> findId(@RequestBody Map<String, String> body) {
		String name = body.get("name");
	    String rawPhone = body.get("phone");
	    String phone = "";
	    MemberVO member = new MemberVO();
	    
	    if (rawPhone.length() == 11) {
	         phone = rawPhone.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
	    } else if (rawPhone.length() == 10) {
	        phone = rawPhone.replaceFirst("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
	    } else {
	        phone = rawPhone;
	    }
	    
	    log.info("phone : "+phone);
	    member.setMemName(name);
	    member.setMemPhoneNumber(phone);
	    
	    List<MemberVO> memList = loginService.findEmailStringByNameAndPhone(member);
	    
	    log.info(""+memList);
	    
	    Map<String, Object> result = new HashMap<>();
	    result.put("count", memList.size());
	    result.put("memList", memList);    
	    return result;
	}
	
	@GetMapping("/lgn/findPw.do")
	public String viewFindPwPage() {
		return "account/findPw";
	}
	
}

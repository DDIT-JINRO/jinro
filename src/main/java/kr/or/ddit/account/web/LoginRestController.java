package kr.or.ddit.account.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import kr.or.ddit.account.service.LoginService;
import kr.or.ddit.config.jwt.JwtProperties;
import kr.or.ddit.config.jwt.JwtUtil;
import kr.or.ddit.main.service.MemberVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginRestController {
	
	@Autowired
	LoginService loginService;
	@Autowired
	JwtProperties jwtProperties;
	
	
	@PostMapping("/memberLogin")
	public Map<String, Object> testLogin(@RequestBody MemberVO memVO) {
	    
	    Map<String, Object> resultMap = loginService.loginProcess(memVO);
	    
	    
//	    log.info(jwtProperties.getSecretKey());
	    
	    
	    
	    return resultMap;
	}

}

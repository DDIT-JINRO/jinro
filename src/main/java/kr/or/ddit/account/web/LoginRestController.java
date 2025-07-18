package kr.or.ddit.account.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.account.service.LoginService;
import kr.or.ddit.main.service.MemberVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginRestController {
	
	@Autowired
	LoginService loginService;
	
	@PostMapping("/memberLogin")
	public Map<String, Object> testLogin(@RequestBody MemberVO memVO,HttpServletResponse resp) {
	    
	    Map<String, Object> resultMap = loginService.loginProcess(memVO);
	    
	    Cookie accessTokenCookie = new Cookie("accessToken", (String) resultMap.get("accessToken"));
        Cookie refreshTokenCookie = new Cookie("refreshToken", (String) resultMap.get("refreshToken"));

        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 31);

        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);

        resp.addCookie(accessTokenCookie);
        resp.addCookie(refreshTokenCookie);
	    
	    resultMap.remove("accessToken");
	    resultMap.remove("refreshToken");
	    
	    return resultMap;
	}

}

package kr.or.ddit.config.jwt;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import kr.or.ddit.account.service.impl.LoginMapper;
import kr.or.ddit.account.web.LoginController;
import kr.or.ddit.main.service.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

    private final LoginController loginController;

	private final long accessTokenExpire = 1000 * 60 * 30; // 30분

	@Autowired
	LoginMapper loginMapper;

	@Autowired
	JwtProperties jwtProperties;

    JwtUtil(LoginController loginController) {
        this.loginController = loginController;
    }

	public String createAccessToken(String memId) {
		
		int intMemId = Integer.parseInt(memId);
		
		MemberVO memberVO = loginMapper.selectAuthByMemId(intMemId);

		String auth = memberVO.getMemRole();
		
		if (auth.equals("R01001")) {
			return Jwts.builder().setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ:JWT
					.claim("roles", List.of("ROLE_USER")).setIssuer(this.jwtProperties.getIssuer()).setSubject(memId)
					.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + accessTokenExpire))
					.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()).compact();
		}else if(auth.equals("R01002")) {
			return Jwts.builder().setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ:JWT
					.claim("roles", List.of("ROLE_USER", "ROLE_ADMIN")).setIssuer(this.jwtProperties.getIssuer())
					.setSubject(memId).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpire))
					.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()).compact();			
		}else if(auth.equals("R01003")){
			return Jwts.builder().setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ:JWT
					.claim("roles", List.of("ROLE_USER", "ROLE_COUNSEL")).setIssuer(this.jwtProperties.getIssuer())
					.setSubject(memId).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpire))
					.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()).compact();
			
		}else {
			return Jwts.builder().setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ:JWT
					.claim("roles", List.of("ROLE_USER", "ROLE_CNSLEADER")).setIssuer(this.jwtProperties.getIssuer())
					.setSubject(memId).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpire))
					.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()).compact();
		}
	}

	public String createRefreshToken(String memId) {
		// 예: 1_550e8400-e29b-41d4-a716-446655440000
		return memId + "_" + UUID.randomUUID().toString();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);
			log.info("유효한 토큰입니다.");
			return true;
		} catch (ExpiredJwtException e) {
			log.info("만료된 토큰입니다.");
		} catch (JwtException e) {
			log.info("유효하지 않은 토큰입니다.");
		}
		return false;
	}

	public String extractMemId(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
			return claims.getSubject();
		} catch (JwtException e) {
			return null;
		}
	}
	
	public String resolveToken(HttpServletRequest request) {
        
		if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {              	
                	return cookie.getValue(); // 토큰 반환
                }
            }
        }
        return null;
    }
	
	// JWT에서 username (subject) 추출
	public String getUsernameFromToken(String token) {
	    Claims claims = getClaims(token);
	    return claims.getSubject(); // .setSubject() 했던 값 (예: 사용자 ID)
	}

	// JWT에서 roles 추출
	public List<String> getRolesFromToken(String token) {
	    Claims claims = getClaims(token);
	    return claims.get("roles", List.class); // .claim("roles", List.of(...)) 했던 값
	}

	// JWT에서 Claims 추출 (공통 유틸)
	private Claims getClaims(String token) {
	    return Jwts.parser()
	            .setSigningKey(jwtProperties.getSecretKey()) // 시크릿 키 사용
	            .parseClaimsJws(token)
	            .getBody(); // JWT의 Payload 부분
	}
}

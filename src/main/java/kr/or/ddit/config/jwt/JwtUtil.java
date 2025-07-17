package kr.or.ddit.config.jwt;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	private final long accessTokenExpire = 1000 * 60 * 30;     // 30분
	private final long refreshTokenExpire = 1000 * 60 * 60 * 24 * 14; // 14일
	
	@Autowired
	JwtProperties jwtProperties;
	
    public String createAccessToken(String memId) {
    	
    	log.info(jwtProperties.getSecretKey());
    	
    	
        return Jwts.builder()
                .setSubject(memId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpire))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public String createRefreshToken(String memId) {
        return Jwts.builder()
                .setSubject(memId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpire))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
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
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}

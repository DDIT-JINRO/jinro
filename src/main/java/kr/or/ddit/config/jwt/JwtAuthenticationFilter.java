package kr.or.ddit.config.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String uri = request.getRequestURI();

		// 정적 리소스 요청은 필터 제외 (css, js, image, favicon 등)
		if (uri.startsWith("/static/") || uri.startsWith("/css/") || uri.startsWith("/js/")
				|| uri.startsWith("/images/") || uri.equals("/favicon.ico") || uri.startsWith("/font/")) {
			filterChain.doFilter(request, response); // 그냥 통과
			return;
		}

		// 1. 쿠키나 헤더에서 JWT 꺼내기
		try {
			String token = jwtUtil.resolveToken(request);
			System.out.println("토큰 유효성 검증 시도 : " + token);
			// 2. 토큰이 유효하면
			if (token != null && token !="" && jwtUtil.validateToken(token)) {
				
				
				String username = jwtUtil.getUsernameFromToken(token);
				List<String> roles = jwtUtil.getRolesFromToken(token); // 클레임에서 roles 꺼내기

				// 3. UserDetails 생성
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

				// 4. 인증 객체 생성 및 SecurityContext에 저장
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			
				
				filterChain.doFilter(request, response);
			} else if (token != null && token !="" && !jwtUtil.validateToken(token)) {
				String refreshToken = jwtUtil.resolveRefreshToken(request);
				if (refreshToken != null) {
					String userId = refreshToken.split("_")[0];
					String storedRefreshToken = jwtUtil.getRefreshTokenFromDb(userId);
					if (refreshToken.equals(storedRefreshToken)) {
						String newAccessToken = jwtUtil.createAccessToken(userId);
						Cookie newTokenCookie = new Cookie("accessToken", newAccessToken);
                        newTokenCookie.setPath("/");
                        newTokenCookie.setHttpOnly(true);
                        newTokenCookie.setMaxAge(60 * 31);
                        response.addCookie(newTokenCookie);
                        
                        
                        String username = jwtUtil.getUsernameFromToken(newAccessToken);
        				List<String> roles = jwtUtil.getRolesFromToken(newAccessToken);

        				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        				List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
        						.collect(Collectors.toList());

        				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        						userDetails, null, authorities);
        				SecurityContextHolder.getContext().setAuthentication(authentication);
        				
        				filterChain.doFilter(request, response);
					}else {
						SecurityContextHolder.clearContext();
					}
				}
			} else {
				// 토큰 없거나 유효하지 않을 때 인증 초기화 (null로 설정)
				SecurityContextHolder.clearContext();
			}

		} catch (Exception e) {
			// 예외 발생 시 인증 초기화 및 로그 기록
			SecurityContextHolder.clearContext();
			logger.error("JWT 인증 처리 중 오류", e);
		}

		// 다음 필터로 진행
		filterChain.doFilter(request, response);
	}
}

package kr.or.ddit.config;

import kr.or.ddit.config.jwt.JwtAuthenticationFilter;
import kr.or.ddit.config.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	
	public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/test").authenticated() // requestMatcheres 내부에 권한이 필요한 요청은 넣어주어야합니다.
				.anyRequest().permitAll()).csrf(csrf -> csrf.disable()) // CSRF 비활성화 (개발용)
				.formLogin(form -> form.disable()) // 로그인 폼 비활성화
				.httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 인증 비활성화
				.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService),UsernamePasswordAuthenticationFilter.class)
				.logout(logout -> logout
					    .logoutUrl("/logout")
					    .logoutSuccessHandler((request, response, authentication) -> {
					        String referer = request.getHeader("Referer");
					        if (referer != null) {
					            response.sendRedirect(referer);
					        } else {
					            response.sendRedirect("/");
					        }
					    })
					    .invalidateHttpSession(true)
					    .deleteCookies("JSESSIONID", "accessToken", "refreshToken")
					);
		return http.build();
	}

	// 비밀번호 암호화를 위한 인코더
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}

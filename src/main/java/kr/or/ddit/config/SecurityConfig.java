package kr.or.ddit.config;

import kr.or.ddit.account.service.LoginService;
import kr.or.ddit.account.web.LoginController;
import kr.or.ddit.config.jwt.JwtAuthenticationFilter;
import kr.or.ddit.config.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	
	
	
	
    private final LoginController loginController;
    
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService, LoginController loginController) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		this.loginController = loginController;
	}
	
	
	public WebSecurityCustomizer configure() {
		return (web)->web.ignoring()
				.requestMatchers(new AntPathRequestMatcher("/static/**"));
	}
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/test").hasRole("USER") // requestMatcheres 내부에 권한이 필요한 요청은 넣어주어야합니다.
				.anyRequest().permitAll()).csrf(csrf -> csrf.disable())
				.exceptionHandling(ex -> ex
				.authenticationEntryPoint(authenticationEntryPoint()) // 인증 안됐을 때
				.accessDeniedHandler(accessDeniedHandler())           // 인증됐지만 권한 없을 때
			)
				.formLogin(form -> form.disable()) 
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

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		System.out.println("여기가 401에러");
		return (request, response, authException) -> {
			response.sendRedirect("/error/logReq"); // 에러 페이지로 리다이렉트
		};
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		System.out.println("여기가 403에러");
		return (request, response, accessDeniedException) -> {
			response.sendRedirect("/error/authReq"); // 다른 에러 페이지로 리다이렉트 가능
		};
	}
	
}

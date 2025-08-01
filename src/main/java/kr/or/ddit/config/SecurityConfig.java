package kr.or.ddit.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import kr.or.ddit.admin.las.service.VisitLogService;
import kr.or.ddit.config.jwt.JwtAuthenticationFilter;
import kr.or.ddit.config.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SecurityConfig {
	
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	private final VisitLogService visitLogService;
	
	@Value("${DEV.SERVER.BASE_URL}")
	private String SERVER_URL;
	
	public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService, VisitLogService visitLogService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		this.visitLogService = visitLogService;
		
	}
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(SERVER_URL+":5173"));  // React dev 서버
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(auth -> auth
				.requestMatchers("/test").hasRole("USER") // 모든 사용자는 유저권한을 포함시켜놨습니다. 로그인이 필요한 서비스는 여기에 넣어주시면 됩니다.
				.anyRequest().permitAll()).csrf(csrf -> csrf.disable())
				.exceptionHandling(ex -> ex
				.authenticationEntryPoint(authenticationEntryPoint()) // 인증 안됐을 때
				.accessDeniedHandler(accessDeniedHandler())           // 인증됐지만 권한 없을 때
			)
				.formLogin(form -> form.disable()) 
				.httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 인증 비활성화
				.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService, visitLogService),UsernamePasswordAuthenticationFilter.class)
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
		return (request, response, authException) -> {
			response.sendRedirect("/error/logReq"); // 에러 페이지로 리다이렉트
		};
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return (request, response, accessDeniedException) -> {
			response.sendRedirect("/error/authReq"); // 다른 에러 페이지로 리다이렉트 가능
		};
	}
	
}

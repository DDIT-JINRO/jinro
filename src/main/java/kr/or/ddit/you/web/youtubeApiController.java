package kr.or.ddit.you.web;

import java.util.List;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class youtubeApiController {

	private static final String API_KEY = "AIzaSyA8qDS5UDxsmNB9MmHCCinfgQrfhY94IXc";
	private static final String ACCESS_TOKEN = "ya29.a0AfB_byEXAMPLE-TOKEN"; // 실제 액세스 토큰으로 교체

	@GetMapping("/main/youtube")
	public String youtube(Model model) {
		// 1. 기본 URL
		String baseUrl = "https://www.googleapis.com/youtube/v3/search";

		// 2. 검색 키워드
		String keyword = "dream";

		// 3. URL 빌드
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
				.queryParam("part", "snippet")
				.queryParam("q", keyword) // 검색어
				.queryParam("maxResults", 30) // 결과 개수 제한
				.queryParam("key", API_KEY) // API 키 추가
				.queryParam("type","video");

		// 4. 헤더 설정
		//이 코드는 HTTP 요청의 헤더 부분을 설정합니다.
		HttpHeaders headers = new HttpHeaders();
		// 응답 유형
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));

		// 5. 요청 준비
		HttpEntity<Void> entity = new HttpEntity<>(headers);

		// 6. 요청 실행
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
				builder.toUriString(), //요청 url
				HttpMethod.GET,
				entity, // 헤더 포함한 요청
				String.class //응답형식
	    );

		// 7. 결과 로그 + JSP 전달
		log.info("유튜브 API 응답: {}", response.getBody());
		model.addAttribute("youtubeJson", response.getBody());

		return "you/youtube";
	}
}

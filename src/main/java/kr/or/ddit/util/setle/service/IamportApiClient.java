package kr.or.ddit.util.setle.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

//Gson 라이브러리 관련 import 추가
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 아임포트 REST API와 통신하는 클라이언트 클래스입니다. 아임포트 API 키를 통해 토큰을 발급받고, 결제 정보를 조회하며, 정기 결제
 * 등을 수행합니다.
 */
@Component
public class IamportApiClient {

	@Value("${iamport.api.key}")
	private String apiKey;

	@Value("${iamport.api.secret}")
	private String apiSecret;

	// RESTful API 호출을 위한 Spring의 클라이언트. 주로 HTTP GET, POST 등의 요청을 보냅니다.
	// final로 선언하여 한 번만 초기화되도록 합니다.
	private final RestTemplate restTemplate = new RestTemplate();

	// JSON 데이터를 자바 객체로, 자바 객체를 JSON 데이터로 변환할 때 사용됩니다.
	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 아임포트 Access Token을 발급받는 메서드입니다. 아임포트 API 호출 시 인증을 위해 필요한 토큰입니다.
	 *
	 * @return 발급된 Access Token 문자열 또는 토큰 발급 실패 시 null
	 * @throws JsonProcessingException
	 */
	public String getAccessToken() throws JsonProcessingException {
		// 아임포트 토큰 발급 API의 URL
		String url = "https://api.iamport.kr/users/getToken";

		System.out.println("apiKey: " + apiKey);
		System.out.println("apiSecret: " + apiSecret);

		System.out.println("restTemplate message converters:");
		restTemplate.getMessageConverters().forEach(converter -> {
			System.out.println(converter.getClass());
		});

		// HTTP 요청 헤더 설정: JSON 형태로 데이터를 보낼 것임을 명시
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// DTO 객체 사용
		/*
		 * { "imp_key": "your_api_key", "imp_secret": "your_api_secret" } 아임포트 api가 위의
		 * 형식의 json구조를 원하기에 IamportTokenRequest을 사용
		 */
		IamportTokenRequest tokenRequest = new IamportTokenRequest(apiKey, apiSecret);
		String jsonBody = objectMapper.writeValueAsString(tokenRequest);
		try {
			System.out.println("보내는 JSON: " + objectMapper.writeValueAsString(tokenRequest));
		} catch (JsonProcessingException e) {
			e.printStackTrace(); // 혹은 log.error(...) 사용
		}

		HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

		try {
			// POST 요청을 보내고 응답을 Map 형태로 받습니다.
			ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

			// 응답 본문이 존재하고 'response' 필드를 포함하는지 확인합니다.
			/*
			 * "response"가 변수 이름이 아니라, API 응답 JSON의 특정 '키(key)'를 나타내는 문자열이기 때문에 ""로 담는다 성공응답
			 * 예시 { "code": 0, "message": "성공", "response": { // <-- 여기가 "response"라는
			 * 키(key)입니다. "access_token": "발급된_토큰_값", "now": 1678886400, "expired_at":
			 * 1678890000 } }
			 */
			if (response.getBody() != null && response.getBody().containsKey("response")) {
				// 응답 본문에서 'response' 필드에 해당하는 Map을 추출합니다.
				Map<String, Object> responseBody = (Map<String, Object>) response.getBody().get("response");
				// 추출된 Map에서 'access_token' 값을 반환합니다.
				return (String) responseBody.get("access_token");
			}
			return null; // 응답이 유효하지 않은 경우 null 반환
		} catch (Exception e) {
			// 예외 발생 시 콘솔에 에러 메시지를 출력합니다. (실제 운영 환경에서는 로깅 프레임워크 사용 권장)
			System.err.println("Failed to get Iamport Access Token: " + e.getMessage());
			return null;
		}
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	/**
	 * imp_uid(아임포트 결제 고유 ID)를 사용하여 특정 결제 정보를 조회하는 메서드입니다. 클라이언트로부터 전달받은 결제 정보를 서버에서
	 * 검증할 때 사용됩니다.
	 *
	 * @param impUid 아임포트가 부여한 결제 고유 식별자
	 * @return 조회된 결제 정보가 담긴 Map (성공 시), 또는 null (실패 시)
	 * @throws JsonProcessingException
	 */
	public Map<String, Object> getPaymentInfo(String impUid) throws JsonProcessingException {
		// 먼저 Access Token을 발급받습니다. 토큰 없이는 API 호출 불가.
		String accessToken = getAccessToken();
		if (accessToken == null) {
			return null; // 토큰 발급 실패 시 즉시 종료
		}

		// 결제 정보 조회 API의 URL. impUid를 경로 변수로 사용합니다.
		String url = "https://api.iamport.kr/payments/" + impUid;

		// HTTP 요청 헤더 설정: 인증 토큰 (Bearer Token)을 포함합니다.
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken); // "Bearer [Access Token]" 형태로 헤더에 추가

		// 요청 본문 없이 헤더만 포함하는 HTTP 엔티티 생성
		HttpEntity<Void> request = new HttpEntity<Void>(headers);

		try {
			// GET 요청을 보내고 응답을 Map 형태로 받습니다.
			ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

			// 응답 본문이 존재하고 'response' 필드를 포함하는지 확인합니다.
			if (response.getBody() != null & response.getBody().containsKey("response")) {
				// 'response' 필드에 해당하는 결제 정보 Map을 반환합니다.
				return (Map<String, Object>) response.getBody().get("response");
			}
			return null; // 응답이 유효하지 않은 경우 null 반환
		} catch (Exception e) {
			System.err.println("Failed to get Iamport Payment Info for imp_uid " + impUid + ": " + e.getMessage());
			return null;
		}
	}

	/**
	 * customer_uid(고객 고유 식별자)를 사용하여 정기 결제를 예약하는 메서드입니다. 일반적으로 최초 빌링키 발급 후 다음 자동 결제를
	 * 미리 예약할 때 사용됩니다. 아임포트 API: POST /subscribe/payments/schedule
	 *
	 * @param customerUid  아임포트에 등록된 고객의 고유 식별자
	 * @param scheduleData 예약할 결제 정보가 담긴 Map (merchant_uid, amount, schedule_at(Unix
	 *                     timestamp) 등이 포함된 'schedules' 리스트 포함)
	 * @return API 응답 Map (성공 시), 또는 null (실패 시)
	 * @throws JsonProcessingException
	 */
	public List<Map<String, Object>> scheduleSubscriptionPayment(String customerUid, Map<String, Object> scheduleData)
			throws JsonProcessingException {
		String accessToken = getAccessToken();
		if (accessToken == null) {
			return null; // 토큰 발급 실패 시 즉시 종료
		}

		System.out.println("토큰발급 : 시작");
		System.out.println(accessToken);
		System.out.println("토큰발급 : 끝");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = "https://api.iamport.kr/subscribe/payments/schedule";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(accessToken);

		// API 요청 본문 구성: customer_uid와 예약 스케줄 정보
		Map<String, Object> body = new HashMap<>();
		body.put("customer_uid", customerUid);
		body.put("schedules", scheduleData.get("schedules")); // 'schedules' 키에 해당하는 리스트를 추출하여 전달

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

		// 수정본
		ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
		System.out.println("전체 응답: " + response.getBody());

		Map<String, Object> bodyMap = response.getBody();
		if (bodyMap != null) {
			Object code = bodyMap.get("code");
			if (code instanceof Integer && ((Integer) code) == 0) {
				// 성공
				return (List<Map<String, Object>>) bodyMap.get("response");
			} else {
				System.out.println("❌ 실패 코드 또는 응답: " + bodyMap);
				return null;
			}
		}
		System.out.println("❌ 응답 body가 null입니다");
		return null;
	}

	/**
	 * customer_uid(고객 고유 식별자)를 사용하여 빌링키로 즉시 결제를 수행하는 메서드입니다. 주로 정기 결제 스케줄러에서 매달 자동
	 * 결제를 시도할 때 사용됩니다. 아임포트 API: POST /subscribe/payments/again
	 *
	 * @param customerUid 고객 고유 식별자
	 * @param merchantUid 상점에서 생성하는 이번 결제 건의 고유 주문번호
	 * @param amount      결제 금액
	 * @param name        상품명
	 * @return API 응답 Map (성공 시), 또는 null (실패 시)
	 * @throws JsonProcessingException
	 */
	public Map<String, Object> payAgain(String customerUid, String merchantUid, double amount, String name)
			throws JsonProcessingException {
		String accessToken = getAccessToken();
		if (accessToken == null) {
			return null; // 토큰 발급 실패 시 즉시 종료
		}

		String url = "https://api.iamport.kr/subscribe/payments/again";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(accessToken);

		// --- Gson을 사용한 수동 JSON 생성 ---
		Gson gson = new Gson();
		JsonObject requestBodyJson = new JsonObject();
		requestBodyJson.addProperty("customer_uid", customerUid);
		requestBodyJson.addProperty("merchant_uid", merchantUid);
		requestBodyJson.addProperty("amount", amount);
		requestBodyJson.addProperty("name", name);

		String jsonBody = gson.toJson(requestBodyJson);
		System.out.println("보내는 JSON (payAgain): " + jsonBody);

		HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

		try {
			// POST 요청을 보내고 응답을 Map 형태로 받습니다.
			ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
			if (response.getBody() != null && response.getBody().containsKey("response")) {
				return (Map<String, Object>) response.getBody().get("response");
			}
			return null;
		} catch (HttpClientErrorException e) {
			System.err.println("❌ payAgain API 호출 실패! Status: " + e.getStatusCode());
			System.err.println("Response Body: " + e.getResponseBodyAsString());
			return null;
		} catch (Exception e) {
			System.err.println("❌ Failed to perform payAgain for customer_uid " + customerUid + ": " + e.getMessage());
			return null;
		}
	}

	/**
	 * customer_uid(고객 고유 식별자)를 사용하여 예약된 결제가 실행되기전에 해당 예약의 취소를 수행하는 메서드입니다. 주로 정기 결제
	 * 스케줄러에서 매달 자동 결제를 시도할 때 사용됩니다. 아임포트 API: POST
	 * /subscribe/payments/schedule/{merchant_uid}
	 *
	 * @param customerUid 고객 고유 식별자
	 * @param merchantUid 상점에서 생성하는 이번 결제 건의 고유 주문번호
	 * @throws JsonProcessingException
	 */
	public Map<String, Object> unscheduleSubscriptionPayment(String customerUid, String merchantUid)
			throws JsonProcessingException {
		String accessToken = getAccessToken();
		if (accessToken == null) {
			return null; // 토큰 발급 실패 시 즉시 종료
		}

		String url = "https://api.iamport.kr/subscribe/payments/unschedule";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(accessToken);

		// API 요청 본문 구성: 고객, 주문
		Map<String, Object> body = new HashMap<>();
		body.put("customer_uid", customerUid);
		body.put("merchant_uid", List.of(merchantUid));

		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(body, headers);

		try {
			// POST 요청을 보내고 응답을 Map 형태로 받습니다.
			ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
			if (response.getBody() != null && response.getBody().containsKey("response")) {
				return (Map<String, Object>) response.getBody().get("response");
			}
			return null;
		} catch (Exception e) {
			System.err.println("Failed to perform payAgain for customer_uid " + customerUid + ": " + e.getMessage());
			return null;
		}

	}

}

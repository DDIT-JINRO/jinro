package kr.or.ddit.pse.cat.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import kr.or.ddit.account.lgn.service.LoginService;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.pse.cat.service.CareerAptitudeTestService;
import kr.or.ddit.pse.cat.service.TemporarySaveVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CareerAptitudeTestServiceImpl implements CareerAptitudeTestService {

	@Value("${DEV.TEST.API_KEY}")
	private String TEST_API_KEY;

	@Autowired
	LoginService loginService;

	@Autowired
	CareerAptitudeTestMapper careerAptitudeMapper;

	@Override
	public Map<String, Object> testSubmit(Map<String, Object> data, String memId) {
		int testSeq = (int) data.get("testNo");
		if (testSeq == 30 || testSeq == 31 || testSeq == 24 || testSeq == 25 || testSeq == 20 || testSeq == 21) {
			Map<String, Object> resURL = reqTestNor(data, memId);
			return resURL;
		} else {
			return Map.of("msg", "failed");
		}
	}

	public Map<String, Object> reqTestNor(Map<String, Object> data, String memId) {

		Map<String, Object> answers = (Map<String, Object>) data.get("answers");

		String answer = String.join(" ", answers.entrySet().stream().map((e) -> e.getKey() + "=" + e.getValue()).toArray(String[]::new));

		MemberVO memVO = new MemberVO();
		int numMemId = Integer.parseInt(memId);
		memVO = loginService.selectById(numMemId);

		String memGen = memVO.getMemGen();
		String gender = "";
		if ("G11001".equals(memGen)) {
			gender = "100323";
		} else if ("G11002".equals(memGen)) {
			gender = "100324";
		}

		String testNo = String.valueOf(data.get("testNo"));
		String ageGroup = String.valueOf(data.get("ageGroup"));

		String url = "https://www.career.go.kr/inspct/openapi/test/report";

		RestTemplate restTemplate = new RestTemplate();

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("apikey", TEST_API_KEY);
		requestBody.put("qestrnSeq", testNo);
		requestBody.put("trgetSe", ageGroup);
		requestBody.put("gender", gender);
		requestBody.put("grade", "3");
		requestBody.put("startDtm", System.currentTimeMillis());
		requestBody.put("answers", answer);

		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// HttpEntity로 결합
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		// 요청 실행
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		String responseBody = response.getBody();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(responseBody);

			String succYn = root.get("SUCC_YN").asText();
			String errorReason = root.get("ERROR_REASON").asText();
			JsonNode resultNode = root.get("RESULT");
			long inspctSeq = resultNode.get("inspctSeq").asLong();
			String reportUrl = resultNode.get("url").asText();

			insertResultKeyword(reportUrl, memId, testNo);

			return Map.of("msg", "success", "reportUrl", reportUrl);
		} catch (Exception e) {
			return Map.of("msg", "failed");
		}

	}

	@Override
	public String testV2Submit(Map<String, Object> data, String memId) {

//		List<Map<>> data.get("answers");

		Map<Integer, Integer> answers = (Map<Integer, Integer>) data.get("answers");

//		String answer = String.join(" ",
//				answers.entrySet().stream().map((e) -> e.getKey() + "=" + e.getValue()).toArray(String[]::new));
//
//		MemberVO memVO = new MemberVO();
//		int numMemId = Integer.parseInt(memId);
//		memVO = loginService.selectById(numMemId);
//
//		String memGen = memVO.getMemGen();
//		String gender = "";
//		if ("G11001".equals(memGen)) {
//			gender = "100323";
//		} else if ("G11002".equals(memGen)) {
//			gender = "100324";
//		}
//
//		String testNo = String.valueOf(data.get("testNo"));
//		String ageGroup = String.valueOf(data.get("ageGroup"));
//
//		String url = "https://www.career.go.kr/inspct/openapi/v2/report";
//
//		RestTemplate restTemplate = new RestTemplate();
//
//		Map<String, Object> requestBody = new HashMap<>();
//		requestBody.put("apikey", TEST_API_KEY);
//		requestBody.put("qestrnSeq", testNo);
//		requestBody.put("trgetSe", ageGroup);
//		requestBody.put("gender", gender);
//		requestBody.put("grade", "3");
//		requestBody.put("startDtm", System.currentTimeMillis());
//		requestBody.put("answers", answer);
//
//		// 헤더 설정
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		// HttpEntity로 결합
//		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//		// 요청 실행
//		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//		String responseBody = response.getBody();
//
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			JsonNode root = mapper.readTree(responseBody);
//
//			String succYn = root.get("SUCC_YN").asText();
//			String errorReason = root.get("ERROR_REASON").asText();
//			JsonNode resultNode = root.get("RESULT");
//			long inspctSeq = resultNode.get("inspctSeq").asLong();
//			String reportUrl = resultNode.get("url").asText();
//
////		    log.info("SUCC_YN: {}", succYn);
////		    log.info("ERROR_REASON: {}", errorReason);
////		    log.info("inspctSeq: {}", inspctSeq);
////		    log.info("Report URL: {}", reportUrl);

//			return reportUrl;
//		} catch (Exception e) {
//			return "응답 JSON 파싱 실패";
//		}
		return "";
	}

	@Override
	public String testSave(Map<String, Object> data, String memId) {

		String testType = "";
		String type = data.get("testNo") + "";

		if ("30".equals(type) || "31".equals(type))
			testType = "G37001";
		if ("33".equals(type) || "34".equals(type))
			testType = "G37002";
		if ("24".equals(type) || "25".equals(type))
			testType = "G37003";
		if ("20".equals(type) || "21".equals(type))
			testType = "G37004";
		if ("6".equals(type))
			testType = "G37005";
		if ("8".equals(type))
			testType = "G37006";
		if ("9".equals(type))
			testType = "G37007";
		if ("10".equals(type))
			testType = "G37008";

		ObjectMapper objectMapper = new ObjectMapper();
		TemporarySaveVO temporarySaveVO = new TemporarySaveVO();

		String json = "";
		int intMemId = Integer.parseInt(memId);

		try {

			json = objectMapper.writeValueAsString(data);
			temporarySaveVO.setMemId(intMemId);
			temporarySaveVO.setTsContent(json);
			temporarySaveVO.setTsType(testType);
			int result = careerAptitudeMapper.testSave(temporarySaveVO);

			if (result == 1) {
				return "success";
			} else {
				return "failed";
			}

		} catch (JsonProcessingException e) {
			return "failed";
		}
	}

	@Override
	public Map<String, Object> getSavingTest(String qno, String memId) {

		String testType = "G3700" + qno;

		int intMemId = Integer.parseInt(memId);
		Map<String, Object> map = new HashMap<String, Object>();

		TemporarySaveVO temporarySaveVO = new TemporarySaveVO();

		temporarySaveVO.setTsType(testType);
		temporarySaveVO.setMemId(intMemId);

		TemporarySaveVO savingTest = careerAptitudeMapper.getSavingTest(temporarySaveVO);
		if (savingTest == null) {
			map.put("msg", "failed");
			return map;
		}

		String testContent = savingTest.getTsContent();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, Object> savingContent = objectMapper.readValue(testContent, Map.class);
			savingContent.put("msg", "success");
			return savingContent;
		} catch (JsonMappingException e) {
			map.put("msg", "failed");
			return map;
		} catch (JsonProcessingException e) {
			map.put("msg", "failed");
			return map;
		}

	}

	@Override
	public void delTempSaveTest(String no, String memId) {
		String testType = "G3700" + no;
		int intMemId = Integer.parseInt(memId);
		TemporarySaveVO temporarySaveVO = new TemporarySaveVO();
		temporarySaveVO.setTsType(testType);
		temporarySaveVO.setMemId(intMemId);
		careerAptitudeMapper.delTempSaveTest(temporarySaveVO);
	}

	@Override
	public void insertResultKeyword(String url, String memId, String testNo) {
		url = "https://www.career.go.kr/cloud/w/inspect/value2/report?seq=NzgyMDY4NDk=";

		WebDriver driver = null;
		try {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless", "--no-sandbox", "--disable-gpu", "--disable-dev-shm-usage");

			// 3. WebDriver 인스턴스 생성
			driver = new ChromeDriver(options);
			// 4. 웹 페이지로 이동하여 원하는 작업 수행
			driver.get(url);

			// 5. 암시적 대기 설정 (페이지 로딩까지 최대 5초 대기)
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

			String result = "";
			
			switch (testNo) {
			case "24": {
				// 1. 상위 가치관
	            List<WebElement> topResultElements = driver.findElements(By.cssSelector(".aptitude-tbl-list.value.import tbody tr td:nth-of-type(1)"));
	            String topResultValues = topResultElements.stream().map(WebElement::getText).collect(Collectors.joining(" , "));

	            // 2. 내가 중요하게 생각하는 가치관
	            List<WebElement> myChoiceElements = driver.findElements(By.cssSelector(".aptitude-tbl-list.value.import tbody tr td:nth-of-type(2)"));
	            String myChoiceValues = myChoiceElements.stream().map(WebElement::getText).collect(Collectors.joining(" , "));
	            
	            // 3. 나의 가치지향 텍스트 추출
	            WebElement myTypeElement = driver.findElement(By.cssSelector("p.value-custom > span.fcolor-green"));
	            String myValueType = myTypeElement.getText().replace("\"", "").trim(); // "성취지향" -> 성취지향

	            // 3-2. 해당 유형에 맞는 직업 목록 찾기
	            String recommendedJobs = findJobsByValueType(driver, myValueType);

				result = "상위 가치관 : " + topResultValues + " / 내가 중요하게 생각하는 가치관 : " + myChoiceValues + " / 나의 가치지향 유형 : " + myValueType + " / 추천 직업 목록 : " + recommendedJobs;
				
				break;
			}
			
			case "25": {
				List<WebElement> elements = driver.findElements(By.cssSelector(".cont_result > p.txt_guide > span.emph_b"));

				String keyword1 = "";
				String keyword2 = "";

				if (elements.size() >= 2) {
					keyword1 = elements.get(1).getText().trim();
					keyword2 = elements.get(2).getText().trim();
				}

				List<String> educationResults = parseTable(driver, ".cont_result > table.tbl_result:nth-of-type(1)", testNo);
				List<String> majorResults = parseTable(driver, ".cont_result > table.tbl_result:nth-of-type(2)", testNo);
				
				result = "직업 가치관 상위 2개 단어 : " + keyword1 + ", " + keyword2 + "/ 학력별 추천 직업 : " + educationResults + " / 전공별 추천 직업 : " + majorResults;
				
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + testNo);
			}
			
			log.info("전체 겨로가다ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ : " + result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}

	private List<String> parseTable(WebDriver driver, String tableSelector, String testNo) {
		List<String> results = new ArrayList<>();

		List<WebElement> rows = driver.findElements(By.cssSelector(tableSelector + " > tbody > tr"));

		for (WebElement row : rows) {
			String title = row.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
			List<WebElement> jobNames = row.findElements(By.cssSelector("td:nth-child(2) a"));

			String content = jobNames.stream().map(WebElement::getText).collect(Collectors.joining(" , "));

			results.add(title + ": " + content);
		}

		return results;
	}
	
	// 가치지향 직업 추출
    private String findJobsByValueType(WebDriver driver, String valueType) {
        List<WebElement> jobSections = driver.findElements(By.cssSelector("ul.value4-job > li"));
        
        for (WebElement section : jobSections) {
        	// 가치지향 제목
            WebElement titleElement = section.findElement(By.tagName("dt"));
            if (titleElement.getText().contains(valueType)) {
            	
                // 직업 텍스트를 추출하여 반환
                List<WebElement> jobLinks = section.findElements(By.cssSelector("dd > a"));
                log.info("리스트 길이 이거 실화임? : " + jobLinks.size());
                return jobLinks.stream().map(WebElement::getText).collect(Collectors.joining(" , "));
            }
        }
        // 일치하는 유형을 찾지 못한 경우 빈 리스트 반환
        return "";
    }
}

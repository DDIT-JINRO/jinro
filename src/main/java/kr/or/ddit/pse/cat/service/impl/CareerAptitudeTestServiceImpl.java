package kr.or.ddit.pse.cat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.google.api.client.json.JsonString;

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
	public String testSubmit(Map<String, Object> data, String memId) {
		int testSeq = (int) data.get("testNo");
		if (testSeq == 30 || testSeq == 31 || testSeq == 24 || testSeq == 25 || testSeq == 20 || testSeq == 21) {
			String resURL = reqTestNor(data, memId);
			return resURL;
		} else {
			return "failed";
		}
	}

	public String reqTestNor(Map<String, Object> data, String memId) {

		Map<String, Object> answers = (Map<String, Object>) data.get("answers");

		String answer = String.join(" ",
				answers.entrySet().stream().map((e) -> e.getKey() + "=" + e.getValue()).toArray(String[]::new));

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

//		    log.info("SUCC_YN: {}", succYn);
//		    log.info("ERROR_REASON: {}", errorReason);
//		    log.info("inspctSeq: {}", inspctSeq);
//		    log.info("Report URL: {}", reportUrl);

			return reportUrl;
		} catch (Exception e) {
			return "응답 JSON 파싱 실패";
		}

	}

	@Override
	public String testV2Submit(Map<String, Object> data, String memId) {

//		List<Map<>> data.get("answers");

		Map<Integer, Integer> answers = (Map<Integer, Integer>) data.get("answers");
		log.info(answers + "");
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
		String type = data.get("testNo")+""; 
	
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
		
		log.info("testType : " + testType);
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
		
		String testType = "G3700"+qno;
		
		int intMemId = Integer.parseInt(memId);
		Map<String, Object> map = new HashMap<String, Object>();
		
		TemporarySaveVO temporarySaveVO = new TemporarySaveVO();
		
		temporarySaveVO.setTsType(testType);
		temporarySaveVO.setMemId(intMemId);
		
		TemporarySaveVO savingTest = careerAptitudeMapper.getSavingTest(temporarySaveVO);
		if(savingTest == null) {
			map.put("msg", "failed");
			return  map;
		}
		
		String testContent = savingTest.getTsContent();
		ObjectMapper objectMapper = new ObjectMapper();
        try {
			Map<String, Object> savingContent = objectMapper.readValue(testContent, Map.class);
			savingContent.put("msg", "success");
			return savingContent;
		} catch (JsonMappingException e) {
			map.put("msg", "failed");
			return  map;
		} catch (JsonProcessingException e) {
			map.put("msg", "failed");
			return  map;
		}
        
	}

	@Override
	public void delTempSaveTest(String no, String memId) {
		String testType = "G3700"+no;
		int intMemId = Integer.parseInt(memId);
		TemporarySaveVO temporarySaveVO = new TemporarySaveVO();
		temporarySaveVO.setTsType(testType);
		temporarySaveVO.setMemId(intMemId);
		careerAptitudeMapper.delTempSaveTest(temporarySaveVO);
	}
}

package kr.or.ddit.util.apr.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import kr.or.ddit.util.apr.service.AiProofreadResumeService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AiProofreadResumeServiceImpl implements AiProofreadResumeService {

	private final String apiKey = "AIzaSyDZK7bUXiT7MdHDDNn183nTyBtlyOsZCSE"; // 실제 키
	private final Client client = Client.builder().apiKey(apiKey).build();
	private ExecutorService executor;

	@PostConstruct
	public void init() {
		executor = Executors.newCachedThreadPool();
	}

	@PreDestroy
	public void shutdown() {
		if (executor != null && !executor.isShutdown()) {
			executor.shutdown();
		}
	}

	@Override
	public String proofreadResume(String resumeItems) {
		if (resumeItems == null || resumeItems.isEmpty()) {
			throw new IllegalArgumentException("이력서 항목이 비어 있습니다.");
		}

		String prompt = String.format("""
				당신은 이력서 첨삭 전문가입니다.
				아래 이력서 내용을 분석해서 항목별로 간결하고 명확하게 피드백을 작성해주세요.
				300자 이내로 요약하고, 다음 기준을 포함하세요:

				- 개선할 점
				- 더 나은 표현 제안
				- 누락 또는 과장된 부분

				이력서 내용 (HTML 형식):
				%s
				""", resumeItems);

		GenerateContentResponse response = client.models.generateContent("gemini-1.5-flash", prompt, null);
		log.info("response : {}", response.text());

		return response.text();

	}
}

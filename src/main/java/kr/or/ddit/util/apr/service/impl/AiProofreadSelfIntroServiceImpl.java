package kr.or.ddit.util.apr.service.impl;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import kr.or.ddit.util.apr.service.AiProofreadSelfIntroService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AiProofreadSelfIntroServiceImpl implements AiProofreadSelfIntroService {

	private String apiKey = "AIzaSyAZr-m-tAKezgsWhJ-hPmUIeVP1nCnm_Cs";

	private final Client client = Client.builder().apiKey(apiKey).build(); // 재사용

	private static final String SECTION_PROOFREAD_PROMPT_TEMPLATE = """
			당신은 자기소개서 첨삭 전문가입니다.
			아래 문항의 질문과 답변을 평가하고, 간결하게 피드백을 작성하세요.
			각 피드백은 300자 이내로 요약하고, 핵심 문제점과 개선 제안만 간단히 제시합니다.

			질문: %s
			답변: %s

			피드백은 아래와 같이 핵심만 간결하게 작성해주세요:
			- 문제점 (５줄 이내)
			- 수정 제안 (６줄 이내)

			※ 불필요한 반복 설명 없이, 구체적인 사례와 표현을 최소화하여 요약된 피드백을 제공합니다.
			""";

	private ExecutorService executor;

	public AiProofreadSelfIntroServiceImpl() {
	}

	@PostConstruct
	public void initialize() {
		this.executor = Executors.newCachedThreadPool();
	}

	@PreDestroy
	public void shutdownExecutor() {
		if (this.executor != null && !this.executor.isShutdown()) {
			this.executor.shutdown();
		}
	}

	@Override
	public String proofreadCoverLetter(List<Map<String, String>> selfIntroSections) {
		log.info("🔥 proofreadCoverLetter() 메서드 진입 확인");
		long startTime = System.currentTimeMillis();

		if (selfIntroSections == null || selfIntroSections.isEmpty()) {
			throw new IllegalArgumentException("자기소개서 섹션 내용이 비어있습니다.");
		}

		List<String> feedbacks = proofreadEachSection(selfIntroSections);

		String result = feedbacks.stream().collect(Collectors.joining("\n---\n"));

		log.info("🚀 AI 첨삭 응답 완료 | 전체 소요: {}ms", System.currentTimeMillis() - startTime);
		return result;
	}

	private List<String> proofreadEachSection(List<Map<String, String>> sections) {
		// Client client = Client.builder().apiKey(apiKey).build();
		log.info("✏️ 문항별 첨삭 시작 - 총 {}문항", sections.size());

		long totalStart = System.currentTimeMillis();

		List<CompletableFuture<String>> futures = new ArrayList<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

		for (int i = 0; i < sections.size(); i++) {
			final int index = i;
			final Map<String, String> section = sections.get(index);

			futures.add(CompletableFuture.supplyAsync(() -> {
				long secStart = System.currentTimeMillis();
				LocalDateTime startTime = LocalDateTime.now();
				log.info("🚀 [문항 {}번] 시작 시간: {}", index + 1, startTime.format(formatter));

				String question = section.getOrDefault("question_title", "");
				String answer = section.getOrDefault("original_content", "");

				String prompt = String.format(SECTION_PROOFREAD_PROMPT_TEMPLATE, question, answer);

				try {
					GenerateContentResponse response = client.models.generateContent("gemini-1.5-flash", prompt, null);

					long duration = System.currentTimeMillis() - secStart;
					LocalDateTime endTime = LocalDateTime.now();
					log.info("✅ [문항 {}번] 종료 시간: {} | 소요: {}ms", index + 1, endTime.format(formatter), duration);

					return String.format("[문항 %d번 - AI 피드백]\n%s", index + 1, response.text());
				} catch (Exception e) {
					log.error("❌ [문항 {}번] 처리 실패: {}", index + 1, e.getMessage());
					return String.format("[문항 %d번 - AI 피드백]\n(피드백 생성 중 오류가 발생했습니다.)", index + 1);
				}

			}, this.executor));
		}

		List<String> results = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

		long totalDuration = System.currentTimeMillis() - totalStart;
		log.info("🏁 전체 문항 첨삭 완료 | 총 소요 시간: {}ms", totalDuration);

		return results;
	}
}

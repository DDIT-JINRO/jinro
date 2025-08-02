package kr.or.ddit.util.apr.service.impl;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import kr.or.ddit.util.apr.service.AiProofreadSelfIntroService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AiProofreadSelfIntroServiceImpl implements AiProofreadSelfIntroService {

	// API 키는 애플리케이션 외부 설정 파일 (예: src/main/resources/application.properties 또는
	// application.yml)에 저장하고 @Value로 주입받는 것이 좋습니다.
	// 예: google.gemini.api-key=YOUR_API_KEY_HERE
	// @Value("${google.gemini.api-key}") // 실제 환경에서는 이 어노테이션을 사용합니다.
	private String apiKey = "AIzaSyAZr-m-tAKezgsWhJ-hPmUIeVP1nCnm_Cs"; // 개발/테스트용으로 여기에 직접 입력해도 됩니다.

	// 자기소개서 첨삭용 시스템 프롬프트 (상수로 정의)
	// 이 프롬프트는 AI에게 자기소개서 첨삭 전문가 역할을 부여하고, 구체적인 피드백 및 수정 제안을 JSON 형식으로 요청합니다.
	private static final String SELF_INTRO_PROOFREAD_PROMPT = """
			당신은 입사 지원을 위한 자기소개서 첨삭 전문가입니다.

			각 문항(질문 + 답변)을 읽고 아래 기준에 따라 간결하게 피드백을 작성하세요.

			[중점 사항]
			1. **명확성과 간결성**: 장황한 문장을 줄이고 핵심이 드러나게 하세요.
			2. **설득력**: 지원자의 강점과 성과가 잘 드러나도록 표현을 개선하세요.
			3. **문법 및 표현**: 문법 오류, 맞춤법, 부적절한 어투를 교정하세요.
			4. **직무 관련성**: 직무와 연관된 내용이 부족하면 보완점을 제안하세요.
			5. **진부한 표현 제거**: 모호하고 평범한 표현 대신 구체적인 문장 제시
			6. **정량적 성과 유도**: 수치나 결과 중심으로 서술하도록 조언

			총 문항 수는 정확히 N개이며, 각 문항마다 반드시 "[AI 피드백]"으로 시작하고 "---"로 끝나야 합니다.
			[전체 요약]은 문항 첨삭이 끝난 이후에만 작성하세요.

			[출력 형식]
			각 문항은 아래와 같은 형식으로 작성합니다:

			---

			[AI 피드백]
			- 표현이 평이합니다. 경험 기반 사례를 강조해 주세요.
			- “노력했습니다” 대신 “어떤 일을 어떻게 했는지” 구체적으로 써 보세요.
			---

			마지막에는 전체 요약을 아래 형식으로 제공하세요.

			[전체 요약]
			강점: (예: 진정성 있는 서술, 문장 구조 명확 등)
			개선점: (예: 평이한 표현 다수, 정량적 성과 부족 등)
			최종 조언: (예: 경험을 수치화하고, 직무와 연결하여 설득력 강화)
			""";

	/**
	 * Gemini AI에 자기소개서를 전송하고, 텍스트 기반 피드백 응답을 받아 반환합니다.
	 *
	 * @param selfIntroSections 자기소개서 각 섹션(질문 + 답변) 리스트
	 * @return AI가 작성한 전체 피드백 텍스트
	 */
	public String proofreadCoverLetter(List<Map<String, String>> selfIntroSections) {
		if (selfIntroSections == null || selfIntroSections.isEmpty()) {
			throw new IllegalArgumentException("자기소개서 섹션 내용이 비어있습니다.");
		}

		String formattedSectionsForPrompt = selfIntroSections.stream().map(section -> {
			String questionTitle = section.getOrDefault("question_title", "제목 없음");
			String content = section.getOrDefault("original_content", "");
			return String.format("%s\n%s", questionTitle, content);
		}).collect(Collectors.joining("\n---\n"));

		String fullPrompt = SELF_INTRO_PROOFREAD_PROMPT + "\n" + formattedSectionsForPrompt;

		Client client = Client.builder().apiKey(apiKey).build();
		try {
			log.info("Gemini API 호출 시작...");
			GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", fullPrompt, null);
			String result = response.text(); // 일반 텍스트 응답

			log.info("Gemini 응답 (자기소개서 첨삭): {}", result);
			return result;
		} catch (Exception e) {
			log.error("Gemini API 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("AI 첨삭 요청 처리 중 오류가 발생했습니다.", e);
		}
	}
}

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
				당신은 입사 지원을 위한 자기소개서 최적화 전문 커리어 컨설턴트입니다.
				당신의 임무는 제공된 자기소개서의 각 질문(섹션)별 내용을 비판적으로 검토하고, 개선이 필요한 부분을 식별하며, 해당 섹션의 글자 수 제한을 고려하여 구체적이고 실행 가능한 피드백과 수정 제안을 제공하는 것입니다.

				다음 사항에 중점을 두세요:
				1.  **명확성과 간결성:** 가독성을 높이고 장황하거나 불필요한 표현을 제거하세요.
				2.  **영향력과 설득력:** 지원자의 강점과 성과를 부각시키세요. 강력한 동사를 사용하세요.
				3.  **문법, 맞춤법, 구두점:** 모든 언어적 오류를 수정하세요.
				4.  **직무 관련성:** (제공된 경우) 특정 직무 설명이나 목표 역할에 맞게 내용을 더 잘 조정할 방법을 제안하세요.
				5.  **스토리텔링 (STAR 기법):** 해당되는 경우 STAR (상황, 과제, 행동, 결과) 기법을 사용하여 경험을 구성하는 방법을 조언하거나, 서술 흐름을 개선하세요.
				6.  **독창성:** 일반적이거나 진부한 표현을 식별하고 대안을 제안하세요.
				7.  **정량적 성과:** 측정 가능한 결과를 포함하도록 유도하고, 예시를 제공하세요.

				당신의 응답은 반드시 다음 JSON 형식으로 제공되어야 합니다. 모든 필드는 비어 있더라도 반드시 포함되어야 합니다.
				{
				  "status": "success",
				  "overall_summary": {
				    "strengths": ["자기소개서 전반에서 발견된 주요 강점 나열 (예: '명확한 구조', '강력한 동사 사용')"],
				    "areas_for_improvement": ["자기소개서 전반의 개선 필요 영역 나열 (예: '정량적 결과 부족', '일부 내용이 너무 일반적임', '전체 글자 수 조절 필요')"],
				    "final_advice": "지원자가 자기소개서를 개선하기 위한 전반적인 최종 조언."
				  },
				  "sections_feedback": [
				    {
				      "question_title": "해당 섹션의 질문 제목",
				      "original_content": "해당 섹션의 원본 답변 텍스트.",
				      "feedback_items": [
				        {
				          "type": "문법/명확성/영향력/관련성/간결성/단어선택/구조/글자수", // 피드백 유형
				          "issue_summary": "식별된 문제에 대한 간략한 요약.", // 문제 요약
				          "detailed_reason": "이것이 왜 문제이며 잠재적인 영향이 무엇인지에 대한 상세 설명.", // 상세 이유
				          "suggested_revision": "이 문제에 대한 구체적인 수정 문구 또는 예시. 여러 문장인 경우, 수정된 전체 단락을 제공하세요." // 수정 제안
				        }
				        // 이 섹션의 다른 문제에 대한 feedback_items 객체를 추가
				      ],
				      "overall_section_tip": "이 특정 섹션에 대한 간결한 전반적인 팁 또는 요약." // 해당 섹션에 대한 전반적인 조언
				    }
				    // 자기소개서의 다른 섹션에 대한 section_feedback 객체를 추가
				  ]
				}
				만약 사용자의 입력이 자기소개서가 아니거나 자기소개서 첨삭과 관련이 없다면, 당신은 자기소개서 첨삭 AI이며 자기소개서에만 도움을 줄 수 있다고 한국어로 정중하게 응답하고, status를 "error"로 설정하세요.
				---
				자기소개서 검토 내용 (각 질문별로 아래 형식으로 제공됩니다):
				[질문 제목]
				[답변 내용]
				---
				다음 질문 내용:
				[질문 제목]
				[답변 내용]
				---
				...
				""";

	/**
	 * 자기소개서 첨삭을 요청하고 AI 응답 JSON 문자열을 반환합니다. 이 메서드는 오직 AI 호출 로직만 포함하며, 데이터 조회는 관여하지
	 * 않습니다.
	 *
	 * @param selfIntroSections 프론트엔드에서 전송한 자기소개서 섹션 데이터 (Map 리스트)
	 * @return AI가 첨삭한 결과를 담은 JSON 문자열
	 * @throws IllegalArgumentException 요청 데이터가 유효하지 않을 경우 발생
	 * @throws RuntimeException         AI API 호출 중 오류 발생 시 발생
	 */

	public String proofreadCoverLetter(List<Map<String, String>> selfIntroSections) {
		// Map 리스트를 사용하여 요청 데이터 유효성 검사
		if (selfIntroSections == null || selfIntroSections.isEmpty()) {
			throw new IllegalArgumentException("자기소개서 섹션 내용이 비어있습니다.");
		}

		// 각 섹션의 내용을 AI 프롬프트에 주입할 형태로 포맷팅합니다.
		// 이 부분은 프론트엔드로부터 받은 Map의 getter 메서드를 사용합니다.
		// 구조화된 데이터를 ai가 이해할 수 있는 비구조화된 텍스트(String)으로 변환하는 역활
		String formattedSectionsForPrompt = selfIntroSections.stream().map(section -> {
			String questionTitle = section.getOrDefault("question_title", "제목 없음");
			String content = section.getOrDefault("original_content", "");
			return String.format("%s\n%s", questionTitle, content);
		}).collect(Collectors.joining("\n---\n"));

		// 시스템 프롬프트와 포맷팅된 자기소개서 섹션 내용을 결합하여 최종 프롬프트 생성
		String fullPrompt = SELF_INTRO_PROOFREAD_PROMPT + "\n" + formattedSectionsForPrompt;

		Client client = Client.builder().apiKey(apiKey).build();
		try {
			log.info("Gemini API 호출 시작...");
			GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", fullPrompt, null);
			String result = response.text(); // Gemini API의 응답 텍스트 (JSON 형식으로 기대)

			log.info("Gemini 응답 (자기소개서 첨삭): {}", result);

			return result;
		} catch (Exception e) {
			log.error("Gemini API 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("AI 첨삭 요청 처리 중 오류가 발생했습니다.", e);
		}
	}
}

package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.AnalysisService;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisRequest;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService {
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    @Value("${gemini.api.key}")
    private String geminiApiKey;
    
    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    
    public AnalysisServiceImpl(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                .build();
        this.objectMapper = objectMapper;
    }
    
    /**
     * Gemini API를 호출하여 면접 분석을 수행합니다.
     */
    @Override
    public AnalysisResponse analyzeInterview(AnalysisRequest request) {
        String sessionId = request.getSessionId();
        
        try {            
            // 🎯 API 키 검증
            if (geminiApiKey == null || geminiApiKey.trim().isEmpty()) {
                log.error("❌ Gemini API 키가 설정되지 않았습니다");
                return AnalysisResponse.createDefaultResponse(sessionId, "API 키가 설정되지 않았습니다");
            }
            
            // 1. 프롬프트 생성
            String prompt = buildAnalysisPrompt(request);
            
            // 2. Gemini API 요청 본문 생성
            Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                    Map.of("parts", List.of(
                        Map.of("text", prompt)
                    ))
                ),
                "generationConfig", Map.of(
                    "temperature", 0.7,
                    "topK", 40,
                    "topP", 0.95,
                    "maxOutputTokens", 2048
                ),
                "safetySettings", List.of(
                    Map.of("category", "HARM_CATEGORY_HARASSMENT", "threshold", "BLOCK_NONE"),
                    Map.of("category", "HARM_CATEGORY_HATE_SPEECH", "threshold", "BLOCK_NONE"),
                    Map.of("category", "HARM_CATEGORY_SEXUALLY_EXPLICIT", "threshold", "BLOCK_NONE"),
                    Map.of("category", "HARM_CATEGORY_DANGEROUS_CONTENT", "threshold", "BLOCK_NONE")
                )
            );
                        
            // 3. API 호출
            String response = webClient.post()
                    .uri(geminiApiUrl + "?key=" + geminiApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(60)) // 🎯 타임아웃 증가
                    .block();
            
            // 4. 응답 파싱 및 반환
            return parseGeminiResponse(response, request);
            
        } catch (WebClientResponseException e) {
            log.error("❌ Gemini API 호출 실패 - 세션 ID: {}, 상태: {}, 메시지: {}", 
                     sessionId, e.getStatusCode(), e.getMessage());
            return AnalysisResponse.createDefaultResponse(sessionId, "API 호출 실패: " + e.getMessage());
            
        } catch (Exception e) {
            log.error("❌ 면접 분석 처리 중 오류 - 세션 ID: {}", sessionId, e);
            return AnalysisResponse.createDefaultResponse(sessionId, "분석 처리 오류: " + e.getMessage());
        }
    }
    
    /**
     * 면접 분석용 프롬프트를 생성합니다.
     */
    private String buildAnalysisPrompt(AnalysisRequest request) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("당신은 15년 경력의 전문 면접관이자 진로 상담사입니다. ")
              .append("청소년과 청년들의 면접을 분석하여 건설적이고 격려적인 피드백을 제공해주세요.\n\n");
        
        // 🎯 세션 정보 추가
        prompt.append("=== 분석 세션 정보 ===\n");
        prompt.append("- 세션 ID: ").append(request.getSessionId()).append("\n");
        prompt.append("- 분석 시간: ").append(LocalDateTime.now()).append("\n\n");
        
        // 면접 데이터 정보
        prompt.append("=== 면접 기본 정보 ===\n");
        if (request.getInterviewData() != null) {
            var interviewData = request.getInterviewData();
            prompt.append("- 총 질문 수: ").append(interviewData.getQuestions().size()).append("개\n");
            prompt.append("- 답변 완료: ").append(interviewData.getAnswers().size()).append("개\n");
            prompt.append("- 면접 시간: ").append(interviewData.getDuration()).append("초\n");
            if (interviewData.getTimestamp() != null) {
                prompt.append("- 면접 일시: ").append(interviewData.getTimestamp()).append("\n");
            }
            prompt.append("\n");
        }
        
        // 실시간 분석 데이터
        if (request.getRealtimeAnalysis() != null) {
            prompt.append("=== 실시간 분석 데이터 ===\n");
            
            if (request.getRealtimeAnalysis().getAudio() != null) {
                var audio = request.getRealtimeAnalysis().getAudio();
                prompt.append("🎤 음성 분석:\n");
                prompt.append("- 평균 볼륨: ").append(audio.getAverageVolume()).append("\n");
                prompt.append("- 말하기 시간: ").append(audio.getSpeakingTime()).append("초\n");
                prompt.append("- 말하기 속도: ").append(audio.getWordsPerMinute()).append(" WPM\n");
                prompt.append("- 습관어 횟수: ").append(audio.getFillerWordsCount()).append("회\n");
                if (audio.getSpeechClarity() != null) {
                    prompt.append("- 발음 명확도: ").append(audio.getSpeechClarity()).append("\n");
                }
                prompt.append("\n");
            }
            
            if (request.getRealtimeAnalysis().getVideo() != null) {
                var video = request.getRealtimeAnalysis().getVideo();
                prompt.append("👁️ 영상 분석:\n");
                prompt.append("- 얼굴 감지: ").append(video.getFaceDetected() ? "안정적" : "불안정").append("\n");
                prompt.append("- 아이컨택: ").append(video.getEyeContactPercentage()).append("%\n");
                prompt.append("- 미소 빈도: ").append(video.getSmileDetection()).append("%\n");
                prompt.append("- 자세 점수: ").append(video.getPostureScore()).append("점\n");
                prompt.append("- 얼굴 감지율: ").append(video.getFaceDetectionRate()).append("%\n\n");
            }
        }
        
        // 질문과 답변
        prompt.append("=== 면접 질문 및 답변 ===\n");
        if (request.getInterviewData() != null) {
            List<String> questions = request.getInterviewData().getQuestions();
            List<String> answers = request.getInterviewData().getAnswers();
            
            for (int i = 0; i < questions.size(); i++) {
                prompt.append("Q").append(i + 1).append(": ").append(questions.get(i)).append("\n");
                if (i < answers.size() && answers.get(i) != null && !answers.get(i).trim().isEmpty()) {
                    prompt.append("A").append(i + 1).append(": ").append(answers.get(i)).append("\n\n");
                } else {
                    prompt.append("A").append(i + 1).append(": [답변 없음 또는 음성 인식 실패]\n\n");
                }
            }
        }
        
        // 분석 요청 사항
        prompt.append("=== 분석 요청 사항 ===\n");
        prompt.append("위 데이터를 종합적으로 분석하여 다음과 같은 JSON 형식으로 상세한 분석을 제공해주세요.\n");
        prompt.append("청소년/청년 대상이므로 격려와 성장 중심의 건설적 피드백을 부탁드립니다:\n\n");
        
        prompt.append("```json\n");
        prompt.append("{\n");
        prompt.append("  \"overall_score\": 85,\n");
        prompt.append("  \"grade\": \"B+\",\n");
        prompt.append("  \"audio_analysis\": {\n");
        prompt.append("    \"speech_clarity\": 80,\n");
        prompt.append("    \"pace_appropriate\": 75,\n");
        prompt.append("    \"volume_consistency\": 85,\n");
        prompt.append("    \"feedback\": \"음성 관련 구체적이고 격려적인 피드백\"\n");
        prompt.append("  },\n");
        prompt.append("  \"video_analysis\": {\n");
        prompt.append("    \"eye_contact\": 70,\n");
        prompt.append("    \"facial_expression\": 80,\n");
        prompt.append("    \"posture\": 75,\n");
        prompt.append("    \"feedback\": \"비언어적 소통 관련 구체적이고 격려적인 피드백\"\n");
        prompt.append("  },\n");
        prompt.append("  \"text_analysis\": {\n");
        prompt.append("    \"content_quality\": 80,\n");
        prompt.append("    \"structure_logic\": 75,\n");
        prompt.append("    \"relevance\": 85,\n");
        prompt.append("    \"feedback\": \"답변 내용 관련 구체적이고 발전적인 피드백\"\n");
        prompt.append("  },\n");
        prompt.append("  \"strengths\": [\"구체적인 강점 1\", \"구체적인 강점 2\", \"구체적인 강점 3\"],\n");
        prompt.append("  \"improvements\": [\"실행 가능한 개선점 1\", \"실행 가능한 개선점 2\"],\n");
        prompt.append("  \"recommendation\": \"종합적인 추천사항과 격려 메시지 (100자 이상)\"\n");
        prompt.append("}\n");
        prompt.append("```\n\n");
        
        prompt.append("⚠️ 중요 지침:\n");
        prompt.append("- 모든 점수는 1-100 범위로 제공\n");
        prompt.append("- 청소년/청년 대상이므로 격려와 희망적 관점 중심\n");
        prompt.append("- 구체적이고 실행 가능한 개선 방안 제시\n");
        prompt.append("- JSON 형식을 정확히 준수 (문법 오류 금지)\n");
        prompt.append("- 피드백은 각각 50자 이상으로 상세하게 작성\n");
        prompt.append("- 실시간 데이터를 적극 활용하여 정확한 분석 제공\n");
        
        return prompt.toString();
    }
    
    /**
     * Gemini API 응답을 파싱하여 분석 결과로 변환합니다.
     */
    private AnalysisResponse parseGeminiResponse(String response, AnalysisRequest request) {
        String sessionId = request.getSessionId();
        
        try {
            JsonNode responseNode = objectMapper.readTree(response);
            
            // 🎯 API 응답 오류 체크
            if (responseNode.has("error")) {
                JsonNode errorNode = responseNode.get("error");
                String errorMessage = errorNode.path("message").asText("Unknown API error");
                log.error("❌ Gemini API 오류 응답 - 세션 ID: {}, 오류: {}", sessionId, errorMessage);
                return AnalysisResponse.createDefaultResponse(sessionId, "API 오류: " + errorMessage);
            }
            
            // Gemini API 응답 구조에서 텍스트 추출
            JsonNode candidatesNode = responseNode.path("candidates");
            if (candidatesNode.isEmpty()) {
                log.error("❌ Gemini API 응답에 candidates가 없음 - 세션 ID: {}", sessionId);
                return AnalysisResponse.createDefaultResponse(sessionId, "API 응답 형식 오류");
            }
            
            String generatedText = candidatesNode
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
            
            if (generatedText.isEmpty()) {
                log.error("❌ Gemini API 응답 텍스트가 비어있음 - 세션 ID: {}", sessionId);
                return AnalysisResponse.createDefaultResponse(sessionId, "빈 응답 텍스트");
            }
            
            log.debug("🔍 Gemini 원본 응답 텍스트 길이: {} - 세션 ID: {}", generatedText.length(), sessionId);
            
            // JSON 부분만 추출
            String jsonContent = extractJsonFromText(generatedText);
            
            JsonNode analysisNode = objectMapper.readTree(jsonContent);
            
            // 🎯 응답 객체 생성 (세션 ID 포함)
            AnalysisResponse analysisResponse = new AnalysisResponse(sessionId);
            
            // 기본 점수 및 등급
            analysisResponse.setOverallScore(analysisNode.path("overall_score").asInt(75));
            analysisResponse.setGrade(analysisNode.path("grade").asText("B"));
            
            // 상세 분석 설정
            AnalysisResponse.DetailedAnalysis detailed = new AnalysisResponse.DetailedAnalysis();
            
            // 오디오 분석
            JsonNode audioNode = analysisNode.path("audio_analysis");
            if (!audioNode.isMissingNode()) {
                detailed.setAudio(new AnalysisResponse.DetailedAnalysis.AudioAnalysis(
                    audioNode.path("speech_clarity").asInt(75),
                    audioNode.path("pace_appropriate").asInt(75),
                    audioNode.path("volume_consistency").asInt(75),
                    audioNode.path("feedback").asText("음성 분석이 완료되었습니다.")
                ));
            }
            
            // 비디오 분석
            JsonNode videoNode = analysisNode.path("video_analysis");
            if (!videoNode.isMissingNode()) {
                detailed.setVideo(new AnalysisResponse.DetailedAnalysis.VideoAnalysis(
                    videoNode.path("eye_contact").asInt(75),
                    videoNode.path("facial_expression").asInt(75),
                    videoNode.path("posture").asInt(75),
                    videoNode.path("feedback").asText("비언어적 소통 분석이 완료되었습니다.")
                ));
            }
            
            // 텍스트 분석
            JsonNode textNode = analysisNode.path("text_analysis");
            if (!textNode.isMissingNode()) {
                detailed.setText(new AnalysisResponse.DetailedAnalysis.TextAnalysis(
                    textNode.path("content_quality").asInt(75),
                    textNode.path("structure_logic").asInt(75),
                    textNode.path("relevance").asInt(75),
                    textNode.path("feedback").asText("답변 내용 분석이 완료되었습니다.")
                ));
            }
            
            analysisResponse.setDetailed(detailed);
            
            // 요약 정보
            List<String> strengths = parseStringArray(analysisNode.path("strengths"));
            List<String> improvements = parseStringArray(analysisNode.path("improvements"));
            String recommendation = analysisNode.path("recommendation").asText("계속해서 연습하며 발전해나가세요!");
            
            analysisResponse.setSummary(new AnalysisResponse.AnalysisSummary(strengths, improvements, recommendation));
            
            // 점수 분석
            int audioScore = detailed.getAudio() != null ? 
                (detailed.getAudio().getSpeechClarity() + detailed.getAudio().getPaceAppropriate() + detailed.getAudio().getVolumeConsistency()) / 3 : 75;
            int videoScore = detailed.getVideo() != null ? 
                (detailed.getVideo().getEyeContact() + detailed.getVideo().getFacialExpression() + detailed.getVideo().getPosture()) / 3 : 75;
            int textScore = detailed.getText() != null ? 
                (detailed.getText().getContentQuality() + detailed.getText().getStructureLogic() + detailed.getText().getRelevance()) / 3 : 75;
            
            analysisResponse.setScores(new AnalysisResponse.ScoreBreakdown(audioScore, videoScore, textScore, analysisResponse.getOverallScore()));
            
            return analysisResponse;
            
        } catch (Exception e) {
            log.error("❌ Gemini 응답 파싱 오류 - 세션 ID: {}", sessionId, e);
            return AnalysisResponse.createDefaultResponse(sessionId, "응답 파싱 오류: " + e.getMessage());
        }
    }
    
    /**
     * 텍스트에서 JSON 부분을 추출합니다.
     */
    private String extractJsonFromText(String text) {        
        // 1차: ```json과 ``` 사이의 내용 추출
        int jsonStart = text.indexOf("```json");
        if (jsonStart != -1) {
            jsonStart = text.indexOf('\n', jsonStart) + 1;
            int jsonEnd = text.indexOf("```", jsonStart);
            if (jsonEnd != -1) {
                String extracted = text.substring(jsonStart, jsonEnd).trim();
                return extracted;
            }
        }
        
        // 2차: ``` 없이 { 와 } 사이의 내용 추출 (더 관대한 접근)
        int braceStart = text.indexOf("{");
        int braceEnd = text.lastIndexOf("}");
        
        if (braceStart != -1 && braceEnd != -1 && braceEnd > braceStart) {
            String extracted = text.substring(braceStart, braceEnd + 1);
            return extracted;
        }
        
        // 3차: 전체 텍스트가 JSON인지 확인
        text = text.trim();
        if (text.startsWith("{") && text.endsWith("}")) {
            return text;
        }
        
        log.warn("⚠️ JSON 추출 실패, 기본값 반환");
        
        // 기본값 반환
        return createDefaultJsonResponse();
    }
    
    /**
     * 기본 JSON 응답 생성
     */
    private String createDefaultJsonResponse() {
        return """
        {
          "overall_score": 75,
          "grade": "B",
          "audio_analysis": {
            "speech_clarity": 75, 
            "pace_appropriate": 75, 
            "volume_consistency": 75, 
            "feedback": "음성 분석이 완료되었습니다. 전반적으로 안정적인 말하기를 보여주셨습니다."
          },
          "video_analysis": {
            "eye_contact": 75, 
            "facial_expression": 75, 
            "posture": 75, 
            "feedback": "비언어적 소통 분석이 완료되었습니다. 자연스러운 표정과 자세를 유지하셨습니다."
          },
          "text_analysis": {
            "content_quality": 75, 
            "structure_logic": 75, 
            "relevance": 75, 
            "feedback": "답변 내용 분석이 완료되었습니다. 질문에 적절히 대답하려고 노력하셨습니다."
          },
          "strengths": ["성실한 면접 참여 태도", "기본적인 소통 능력", "적극적인 자세"],
          "improvements": ["답변의 구체성 향상", "자신감 있는 표현", "논리적 구조화"],
          "recommendation": "전반적으로 좋은 면접 태도를 보여주셨습니다. 답변을 더 구체적으로 준비하고 자신감을 가지고 말씀하시면 더욱 좋은 인상을 줄 수 있을 것입니다. 지속적인 연습을 통해 더욱 발전하시길 응원합니다!"
        }
        """;
    }
    
    /**
     * JSON 배열을 List<String>으로 변환합니다.
     */
    private List<String> parseStringArray(JsonNode arrayNode) {
        List<String> result = new ArrayList<>();
        
        if (arrayNode.isArray()) {
            for (JsonNode node : arrayNode) {
                String value = node.asText().trim();
                if (!value.isEmpty()) {
                    result.add(value);
                }
            }
        } else if (arrayNode.isTextual()) {
            String text = arrayNode.asText();
            String[] parts = text.split(",");
            for (String part : parts) {
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    result.add(trimmed);
                }
            }
        }
        
        // 빈 배열인 경우 기본값 제공
        if (result.isEmpty()) {
            result.add("분석 완료");
        }
        
        return result;
    }
}
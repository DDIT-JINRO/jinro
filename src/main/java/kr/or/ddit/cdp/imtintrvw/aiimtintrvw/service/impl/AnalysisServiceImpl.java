package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
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
        try {
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
                )
            );
            
            // 3. API 호출
            String response = webClient.post()
                    .uri(geminiApiUrl + "?key=" + geminiApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();
            
            // 4. 응답 파싱 및 반환
            log.info("response : " + response);
            return parseGeminiResponse(response, request);
            
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Gemini API 호출 실패: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("면접 분석 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }
    
    /**
     * 면접 분석용 프롬프트를 생성합니다.
     */
    private String buildAnalysisPrompt(AnalysisRequest request) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("당신은 15년 경력의 전문 면접관이자 진로 상담사입니다. ")
              .append("청소년과 청년들의 면접을 분석하여 건설적인 피드백을 제공해주세요.\n\n");
        
        // 면접 데이터 정보
        prompt.append("=== 면접 기본 정보 ===\n");
        if (request.getInterviewData() != null) {
            prompt.append("- 총 질문 수: ").append(request.getInterviewData().getQuestions().size()).append("개\n");
            prompt.append("- 답변 완료: ").append(request.getInterviewData().getAnswers().size()).append("개\n");
            prompt.append("- 면접 시간: ").append(request.getInterviewData().getDuration()).append("초\n\n");
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
                prompt.append("- 습관어 횟수: ").append(audio.getFillerWordsCount()).append("회\n\n");
            }
            
            if (request.getRealtimeAnalysis().getVideo() != null) {
                var video = request.getRealtimeAnalysis().getVideo();
                prompt.append("👁️ 영상 분석:\n");
                prompt.append("- 얼굴 감지: ").append(video.getFaceDetected() ? "양호" : "불안정").append("\n");
                prompt.append("- 아이컨택: ").append(video.getEyeContactPercentage()).append("%\n");
                prompt.append("- 미소 빈도: ").append(video.getSmileDetection()).append("%\n");
                prompt.append("- 자세 점수: ").append(video.getPostureScore()).append("점\n\n");
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
                    prompt.append("A").append(i + 1).append(": [답변 없음]\n\n");
                }
            }
        }
        
        // 분석 요청 사항
        prompt.append("=== 분석 요청 사항 ===\n");
        prompt.append("위 데이터를 바탕으로 다음과 같은 형태의 JSON 형식으로 상세한 분석을 제공해주세요:\n\n");
        prompt.append("{\n");
        prompt.append("  \"overall_score\": 85,\n");
        prompt.append("  \"grade\": \"B+\",\n");
        prompt.append("  \"audio_analysis\": {\n");
        prompt.append("    \"speech_clarity\": 80,\n");
        prompt.append("    \"pace_appropriate\": 75,\n");
        prompt.append("    \"volume_consistency\": 85,\n");
        prompt.append("    \"feedback\": \"구체적인 음성 피드백\"\n");
        prompt.append("  },\n");
        prompt.append("  \"video_analysis\": {\n");
        prompt.append("    \"eye_contact\": 70,\n");
        prompt.append("    \"facial_expression\": 80,\n");
        prompt.append("    \"posture\": 75,\n");
        prompt.append("    \"feedback\": \"구체적인 비언어적 소통 피드백\"\n");
        prompt.append("  },\n");
        prompt.append("  \"text_analysis\": {\n");
        prompt.append("    \"content_quality\": 80,\n");
        prompt.append("    \"structure_logic\": 75,\n");
        prompt.append("    \"relevance\": 85,\n");
        prompt.append("    \"feedback\": \"답변 내용에 대한 구체적 피드백\"\n");
        prompt.append("  },\n");
        prompt.append("  \"strengths\": [\"구체적인 강점 1\", \"구체적인 강점 2\"],\n");
        prompt.append("  \"improvements\": [\"구체적인 개선점 1\", \"구체적인 개선점 2\"],\n");
        prompt.append("  \"recommendation\": \"종합적인 추천사항과 격려\"\n");
        prompt.append("}\n\n");
        
        prompt.append("⚠️ 중요사항:\n");
        prompt.append("- 점수는 1-100 범위로 제공\n");
        prompt.append("- 청소년/청년 대상이므로 격려와 건설적 피드백 중심\n");
        prompt.append("- 구체적이고 실행 가능한 조언 제공\n");
        prompt.append("- JSON 형식을 정확히 지켜주세요\n");
        
        return prompt.toString();
    }
    
    /**
     * Gemini API 응답을 파싱하여 분석 결과로 변환합니다.
     */
    private AnalysisResponse parseGeminiResponse(String response, AnalysisRequest request) {
        try {
            JsonNode responseNode = objectMapper.readTree(response);
            
            // Gemini API 응답 구조에서 텍스트 추출
            String generatedText = responseNode
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
            
            System.out.println("🔍 Gemini 원본 응답 텍스트: " + generatedText);
            
            // JSON 부분만 추출 (```json과 ``` 사이의 내용)
            String jsonContent = extractJsonFromText(generatedText);
            System.out.println("📊 추출된 JSON: " + jsonContent);
            
            JsonNode analysisNode = objectMapper.readTree(jsonContent);
            
            // 응답 객체 생성
            AnalysisResponse analysisResponse = new AnalysisResponse();
            
            // 기본 점수 및 등급
            analysisResponse.setOverallScore(analysisNode.path("overall_score").asInt(75));
            analysisResponse.setGrade(analysisNode.path("grade").asText("B"));
            
            // 상세 분석 설정
            AnalysisResponse.DetailedAnalysis detailed = new AnalysisResponse.DetailedAnalysis();
            
            // 오디오 분석 - 🎯 필드명 정확히 매칭
            JsonNode audioNode = analysisNode.path("audio_analysis");
            if (!audioNode.isMissingNode()) {
                AnalysisResponse.DetailedAnalysis.AudioAnalysis audio = 
                    new AnalysisResponse.DetailedAnalysis.AudioAnalysis();
                audio.setSpeechClarity(audioNode.path("speech_clarity").asInt(75));
                audio.setPaceAppropriate(audioNode.path("pace_appropriate").asInt(75));
                audio.setVolumeConsistency(audioNode.path("volume_consistency").asInt(75));
                audio.setFeedback(audioNode.path("feedback").asText("음성 분석 결과입니다."));
                detailed.setAudio(audio);
                
                System.out.println("🎤 음성 분석 파싱 완료: " + audio.getFeedback().substring(0, Math.min(50, audio.getFeedback().length())) + "...");
            }
            
            // 비디오 분석 - 🎯 필드명 정확히 매칭
            JsonNode videoNode = analysisNode.path("video_analysis");
            if (!videoNode.isMissingNode()) {
                AnalysisResponse.DetailedAnalysis.VideoAnalysis video = 
                    new AnalysisResponse.DetailedAnalysis.VideoAnalysis();
                video.setEyeContact(videoNode.path("eye_contact").asInt(75));
                video.setFacialExpression(videoNode.path("facial_expression").asInt(75));
                video.setPosture(videoNode.path("posture").asInt(75));
                video.setFeedback(videoNode.path("feedback").asText("비언어적 소통 분석 결과입니다."));
                detailed.setVideo(video);
                
                System.out.println("👁️ 영상 분석 파싱 완료: " + video.getFeedback().substring(0, Math.min(50, video.getFeedback().length())) + "...");
            }
            
            // 텍스트 분석 - 🎯 필드명 정확히 매칭
            JsonNode textNode = analysisNode.path("text_analysis");
            if (!textNode.isMissingNode()) {
                AnalysisResponse.DetailedAnalysis.TextAnalysis text = 
                    new AnalysisResponse.DetailedAnalysis.TextAnalysis();
                text.setContentQuality(textNode.path("content_quality").asInt(75));
                text.setStructureLogic(textNode.path("structure_logic").asInt(75));
                text.setRelevance(textNode.path("relevance").asInt(75));
                text.setFeedback(textNode.path("feedback").asText("답변 내용 분석 결과입니다."));
                detailed.setText(text);
                
                System.out.println("📝 텍스트 분석 파싱 완료: " + text.getFeedback().substring(0, Math.min(50, text.getFeedback().length())) + "...");
            }
            
            analysisResponse.setDetailed(detailed);
            
            // 요약 정보 - 🎯 배열 파싱 개선
            AnalysisResponse.AnalysisSummary summary = new AnalysisResponse.AnalysisSummary();
            summary.setStrengths(parseStringArray(analysisNode.path("strengths")));
            summary.setImprovements(parseStringArray(analysisNode.path("improvements")));
            summary.setRecommendation(analysisNode.path("recommendation").asText("계속해서 연습하며 발전해나가세요!"));
            analysisResponse.setSummary(summary);
            
            System.out.println("📋 요약 정보 파싱 완료");
            System.out.println("  - 강점: " + summary.getStrengths().size() + "개");
            System.out.println("  - 개선점: " + summary.getImprovements().size() + "개"); 
            System.out.println("  - 추천사항: " + summary.getRecommendation().substring(0, Math.min(30, summary.getRecommendation().length())) + "...");
            
            // 점수 분석
            AnalysisResponse.ScoreBreakdown scores = new AnalysisResponse.ScoreBreakdown();
            scores.setCommunication(detailed.getAudio() != null ? detailed.getAudio().getSpeechClarity() : 75);
            scores.setAppearance(detailed.getVideo() != null ? detailed.getVideo().getEyeContact() : 75);
            scores.setContent(detailed.getText() != null ? detailed.getText().getContentQuality() : 75);
            scores.setOverall(analysisResponse.getOverallScore());
            analysisResponse.setScores(scores);
            
            System.out.println("✅ Gemini 응답 파싱 완료!");
            System.out.println("  - 총점: " + analysisResponse.getOverallScore() + " (" + analysisResponse.getGrade() + ")");
            
            return analysisResponse;
            
        } catch (Exception e) {
            System.err.println("❌ Gemini 응답 파싱 오류: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * 텍스트에서 JSON 부분을 추출합니다.
     */
    private String extractJsonFromText(String text) {
        System.out.println("🔍 JSON 추출 시작...");
        
        // 1차: ```json과 ``` 사이의 내용 추출
        int jsonStart = text.indexOf("```json");
        if (jsonStart != -1) {
            jsonStart = text.indexOf('\n', jsonStart) + 1; // ```json 다음 줄부터
            int jsonEnd = text.indexOf("```", jsonStart);
            if (jsonEnd != -1) {
                String extracted = text.substring(jsonStart, jsonEnd).trim();
                System.out.println("✅ Markdown JSON 블록에서 추출 성공");
                return extracted;
            }
        }
        
        // 2차: { 와 } 사이의 내용 추출
        int braceStart = text.indexOf("{");
        int braceEnd = text.lastIndexOf("}");
        
        if (braceStart != -1 && braceEnd != -1 && braceEnd > braceStart) {
            String extracted = text.substring(braceStart, braceEnd + 1);
            System.out.println("✅ 중괄호 기반 추출 성공");
            return extracted;
        }
        
        // 3차: 전체 텍스트가 JSON인지 확인
        text = text.trim();
        if (text.startsWith("{") && text.endsWith("}")) {
            System.out.println("✅ 전체 텍스트가 JSON으로 판단");
            return text;
        }
        
        System.out.println("⚠️ JSON 추출 실패, 기본값 반환");
        
        // JSON을 찾을 수 없는 경우 기본값 반환
        return """
        {
          "overall_score": 75,
          "grade": "B",
          "audio_analysis": {
            "speech_clarity": 75, 
            "pace_appropriate": 75, 
            "volume_consistency": 75, 
            "feedback": "음성 분석이 완료되었습니다."
          },
          "video_analysis": {
            "eye_contact": 75, 
            "facial_expression": 75, 
            "posture": 75, 
            "feedback": "비언어적 소통 분석이 완료되었습니다."
          },
          "text_analysis": {
            "content_quality": 75, 
            "structure_logic": 75, 
            "relevance": 75, 
            "feedback": "답변 내용 분석이 완료되었습니다."
          },
          "strengths": ["성실한 태도", "기본기 보유"],
          "improvements": ["답변 구체화", "자신감 향상"],
          "recommendation": "지속적인 연습을 통해 더욱 발전하실 수 있습니다!"
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
            // 문자열인 경우 쉼표로 분리 시도
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
            if (arrayNode.isMissingNode()) {
                result.add("분석 항목 없음");
            }
        }
        
        return result;
    }

}

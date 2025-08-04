package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.AnalysisService;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisRequest;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class InterviewAnalysisController {

    private final AnalysisService analysisService;
    
    // 🎯 진행률 추적을 위한 메모리 저장소 (실제로는 Redis 권장)
    private final Map<String, Integer> analysisProgress = new ConcurrentHashMap<>();
    private final Map<String, Boolean> activeSessions = new ConcurrentHashMap<>();

    public InterviewAnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    /**
     * 🎯 면접 분석 메인 엔드포인트 (프론트엔드 연동)
     */
    @PostMapping("/analyze-interview") // 🎯 엔드포인트 변경
    public ResponseEntity<?> analyzeInterview(@RequestBody Map<String, Object> requestData) {
        String sessionId = null;
        
        try {            
            // 세션 ID 추출
            sessionId = (String) requestData.get("sessionId");
            if (sessionId == null || sessionId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", "Invalid session ID",
                    "message", "세션 ID가 필요합니다."
                ));
            }
                        
            // 세션 활성화
            activeSessions.put(sessionId, true);
            updateProgress(sessionId, 5);
            
            // 요청 데이터 검증
            if (!isValidRequest(requestData)) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", "Invalid request data", 
                    "message", "필수 데이터가 누락되었습니다.",
                    "sessionId", sessionId
                ));
            }
            
            updateProgress(sessionId, 15);
            
            // AnalysisRequest 객체로 변환
            AnalysisRequest analysisRequest = convertToAnalysisRequest(requestData);
            
            updateProgress(sessionId, 25);
            
            // 분석 실행
            AnalysisResponse analysisResult = analysisService.analyzeInterview(analysisRequest);
            
            updateProgress(sessionId, 100);
            
            // 🎯 프론트엔드 형식에 맞는 응답 구성
            Map<String, Object> response = Map.of(
                "success", true,
                "sessionId", sessionId,
                "timestamp", LocalDateTime.now().toString(),
                "overallScore", analysisResult.getOverallScore(),
                "grade", analysisResult.getGrade(),
                "analysisMethod", "Gemini AI Expert Analysis",
                "detailed", Map.of(
                    "audio", Map.of(
                        "speechClarity", analysisResult.getDetailed().getAudio().getSpeechClarity(),
                        "paceAppropriate", analysisResult.getDetailed().getAudio().getPaceAppropriate(),
                        "volumeConsistency", analysisResult.getDetailed().getAudio().getVolumeConsistency(),
                        "feedback", analysisResult.getDetailed().getAudio().getFeedback()
                    ),
                    "video", Map.of(
                        "eyeContact", analysisResult.getDetailed().getVideo().getEyeContact(),
                        "facialExpression", analysisResult.getDetailed().getVideo().getFacialExpression(),
                        "posture", analysisResult.getDetailed().getVideo().getPosture(),
                        "feedback", analysisResult.getDetailed().getVideo().getFeedback()
                    ),
                    "text", Map.of(
                        "contentQuality", analysisResult.getDetailed().getText().getContentQuality(),
                        "structureLogic", analysisResult.getDetailed().getText().getStructureLogic(),
                        "relevance", analysisResult.getDetailed().getText().getRelevance(),
                        "feedback", analysisResult.getDetailed().getText().getFeedback()
                    )
                ),
                "summary", Map.of(
                    "strengths", analysisResult.getSummary().getStrengths(),
                    "improvements", analysisResult.getSummary().getImprovements(),
                    "recommendation", analysisResult.getSummary().getRecommendation()
                ),
                "scores", Map.of(
                    "communication", analysisResult.getScores().getCommunication(),
                    "appearance", analysisResult.getScores().getAppearance(),
                    "content", analysisResult.getScores().getContent(),
                    "overall", analysisResult.getScores().getOverall()
                )
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ 분석 실패 - 세션 ID: {}, 오류: {}", sessionId, e.getMessage(), e);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "sessionId", sessionId != null ? sessionId : "unknown",
                "error", "Analysis failed",
                "message", "면접 분석 중 오류가 발생했습니다: " + e.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
        } finally {
            // 정리 작업
            if (sessionId != null) {
                activeSessions.remove(sessionId);
            }
        }
    }

    /**
     * 🎯 분석 진행 상태 확인
     */
    @GetMapping("/analyze-interview/progress/{sessionId}")
    public ResponseEntity<?> getAnalysisProgress(@PathVariable String sessionId) {
        try {
            int progress = analysisProgress.getOrDefault(sessionId, 0);
            String status = progress >= 100 ? "completed" : "processing";
            String message = getProgressMessage(progress);
            
            return ResponseEntity.ok(Map.of(
                "sessionId", sessionId,
                "progress", progress,
                "status", status,
                "message", message,
                "timestamp", LocalDateTime.now().toString()
            ));
            
        } catch (Exception e) {
            log.error("❌ 진행 상태 확인 실패 - 세션 ID: {}", sessionId, e);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "sessionId", sessionId,
                "progress", 0,
                "status", "error",
                "message", "진행 상태 확인 실패: " + e.getMessage()
            ));
        }
    }

    /**
     * 🎯 분석 취소
     */
    @PostMapping("/analyze-interview/cancel/{sessionId}")
    public ResponseEntity<?> cancelAnalysis(@PathVariable String sessionId) {
        try {
            boolean wasActive = activeSessions.containsKey(sessionId);
            
            // 세션 정리
            activeSessions.remove(sessionId);
            analysisProgress.remove(sessionId);
                        
            return ResponseEntity.ok(Map.of(
                "success", true,
                "sessionId", sessionId,
                "message", wasActive ? "분석이 취소되었습니다." : "취소할 분석을 찾을 수 없습니다.",
                "wasActive", wasActive
            ));
            
        } catch (Exception e) {
            log.error("❌ 분석 취소 실패 - 세션 ID: {}", sessionId, e);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "sessionId", sessionId,
                "message", "취소 처리 실패: " + e.getMessage()
            ));
        }
    }

    /**
     * 🎯 API 상태 확인
     */
    @GetMapping("/analyze-interview/health")
    public ResponseEntity<?> healthCheck() {
        try {
            // 서비스 상태 확인
            boolean isHealthy = true; // analysisService 상태 확인 로직 추가 가능
            
            Map<String, Object> health = Map.of(
                "status", isHealthy ? "OK" : "ERROR",
                "message", isHealthy ? "서비스가 정상 작동 중입니다." : "서비스에 문제가 있습니다.",
                "timestamp", LocalDateTime.now().toString(),
                "version", "1.0.0",
                "aiEngine", "Gemini Pro",
                "activeAnalyses", activeSessions.size()
            );
            
            return ResponseEntity.ok(health);
            
        } catch (Exception e) {
            log.error("❌ Health check 실패", e);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "status", "ERROR",
                "message", "Health check 실패: " + e.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
        }
    }

    /**
     * 요청 데이터 유효성 검사
     */
    private boolean isValidRequest(Map<String, Object> requestData) {
        if (requestData == null) return false;
        if (!requestData.containsKey("sessionId")) return false;
        if (!requestData.containsKey("interview_data")) return false;
        
        Map<String, Object> interviewData = (Map<String, Object>) requestData.get("interview_data");
        if (interviewData == null) return false;
        if (!interviewData.containsKey("questions") || !interviewData.containsKey("answers")) return false;
        
        return true;
    }

    /**
     * Map을 AnalysisRequest 객체로 변환
     */
    private AnalysisRequest convertToAnalysisRequest(Map<String, Object> requestData) {
        AnalysisRequest request = new AnalysisRequest();
        
        // Interview Data 설정
        Map<String, Object> interviewDataMap = (Map<String, Object>) requestData.get("interview_data");
        if (interviewDataMap != null) {
            AnalysisRequest.InterviewData interviewData = new AnalysisRequest.InterviewData();
            interviewData.setQuestions((List) interviewDataMap.get("questions"));
            interviewData.setAnswers((List<String>) interviewDataMap.get("answers"));
            interviewData.setDuration(((Number) interviewDataMap.getOrDefault("duration", 0)).intValue());
            interviewData.setSessionId((String) requestData.get("sessionId"));
            request.setInterviewData(interviewData);
        }
        
        // Realtime Analysis 설정
        Map<String, Object> realtimeMap = (Map<String, Object>) requestData.get("realtime_analysis");
        if (realtimeMap != null) {
            AnalysisRequest.RealtimeAnalysis realtimeAnalysis = new AnalysisRequest.RealtimeAnalysis();
            
            // Audio Data
            Map<String, Object> audioMap = (Map<String, Object>) realtimeMap.get("audio");
            if (audioMap != null) {
                AnalysisRequest.RealtimeAnalysis.AudioData audioData = new AnalysisRequest.RealtimeAnalysis.AudioData();
                audioData.setAverageVolume(((Number) audioMap.getOrDefault("averageVolume", 0.0)).doubleValue());
                audioData.setSpeakingTime(((Number) audioMap.getOrDefault("speakingTime", 0)).intValue());
                audioData.setWordsPerMinute(((Number) audioMap.getOrDefault("wordsPerMinute", 0)).intValue());
                audioData.setFillerWordsCount(((Number) audioMap.getOrDefault("fillerWordsCount", 0)).intValue());
                realtimeAnalysis.setAudio(audioData);
            }
            
            // Video Data
            Map<String, Object> videoMap = (Map<String, Object>) realtimeMap.get("video");
            if (videoMap != null) {
                AnalysisRequest.RealtimeAnalysis.VideoData videoData = new AnalysisRequest.RealtimeAnalysis.VideoData();
                videoData.setFaceDetected((Boolean) videoMap.getOrDefault("faceDetected", false));
                videoData.setEyeContactPercentage(((Number) videoMap.getOrDefault("eyeContactPercentage", 0.0)).doubleValue());
                videoData.setSmileDetection(((Number) videoMap.getOrDefault("smileDetection", 0.0)).doubleValue());
                videoData.setPostureScore(((Number) videoMap.getOrDefault("postureScore", 0.0)).doubleValue());
                videoData.setFaceDetectionRate(((Number) videoMap.getOrDefault("faceDetectionRate", 0.0)).doubleValue());
                realtimeAnalysis.setVideo(videoData);
            }
            
            request.setRealtimeAnalysis(realtimeAnalysis);
        }
        
        return request;
    }

    /**
     * 진행률 업데이트
     */
    private void updateProgress(String sessionId, int progress) {
        analysisProgress.put(sessionId, progress);
    }

    /**
     * 진행률에 따른 메시지 반환
     */
    private String getProgressMessage(int progress) {
        if (progress < 10) return "분석 준비 중...";
        if (progress < 25) return "데이터 검증 중...";
        if (progress < 40) return "영상 데이터 처리 중...";
        if (progress < 60) return "음성 분석 중...";
        if (progress < 80) return "답변 내용 분석 중...";
        if (progress < 95) return "종합 분석 중...";
        if (progress < 100) return "결과 생성 중...";
        return "분석 완료!";
    }
}
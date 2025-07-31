package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.AnalysisService;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisRequest;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisResponse;

@RestController
@RequestMapping("/api/interview")
public class InterviewAnalysisController {
	
	@Autowired
	private final AnalysisService analysisService;
	
	public InterviewAnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }
	
	/**
     * 상세 AI 분석 결과를 제공합니다.
     * 
     * @param request 면접 데이터 및 실시간 분석 결과
     * @return Gemini AI의 상세 분석 결과
     */
    @PostMapping("/detailed-analysis")
    public ResponseEntity<?> getDetailedAnalysis(@RequestBody AnalysisRequest request) {
        try {
            // 입력 유효성 검사
            if (request.getInterviewData() == null || 
                request.getInterviewData().getQuestions() == null ||
                request.getInterviewData().getQuestions().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "면접 질문 데이터가 필요합니다."));
            }
            
            // Gemini API 호출하여 분석 수행
            AnalysisResponse analysis = analysisService.analyzeInterview(request);
            
            return ResponseEntity.ok(analysis);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                        "error", "분석 처리 중 오류가 발생했습니다.",
                        "message", e.getMessage()
                    ));
        }
    }
    
    /**
     * API 상태 확인용 엔드포인트
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "OK",
            "service", "Interview Analysis API",
            "timestamp", java.time.LocalDateTime.now().toString()
        ));
    }
}

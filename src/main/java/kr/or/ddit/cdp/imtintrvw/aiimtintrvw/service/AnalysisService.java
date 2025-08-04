package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service;

import java.util.Map;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisRequest;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisResponse;

public interface AnalysisService {

    /**
     * 🎯 면접 분석 메인 메서드
     */
    AnalysisResponse analyzeInterview(AnalysisRequest request);
    
    /**
     * 🎯 Map 형태의 요청 데이터로 면접 분석 수행
     */
    AnalysisResponse analyzeInterviewFromMap(Map<String, Object> requestData);
    
    /**
     * 🎯 요청 데이터 유효성 검증
     */
    boolean validateRequest(Map<String, Object> requestData);
    
    /**
     * 🎯 Map을 AnalysisRequest 객체로 변환
     */
    AnalysisRequest convertToAnalysisRequest(Map<String, Object> requestData);
    
    /**
     * 🎯 세션 활성화
     */
    void activateSession(String sessionId);
    
    /**
     * 🎯 세션 비활성화
     */
    void deactivateSession(String sessionId);
    
    /**
     * 🎯 분석 진행률 업데이트
     */
    void updateProgress(String sessionId, int progress);
    
    /**
     * 🎯 분석 진행률 조회
     */
    Map<String, Object> getAnalysisProgress(String sessionId);
    
    /**
     * 🎯 분석 취소
     */
    Map<String, Object> cancelAnalysis(String sessionId);
    
    /**
     * 🎯 서비스 상태 확인
     */
    Map<String, Object> getHealthStatus();
    
    /**
     * 🎯 세션 활성 상태 확인
     */
    boolean isSessionActive(String sessionId);
}
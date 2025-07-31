package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

public class AnalysisRequest {
	@JsonProperty("interview_data")
    private InterviewData interviewData;
    
    @JsonProperty("realtime_analysis")
    private RealtimeAnalysis realtimeAnalysis;
    
    public InterviewData getInterviewData() { return interviewData; }
    public void setInterviewData(InterviewData interviewData) { this.interviewData = interviewData; }
    
    public RealtimeAnalysis getRealtimeAnalysis() { return realtimeAnalysis; }
    public void setRealtimeAnalysis(RealtimeAnalysis realtimeAnalysis) { this.realtimeAnalysis = realtimeAnalysis; }
    
    @Data
    @NoArgsConstructor
    public static class InterviewData {
        private List<String> questions;
        private List<String> answers;
        private int duration;
        private String sessionId;
    }
    
    @Data
    @NoArgsConstructor
    public static class RealtimeAnalysis {
        private AudioData audio;
        private VideoData video;
        
        @Data
        public static class AudioData {
            private Double averageVolume;
            private Integer speakingTime;
            private Integer wordsPerMinute;
            private Integer fillerWordsCount;
        }
        
        
        @Data
        public static class VideoData {
            private Boolean faceDetected;
            private Double eyeContactPercentage;
            private Double smileDetection;
            private Double postureScore;
            private Double faceDetectionRate;
        }
    }
}

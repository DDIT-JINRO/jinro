package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalysisResponse {
	private Integer overallScore;
    private String grade;
    private LocalDateTime timestamp;
    private String analysisMethod;
    private DetailedAnalysis detailed;
    private AnalysisSummary summary;
    private ScoreBreakdown scores;
    
    @Data
    public static class DetailedAnalysis {
        private AudioAnalysis audio;
        private VideoAnalysis video;
        private TextAnalysis text;
        
        @Data
        public static class AudioAnalysis {
            private Integer speechClarity;
            private Integer paceAppropriate;
            private Integer volumeConsistency;
            private String feedback;
        }
        
        @Data
        public static class VideoAnalysis {
            private Integer eyeContact;
            private Integer facialExpression;
            private Integer posture;
            private String feedback;
        }
        
        @Data
        public static class TextAnalysis {
            private Integer contentQuality;
            private Integer structureLogic;
            private Integer relevance;
            private String feedback;
        }
    }
    
    @Data
    public static class AnalysisSummary {
        private List<String> strengths;
        private List<String> improvements;
        private String recommendation;
    }
    
    @Data
    public static class ScoreBreakdown {
        private Integer communication;
        private Integer appearance;
        private Integer content;
        private Integer overall;
    }
}

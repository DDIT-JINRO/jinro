package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalysisRequest {

	// 🎯 세션 ID 추가
	@JsonProperty("sessionId")
	private String sessionId;

	@JsonProperty("interview_data")
	private InterviewData interviewData;

	@JsonProperty("realtime_analysis")
	private RealtimeAnalysis realtimeAnalysis;

	@Data
	@NoArgsConstructor
	public static class InterviewData {
		private List<String> questions;
		private List<String> answers;
		private int duration;

		// 🎯 timestamp 필드 추가 (프론트엔드에서 전송)
		private String timestamp;

		// sessionId는 상위 레벨로 이동
		private String sessionId;
	}

	@Data
	@NoArgsConstructor
	public static class RealtimeAnalysis {
		private AudioData audio;
		private VideoData video;

		// 🎯 메타데이터 추가 (선택사항)
		private MetaData metadata;

		@Data
		@NoArgsConstructor
		public static class AudioData {
			private Double averageVolume;
			private Integer speakingTime;
			private Integer wordsPerMinute;
			private Integer fillerWordsCount;

			// 🎯 추가 음성 분석 필드들
			private Double speechClarity;
			private Double noiseLevel;
			private Double pauseFrequency;
		}

		@Data
		@NoArgsConstructor
		public static class VideoData {
			private Boolean faceDetected;
			private Double eyeContactPercentage;
			private Double smileDetection;
			private Double postureScore;
			private Double faceDetectionRate;

			// 🎯 추가 영상 분석 필드들
			private Object emotionAnalysis; // Map 형태로 감정 분석 결과
			private Double lightingQuality;
			private Double headMovementStability;
		}

		@Data
		@NoArgsConstructor
		public static class MetaData {
			private String browserInfo;
			private String deviceType;
			private String analysisStartTime;
			private String clientTimezone;
		}
	}
}
package kr.or.ddit.ertds.hgschl.service; 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * 카카오 주소-좌표 변환 API의 JSON 응답을 매핑하기 위한 DTO 클래스입니다.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // JSON의 모든 필드를 매핑할 필요 없이, 필요한 필드만 선언
public class KakaoGeocodingResponse {
    
    @JsonProperty("documents")
    private List<Document> documents;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {

        @JsonProperty("x") // JSON 필드명 "x"를 'x' 변수에 매핑
        private String x; // 경도(Longitude)

        @JsonProperty("y") // JSON 필드명 "y"를 'y' 변수에 매핑
        private String y; // 위도(Latitude)
    }
}
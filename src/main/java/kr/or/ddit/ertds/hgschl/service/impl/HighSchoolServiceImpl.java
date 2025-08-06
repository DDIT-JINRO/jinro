package kr.or.ddit.ertds.hgschl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.ertds.hgschl.service.HighSchoolDeptVO;
import kr.or.ddit.ertds.hgschl.service.HighSchoolService;
import kr.or.ddit.ertds.hgschl.service.HighSchoolVO;
import kr.or.ddit.ertds.hgschl.service.KakaoGeocodingResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HighSchoolServiceImpl implements HighSchoolService {

	@Autowired
	private HighSchoolMapper highSchoolMapper;

	// 모든 고등학교 리스트
	@Override
	public List<HighSchoolVO> highSchoolList(HighSchoolVO highSchoolVO) {

		return highSchoolMapper.highSchoolList(highSchoolVO);
	}

	// 고등학교 상세
	@Override
	public HighSchoolVO highSchoolDetail(int hsId) {

		return highSchoolMapper.highSchoolDetail(hsId);
	}

	// 검색 결과 갯수
	@Override
	public int selectHighSchoolCount(HighSchoolVO highSchoolVO) {

		return highSchoolMapper.selectHighSchoolCount(highSchoolVO);
	}

	//지역 필터 옵션 목록 조회
	@Override
	public List<ComCodeVO> selectRegionList() {

		return highSchoolMapper.selectRegionList();
	}

    //학교 유형 필터 옵션 목록 조회
	@Override
	public List<ComCodeVO> selectSchoolTypeList() {

		return highSchoolMapper.selectSchoolTypeList();
	}

    //공학 여부 필터 옵션 목록 조회
	@Override
	public List<ComCodeVO> selectCoedTypeList() {
	
		return highSchoolMapper.selectCoedTypeList();
	}

	//특정 고등학교의 학과 목록 조회
	@Override
	public List<HighSchoolDeptVO> selectDeptsBySchoolId(int hsId) {
		
		return highSchoolMapper.selectDeptsBySchoolId(hsId);
	}

	@Override
	public int highSchoolDelete(int hsId) {

		return highSchoolMapper.highSchoolDelete(hsId);
	}

	@Override
	public int highSchoolDeptDelete(int hsdId) {

		return highSchoolMapper.highSchoolDeptDelete(hsdId);
	}
	
	 // 학교 객체 하나에 대한 좌표를 조회하고 설정하는 private 메소드
    private void fetchAndSetCoordinates(HighSchoolVO school) {
        String kakaoRestApiKey = "여기에_카카오_REST_API_키를_입력하세요";
        String address = school.getHsAddr();
        
        if (address == null || address.trim().isEmpty()) {
            log.warn("주소가 없어 좌표를 얻을 수 없습니다: {}", school.getHsName());
            return;
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();
            
            String geocodingApiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + java.net.URLEncoder.encode(address, "UTF-8");

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(geocodingApiUrl, HttpMethod.GET, entity, String.class);
            
            KakaoGeocodingResponse kakaoResponse = mapper.readValue(responseEntity.getBody(), KakaoGeocodingResponse.class);

            if (kakaoResponse != null && kakaoResponse.getDocuments() != null && !kakaoResponse.getDocuments().isEmpty()) {
                KakaoGeocodingResponse.Document doc = kakaoResponse.getDocuments().get(0);
                school.setHsLat(Double.parseDouble(doc.getY()));
                school.setHsLot(Double.parseDouble(doc.getX()));
                log.info("좌표 변환 성공: {}, 위도={}, 경도={}", school.getHsName(), school.getHsLat(), school.getHsLot());
            } else {
                log.warn("좌표를 찾을 수 없었습니다: {}", school.getHsName());
            }
        } catch (Exception e) {
            log.error("좌표 변환 중 오류 발생 (학교: {}): {}", school.getHsName(), e.getMessage());
        }
    }
    
    // 이제 기존의 전체 업데이트 메소드는 이렇게 간단해집니다.
    @Override
    @Transactional
    public void updateAllHighSchoolCoordinates() {
        List<HighSchoolVO> allSchools = highSchoolMapper.selectAllHighSchoolsForCoords(); // (별도의 간단한 조회 쿼리 권장)
        for (HighSchoolVO school : allSchools) {
            if (school.getHsLat() == null || school.getHsLot() == null) {
                fetchAndSetCoordinates(school); // 분리한 메소드 호출
                highSchoolMapper.updateHighSchoolCoordinates(school);
                try {
                    Thread.sleep(50); // API 호출 제한 피하기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // 고등학교 정보 입력 
    @Override
    @Transactional
    public int highSchoolInsert(HighSchoolVO highSchoolVO) {
        // 1. 전달받은 VO의 주소를 이용해 좌표 정보를 조회하고 VO에 채워넣음
        fetchAndSetCoordinates(highSchoolVO);
        
        // 2. 모든 정보가 채워진 VO를 Mapper로 전달하여 DB에 INSERT
        return highSchoolMapper.highSchoolInsert(highSchoolVO);
    }
    
    //고등학교 학과 입력
    @Override
    public int highSchoolDeptInsert(HighSchoolDeptVO highSchoolDeptVO) {
    	
    	return highSchoolMapper.highSchoolDeptInsert(highSchoolDeptVO);
    }

    // 고등학교 정보 수정
    @Override
    public int highSchoolUpdate(HighSchoolVO highSchoolVO) {
        // 주소가 변경되었을 경우에만 좌표를 다시 조회하도록 할 수 있음 (선택적)
        // fetchAndSetCoordinates(highSchoolVO); 
        return highSchoolMapper.highSchoolUpdate(highSchoolVO);
    }
	
	//고등학교 학과 정보 수정
	@Override
	public int highSchoolDeptUpdate(HighSchoolDeptVO highSchoolDeptVO) {

		return highSchoolMapper.highSchoolDeptUpdate(highSchoolDeptVO);
	}

}

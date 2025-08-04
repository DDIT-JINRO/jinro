package kr.or.ddit.empt.enp.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.empt.enp.service.EnterprisePostingService;
import kr.or.ddit.empt.enp.service.KakaoGeocodingAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/empt")
@RequiredArgsConstructor
@Slf4j
public class EnterprisePostingController {
	
	private final EnterprisePostingService enterprisePostingService;
	private final KakaoGeocodingAPI kakaoGeocodingAPI;
	
	@GetMapping("/enp/enterprisePosting.do")
	public String enterprisePosting() {
		
		return "empt/enp/enterprisePosting";
	}
	
	@GetMapping("/api")
	public void selectCompanyList(){
		List<CompanyVO> companyVOList =  enterprisePostingService.selectCompanyList();
		
		
		// 처음 5개 항목만 처리
	    for (CompanyVO  companyVO : companyVOList) {
	        

	        String address = kakaoGeocodingAPI.getAddress(companyVO.getCpLocationX(),companyVO.getCpLocationY() );

	        if (address != null && !address.isEmpty()) {
	            companyVO.setCpRegion(address);
	            enterprisePostingService.updateRegion(companyVO);
	        } else {
	            log.error("주소를 찾을 수 없습니다. CP_ID: {}", companyVO.getCpId());
	        }

	        // 각 항목 처리 후 1초(1000ms) 딜레이
	        try {
	            Thread.sleep(50);  // 1초간 대기
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();  // 예외 처리
	        }
	    }
		
	}
	
	
}

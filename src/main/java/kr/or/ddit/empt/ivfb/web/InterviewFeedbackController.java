package kr.or.ddit.empt.ivfb.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.empt.ivfb.service.InterviewFeedbackService;
import kr.or.ddit.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/empt")
public class InterviewFeedbackController {

	@Autowired
	InterviewFeedbackService interviewFeedbackService;
	
	@GetMapping("/ivfb/interViewFeedback.do")
	public String interViewFeedbackList() {
		return "empt/ivfb/interviewFeedback";
	}
	
	@GetMapping("/ivfb/insertInterViewFeedbackView.do")
	public String insertInterViewFeedbackView() {
		return "empt/ivfb/insertInterViewFeedbackView";
	}
	
	@ResponseBody
	@GetMapping("/ivfb/selectCompanyList.do")
	public ResponseEntity<Map<String, Object>> selectCompanyList(@RequestParam String cpName) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			List<CompanyVO> companyList = interviewFeedbackService.selectCompanyList(cpName);
			response.put("success", true);
			response.put("companyList", companyList);
			return ResponseEntity.ok(response);
		} catch(CustomException e) {
			log.error("기업 정보 리스트 조회 중 에러 발생 : {}", e.getMessage());
			response.put("success", false);
			response.put("message", "기업 정보 조회 중 오류가 발생했습니다 : " + e.getMessage());
			return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
		} catch(Exception e) {
			log.error("알 수 없는 에러 발생 : {}", e.getMessage());
			response.put("success", false);
			response.put("message", "알 수 없는 에러가 발생했습니다 : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
}

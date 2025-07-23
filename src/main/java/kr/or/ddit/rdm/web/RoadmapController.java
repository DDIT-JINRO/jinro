package kr.or.ddit.rdm.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.config.jwt.JwtUtil;
import kr.or.ddit.rdm.service.RoadmapService;
import kr.or.ddit.rdm.service.RoadmapStepVO;
import kr.or.ddit.rdm.service.RoadmapVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/roadmap")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class RoadmapController {

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	RoadmapService roadmapService;
	
	// 특정 사용자의 로드맵 정보 조회
	@GetMapping("/selectMemberRoadmap")
	public ResponseEntity<Map<String, Object>> selectMemberRoadmap(@AuthenticationPrincipal String memId) {
		Map<String, Object> roadmapInfo = this.roadmapService.selectMemberRoadmap(Integer.parseInt(memId));
		
		return new ResponseEntity<Map<String, Object>>(roadmapInfo, HttpStatus.OK);
	}
	
	// 로드맵 노드별 미션 리스트
	@GetMapping("/selectMissionList")
	public ResponseEntity<List<RoadmapStepVO>> selectMissionList() {
		List<RoadmapStepVO> roadmapStepVOList = this.roadmapService.selectMissionList();

		return new ResponseEntity<List<RoadmapStepVO>>(roadmapStepVOList, HttpStatus.OK);
	}
	
	// 특정 사용자의 미션 완료 확인 메서드
	@PostMapping("/updateCompleteMission")
	public ResponseEntity<String> updateCompleteMission(@AuthenticationPrincipal String memId, @RequestBody RoadmapVO request) {
		String result = this.roadmapService.updateCompleteMission(memId, request.getRsId());
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
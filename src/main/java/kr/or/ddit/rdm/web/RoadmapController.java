package kr.or.ddit.rdm.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.config.jwt.JwtUtil;
import kr.or.ddit.rdm.service.RoadmapService;
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
	
	@GetMapping("/selectMemberRoadmap")
	public ResponseEntity<Map<String, Object>> selectMemberRoadmap(@AuthenticationPrincipal String memId) {
		log.info("멤버의 아이디 입니다 : " + memId);
		
		Map<String, Object> roadmapInfo = this.roadmapService.selectMemberRoadmap(Integer.parseInt(memId));
		
		log.info("로드맵 정보입니다. : " + roadmapInfo);
		
		return new ResponseEntity<Map<String, Object>>(roadmapInfo, HttpStatus.OK);
	}
}
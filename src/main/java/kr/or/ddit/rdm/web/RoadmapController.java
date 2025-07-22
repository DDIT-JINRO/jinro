package kr.or.ddit.rdm.web;

import java.util.List;

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
	
	@GetMapping("/selectMemberRoadmap")
	public ResponseEntity<List<RoadmapVO>> selectMemberRoadmap(@AuthenticationPrincipal String memId) {
		List<RoadmapVO> roadmap = this.roadmapService.selectMemberRoadmap(Integer.parseInt(memId));
		
		log.info("로드맵 결과111 : " + roadmap);
		
		return new ResponseEntity<List<RoadmapVO>>(roadmap, HttpStatus.OK);
	}
}
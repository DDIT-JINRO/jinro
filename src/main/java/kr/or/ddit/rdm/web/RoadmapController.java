package kr.or.ddit.rdm.web;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/roadmap")
@CrossOrigin(origins = "http://localhost:5173")
public class RoadmapController {
	
	@GetMapping("/selectMemberId")
	public Map<String, Object> selectMemberId() {
		
		
		return Map.of("memId",	memId);
	}
	
	@GetMapping("/userInfo")
	public Map<String, Object> insertMemberInfo() {
		int memId = 1;
		
		log.info("selectMemberInfo -> memId : " + memId);
		
		return Map.of("memId",	memId);
	}
	
}

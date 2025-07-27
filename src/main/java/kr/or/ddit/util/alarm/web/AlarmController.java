package kr.or.ddit.util.alarm.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.util.alarm.service.AlarmService;
import kr.or.ddit.util.alarm.service.AlarmVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/alarm")
public class AlarmController {

	@Autowired
	AlarmService alarmService;

	@GetMapping("/getAlarms")
	public ResponseEntity<List<AlarmVO>> getAlarms(Principal principal){
		if(principal != null && principal.getName() != "anonymousUser") {
			String memIdStr = principal.getName();
			List<AlarmVO> list = this.alarmService.selectAllByMember(Integer.parseInt(memIdStr));
			return ResponseEntity.ok(list);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}

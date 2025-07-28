package kr.or.ddit.util.alarm.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.or.ddit.util.alarm.service.AlarmService;
import kr.or.ddit.util.alarm.service.AlarmVO;
import kr.or.ddit.util.alarm.service.impl.AlarmEmitterManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/alarm")
public class AlarmController {

	@Autowired
	AlarmEmitterManager emitterManager;

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

	// 클라이언트가 구독하면 emitterManager를 통해 관리하기 시작
	@GetMapping(value = "/sub",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter getMethodName(@RequestParam int memId) {

		SseEmitter emitter = null;
		emitter = emitterManager.createOrReplaceEmitter(memId);
		
		// EventSource 객체를 생성하면서 해당 주소(/api/alarm/sub)로 연결
		// 서버가 응답 받아 emitter 객체를 return 하면 정상적으로 연결완료
		// eventSource객체 emitter객체 1대1로 연결
		return emitter;
	}


}

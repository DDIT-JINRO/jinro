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

	@GetMapping(value = "/sub",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public String getMethodName(@RequestParam int memId) {
		SseEmitter emitter = new SseEmitter();
		System.out.println("##### emitter 타임아웃 : "+emitter.getTimeout());
		try {
			emitter.send(SseEmitter.event()
							.name("alarm")
							.data(new AlarmVO())
							.build());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String();
	}


}

package kr.or.ddit.cns.off.rsv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cnslt/off")
public class OfflineCounselingReserveController {
	
	@GetMapping("/offlineReservation.do")
	public String offlineReservation() {
		log.info("asdasd");
		return "cns/offlineReservation";
	}
	
}

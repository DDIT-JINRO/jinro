package kr.or.ddit.cnslt.off.cnsh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cnslt/off")
@Controller
@Slf4j
public class OfflineCounselingHistoryController {
	
	@GetMapping("cnsh/offlineReservationHistory.do")
	public String offlineReservationHistory() {
		return "cnslt/off/cnsh/offlineReservationHistory";
	}
}

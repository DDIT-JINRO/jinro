package kr.or.ddit.cnslt.vid.cnsh.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cnslt/vid")
//상담 예약 내역
public class VideoCounselingHistoryController {
	
	@GetMapping("cnsh/videoReservationHistory.do")
	public String videoReservationHistory() {
		return "cnslt/vid/cnsh/videoReservationHistory";
	}
}

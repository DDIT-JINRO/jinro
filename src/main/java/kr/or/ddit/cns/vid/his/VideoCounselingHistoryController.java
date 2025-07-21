package kr.or.ddit.cns.vid.his;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cnslt/vid")
//상담 예약 내역
public class VideoCounselingHistoryController {
	
	@GetMapping("/videoReservationHistory.do")
	public String videoReservationHistory() {
		return "cns/videoReservationHistory";
	}
}

package kr.or.ddit.cns.vid.rsv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cnslt/vid")
// 상담 예약 신청
public class VideoCounselingReserveController {
	
	@GetMapping("/videoReservation.do")
	public String videoReservation() {
		return "cns/videoReservation";
	}
}

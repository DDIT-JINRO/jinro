package kr.or.ddit.cnslt.vid.crsv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cnslt/vid")
// 상담 예약 신청
public class VideoCounselingReserveController {
	
	@GetMapping("crsv/videoReservation.do")
	public String videoReservation() {
		return "cnslt/vid/crsv/videoReservation";
	}
}

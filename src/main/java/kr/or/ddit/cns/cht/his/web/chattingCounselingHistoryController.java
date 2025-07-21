package kr.or.ddit.cns.cht.his.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Controller
@Slf4j
@RequestMapping("/cnslt/cht")
public class chattingCounselingHistoryController {
	
	@GetMapping("/chattingReservationHistory.do")
	public String chattingReservationHistory() {
		return "cns/chattingReservationHistory";
	}
}

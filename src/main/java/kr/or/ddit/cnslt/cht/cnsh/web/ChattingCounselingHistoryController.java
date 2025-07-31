package kr.or.ddit.cnslt.cht.cnsh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Controller
@Slf4j
@RequestMapping("/cnslt/cht")
public class ChattingCounselingHistoryController {
	
	@GetMapping("cnsh/chattingReservationHistory.do")
	public String chattingReservationHistory() {
		return "cnslt/cht/cnsh/chattingReservationHistory";
	}
}

package kr.or.ddit.cnslt.cht.crsv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Controller
@Slf4j
@RequestMapping("/cnslt/cht")
public class ChattingCounselingReserveController {
	@GetMapping("crsv/chattingReservation.do")
	public String chattingReservation() {
		return "cnslt/cht/crsv/chattingReservation";
	}
}

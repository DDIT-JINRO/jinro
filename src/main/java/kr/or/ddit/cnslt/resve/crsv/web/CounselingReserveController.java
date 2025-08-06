package kr.or.ddit.cnslt.resve.crsv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cnslt/resve")
public class CounselingReserveController {
	
	@GetMapping("/crsv/Reservation.do")
	public String counselingReservation() {
		log.info("asdasd");
		return "cnslt/resve/crsv/counselingreserve";
	}
	
}

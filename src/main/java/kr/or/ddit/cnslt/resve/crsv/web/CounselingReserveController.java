package kr.or.ddit.cnslt.resve.crsv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cnslt.resve.crsv.service.CounselingReserveService;
import kr.or.ddit.cnslt.resve.crsv.service.impl.CounselingReserveServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cnslt/resve")
public class CounselingReserveController {
	
	private final CounselingReserveService counselingReserveService; 
	
	@GetMapping("/crsv/reservation.do")
	public String counselingReservation() {
		log.info("asdasd");
		return "cnslt/resve/crsv/counselingreserve";
	}
	
}

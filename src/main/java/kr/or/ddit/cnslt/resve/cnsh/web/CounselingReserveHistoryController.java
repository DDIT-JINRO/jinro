package kr.or.ddit.cnslt.resve.cnsh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cnslt/resve")
@Controller
@Slf4j
public class CounselingReserveHistoryController {
	
	@GetMapping("/cnsh/counselingReserveHistory.do")
	public String counselingHistory() {
		return "cnslt/resve/cnsh/counselingreservehistory";
	}
}

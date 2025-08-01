package kr.or.ddit.cdp.rsm.rsmb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/rsm/rsmb")
@Controller
@Slf4j
public class ResumeBoardController {
	
	@GetMapping("/resumeBoardList.do")
	public String resumeBoardPage() {
		return "cdp/rsm/rsmb/resumeBoardList";
	}
	
	@GetMapping("/detail.do")
	public String resumeBoardDetailPage() {
		return "cdp/rsm/rsmb/resumeBoardDetail";
	}
}

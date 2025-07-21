package kr.or.ddit.cdp.imtintrvw.bsintrvw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cdp.rsm.rsm.web.ResumeController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/imtintrvw/bsintrvw")
@Controller
@Slf4j
public class BassInterviewController {

	@GetMapping()
	public String bassInterviewPage() {
		return "cdp/imtintrvw/bsintrvw/bassInterview";
	}
}

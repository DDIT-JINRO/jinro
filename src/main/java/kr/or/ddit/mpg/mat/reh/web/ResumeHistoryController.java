package kr.or.ddit.mpg.mat.reh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class ResumeHistoryController {

	@GetMapping("/mat/reh/selectResumeHistoryList.do")
	public String selectResumeHistoryList () {
		return "mpg/mat/reh/selectResumeHistoryList";
	}
	
}

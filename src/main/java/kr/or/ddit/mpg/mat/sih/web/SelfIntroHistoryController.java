package kr.or.ddit.mpg.mat.sih.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class SelfIntroHistoryController {

	@GetMapping("/mat/sih/selectSelfIntroHistoryList.do")
	public String selectSelfIntroHistoryList () {
		return "mpg/mat/sih/selectSelfIntroHistoryList";
	}
	
}

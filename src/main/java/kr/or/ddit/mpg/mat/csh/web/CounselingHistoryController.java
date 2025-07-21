package kr.or.ddit.mpg.mat.csh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class CounselingHistoryController {

	@GetMapping("/mat/csh/selectCounselingHistoryList.do")
	public String selectCounselingHistoryList () {
		return "mpg/mat/csh/selectCounselingHistoryList";
	}
	
}

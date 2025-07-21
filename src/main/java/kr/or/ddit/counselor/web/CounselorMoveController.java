package kr.or.ddit.counselor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/counselor")
@Slf4j
public class CounselorMoveController {
	
	@GetMapping
	public String movePage() {

		return "counselor/dashboard";
	}
	
	// counselor의 move페이지의 jsp로 forward
	@GetMapping("/counselorMoveController.do")
	public String movePage(@RequestParam String target) {

		log.info("movePage : "+target);

		return "counselor/"+target;
	}
	
}

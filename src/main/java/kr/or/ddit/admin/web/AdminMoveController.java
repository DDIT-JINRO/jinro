package kr.or.ddit.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminMoveController {


	
	@GetMapping
	public String movePage() {


		return "admin/movePage/dashboard";
	}
	
	// admin의 move페이지의 jsp로 forward
	@GetMapping("/adminMoveController.do")
	public String movePage(@RequestParam String target) {

		log.info("movePage : "+target);

		return "admin/movePage/"+target;
	}

	

	
}

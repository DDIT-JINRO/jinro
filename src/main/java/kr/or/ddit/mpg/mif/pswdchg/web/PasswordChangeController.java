package kr.or.ddit.mpg.mif.pswdchg.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mypage")
@Slf4j
public class PasswordChangeController {

	@GetMapping("/mpg/mif/pswdchg/selectPasswordChangeView.do")
	public String selectCareerList () {
		return "mpg/mif/pswdchg/selectPasswordChangeView";
	}
	
}

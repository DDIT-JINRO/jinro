package kr.or.ddit.mpg.mif.whdwl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class WithdrawalController {

	@GetMapping("/mif/whdwl/selectWithdrawalView.do")
	public String selectCareerList () {
		return "mpg/mif/whdwl/selectWithdrawalView";
	}
	
}

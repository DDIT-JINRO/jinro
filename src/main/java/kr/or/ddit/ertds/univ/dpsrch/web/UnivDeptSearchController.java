package kr.or.ddit.ertds.univ.dpsrch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ertds")
public class UnivDeptSearchController {
	
	// 중분류 학과정보로 이동
		@GetMapping("univ/dpsrch/selectDeptList.do")
		public String selectDeptList() {
			return "ertds/univ/dpsrch/selectDeptList";
		}
	
}

package kr.or.ddit.ertds.univ.mjsrch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ertds")
public class MajorSearchController {
	
	// 중분류 학과정보로 이동
		@GetMapping("univ/mjsrch/selectMajorList.do")
		public String majorListPage() {
			return "ertds/univ/mjsrch/major"; // /WEB-INF/views/erds/univ/major.jsp
		}
	
}

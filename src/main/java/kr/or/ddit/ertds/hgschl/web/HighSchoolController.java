package kr.or.ddit.ertds.hgschl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/hgschl")
@Controller
@Slf4j
public class HighSchoolController {
	
	
	
	@GetMapping("/selectHgschList.do")
	public String highSchoolListPage() {
		
		return "ertds/hgschl/list"; // /WEB-INF/views/erds/hgschl/list.jsp
	}

}

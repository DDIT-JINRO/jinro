package kr.or.ddit.ertds.hgschl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/hgschl")
@Controller
@Slf4j
public class HighSchoolController {
	
	
	//고등학교 리스트
	@GetMapping("/selectHgschList.do")
	public String highSchoolListPage() {
		
		return "ertds/hgschl/list"; // /WEB-INF/views/erds/hgschl/list.jsp
	}
	
	
	//고등학교 리스트
	@GetMapping("/selectHgschDetail.do")
	public String highSchoolDetailPage() {
		
		return "ertds/hgschl/detail"; // /WEB-INF/views/erds/hgschl/detail.jsp
	}

}

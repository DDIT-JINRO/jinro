package kr.or.ddit.ertds.hgschl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/ertds")
@Controller
@Slf4j
public class HighSchoolController {

	// 고등학교 리스트
	@GetMapping("/hgschl/selectHgschList.do")
	public String highSchoolListPage() {

		return "ertds/hgschl/HighSchoolList"; // /WEB-INF/views/erds/hgschl/list.jsp
	}

	// 고등학교 상세
	@GetMapping("/hgschl/selectHgschDetail.do")
	public String highSchoolDetailPage() {

		return "ertds/hgschl/HighSchoolDetail"; // /WEB-INF/views/erds/hgschl/detail.jsp
	}

}

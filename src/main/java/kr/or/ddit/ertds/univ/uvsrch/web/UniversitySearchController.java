package kr.or.ddit.ertds.univ.uvsrch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ertds")
@Controller
public class UniversitySearchController {

	// 중분류 대학검색으로 이동
	@GetMapping("/univ/uvsrch/selectUnivList.do")
	public String qlfexmListPage() {

		return "ertds/univ/uvsrch/list"; // /WEB-INF/views/erds/univ/list.jsp
	}

	// 대학비교
	@GetMapping("/univ/uvsrch/selectCompare.do")
	public String univComparePage() {

		return "ertds/univ/uvsrch/compare"; // /WEB-INF/views/erds/univ/compare.jsp
	}

	// 대학
	@GetMapping("/univ/uvsrch/selectDetail.do")
	public String univDetailPage() {

		return "ertds/univ/uvsrch/detail"; // /WEB-INF/views/erds/univ/detail.jsp
	}

}

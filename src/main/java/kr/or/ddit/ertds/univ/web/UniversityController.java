package kr.or.ddit.ertds.univ.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/univ")
@Controller
@Slf4j
public class UniversityController {

	// 중분류 대학검색으로 이동
	@GetMapping("/selectUnivList.do")
	public String qlfexmListPage() {

		return "ertds/univ/list"; // /WEB-INF/views/erds/univ/list.jsp
	}

	// 중분류 학과정보로 이동
	@GetMapping("/selectMajorList.do")
	public String majorListPage() {
		return "ertds/univ/major"; // /WEB-INF/views/erds/univ/major.jsp
	}

	// 대학비교
	@GetMapping("/selectCompare.do")
	public String univComparePage() {

		return "ertds/univ/compare"; // /WEB-INF/views/erds/univ/compare.jsp
	}

	// 대학비교
	@GetMapping("/selectDetail.do")
	public String univDetailPage() {

		return "ertds/univ/detail"; // /WEB-INF/views/erds/univ/detail.jsp
	}

}

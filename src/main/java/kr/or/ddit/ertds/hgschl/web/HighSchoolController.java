package kr.or.ddit.ertds.hgschl.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.ertds.hgschl.service.HighSchoolService;
import kr.or.ddit.ertds.hgschl.service.HighSchoolVO;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/ertds")
@Controller
@Slf4j
public class HighSchoolController {
	
	@Autowired
	private HighSchoolService highSchoolService;

	// 고등학교 리스트
	@GetMapping("/hgschl/selectHgschList.do")
	public String highSchoolListPage(Model model) {
		
		List<HighSchoolVO> schoolList = highSchoolService.getAllHighSchools();
		
		model.addAttribute("schoolList", schoolList);

		return "ertds/hgschl/HighSchoolList"; // /WEB-INF/views/erds/hgschl/list.jsp
	}

	// 고등학교 상세
	@GetMapping("/hgschl/selectHgschDetail.do")
	public String highSchoolDetailPage() {

		return "ertds/hgschl/HighSchoolDetail"; // /WEB-INF/views/erds/hgschl/detail.jsp
	}

}

package kr.or.ddit.ertds.univ.uvsrch.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.ertds.univ.uvsrch.service.UniversityService;
import kr.or.ddit.ertds.univ.uvsrch.service.UniversityVO;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/ertds/univ/uvsrch")
@Controller
public class UniversitySearchController {

	@Autowired
	UniversityService universityService;
	
	// 중분류 대학검색으로 이동
	@GetMapping("/selectUnivList.do")
	public String selectUnivList(
			@RequestParam(required = false) String searchKeyword,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "5") int size,
			UniversityVO universityVO,
			Model model,
			Principal principal) {
		if (universityVO != null && universityVO.getSize() == 0) universityVO.setSize(size);
		if (universityVO != null && universityVO.getCurrentPage() == 0) universityVO.setCurrentPage(currentPage);		
		
		List<UniversityVO> list = this.universityService.selectUniversityList(universityVO);
		int totalCount = this.universityService.selectUniversityTotalCount(universityVO);
		
		log.info("list : ", list);
		log.info("totalCount : ", totalCount);
		ArticlePage<UniversityVO> articlePage = new ArticlePage<>(totalCount, currentPage, size, list, searchKeyword);
		List<BookMarkVO> bookMarkVOList = new ArrayList<>();
		if(principal!=null && !principal.getName().equals("anonymousUser")) {
			int memId = Integer.parseInt(principal.getName());
			BookMarkVO bookMarkVO = new BookMarkVO();
			bookMarkVO.setMemId(memId);
			bookMarkVO.setBmCategoryId("G03001");
			
			bookMarkVOList = this.universityService.selectBookMarkVO(bookMarkVO);
			log.info("bookMarkVOList"+bookMarkVOList);
			
		}
		model.addAttribute("articlePage", articlePage);
		model.addAttribute("bookMarkVOList", bookMarkVOList);
		
		return "ertds/univ/uvsrch/list"; // /WEB-INF/views/erds/univ/list.jsp
	}

	// 대학비교
	@GetMapping("/selectCompare.do")
	public String selectCompare() {

		return "ertds/univ/uvsrch/compare"; // /WEB-INF/views/erds/univ/compare.jsp
	}

	// 대학
	@GetMapping("/selectDetail.do")
	public String selectDetail() {

		return "ertds/univ/uvsrch/detail"; // /WEB-INF/views/erds/univ/detail.jsp
	}

}

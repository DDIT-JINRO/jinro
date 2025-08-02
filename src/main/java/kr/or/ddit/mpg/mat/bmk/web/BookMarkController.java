package kr.or.ddit.mpg.mat.bmk.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkService;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class BookMarkController {

	@Autowired
	BookMarkService bookmarkService;
	
	@GetMapping("/mat/bmk/selectBookMarkList.do")
	public String selectBookMarkList (@AuthenticationPrincipal String memId, @ModelAttribute BookMarkVO bookmarkVO, Model model) {
		
		List<ComCodeVO> bmCategoryIdList = this.bookmarkService.selectBmCategoryIdList();
		
		ArticlePage<BookMarkVO> articlePage = this.bookmarkService.selectBookmarkList(memId, bookmarkVO);
		
		model.addAttribute("articlePage", articlePage);
		model.addAttribute("bmCategoryList", bmCategoryIdList);
		
		return "mpg/mat/bmk/selectBookmarkList";
	}
	
}

package kr.or.ddit.cnslt.rvw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.cnslt.rvw.service.CounselingReviewService;
import kr.or.ddit.cnslt.rvw.service.CounselingReviewVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cnslt/rvw")
public class CounselingReviewController {
	
	@Autowired
	private CounselingReviewService counselingReviewService;
	
	@GetMapping("/cnsReview.do")
	public String cnsReview(@ModelAttribute CounselingReviewVO counselingReview, Model model) {
		
		ArticlePage<CounselingReviewVO> articlePage = counselingReviewService.selectCounselingReview(counselingReview);
		
		model.addAttribute("articlePage", articlePage);
		
		return "cnslt/rvw/cnsReview";
	}
	
	@GetMapping("/cnslt/rvw/insertCnsReviewView.do")
	public String insertCnsReviewView() {
		return "cnslt/rvw/insertCnsReviewView.do";
	}
}

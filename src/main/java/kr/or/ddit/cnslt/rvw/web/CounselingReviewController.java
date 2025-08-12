package kr.or.ddit.cnslt.rvw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		ArticlePage<CounselingReviewVO> articlePage = counselingReviewService.selectCounselingReviewList(counselingReview);
		
		model.addAttribute("articlePage", articlePage);
		
		return "cnslt/rvw/cnsReview";
	}
	
	@GetMapping("/insertCnsReviewView.do")
	public String insertCnsReviewView(@AuthenticationPrincipal String memId, Model model) {
		return "cnslt/rvw/insertCnsReviewView";
	}
	
	@GetMapping("/updateCnsReviewView.do")
	public String updateCnsReviewView(@RequestParam String crId, Model model) {
		
		CounselingReviewVO counselingReview = counselingReviewService.selectCounselingReview(crId) ;
		
		model.addAttribute("counselingReview", counselingReview);
		
		return "cnslt/rvw/updateCnsReviewView";
	}
}

package kr.or.ddit.cnslt.rvw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cnslt/rvw")
public class CounselingReviewController {
	
	@GetMapping("/cnsReview.do")
	public String cnsReview() {
		return "cnslt/rvw/cnsReview";
	}
}

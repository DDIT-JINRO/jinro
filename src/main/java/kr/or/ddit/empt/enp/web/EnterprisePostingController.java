package kr.or.ddit.empt.enp.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.empt.enp.service.EnterprisePostingService;
import kr.or.ddit.util.ArticlePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/empt")
@RequiredArgsConstructor
@Slf4j
public class EnterprisePostingController {
	
	private final EnterprisePostingService enterprisePostingService;
	
	@GetMapping("/enp/enterprisePosting.do")
	public String enterprisePosting(
			@ModelAttribute CompanyVO companyVO,
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model) {
		
		log.info("companyVO"+companyVO);
		companyVO.setCurrentPage(currentPage);
		companyVO.setSize(5);
		int total = enterprisePostingService.selectCompanyListCount(companyVO);
		log.info("total : "+total);
		
		List<CompanyVO> companyVOList = enterprisePostingService.selectCompanyList(companyVO);
		List<ComCodeVO> codeVOCompanyScaleList = enterprisePostingService.selectCodeVOCompanyScaleList();
		List<ComCodeVO>  CodeVORegionList = enterprisePostingService.selectCodeVORegionList();
		
		ArticlePage<CompanyVO> articlePage =
				new ArticlePage<CompanyVO>(total, companyVO.getCurrentPage(), companyVO.getSize(), companyVOList, companyVO.getKeyword());
		
		articlePage.setUrl("/empt/enp/enterprisePosting.do");
		model.addAttribute("articlePage", articlePage);
		model.addAttribute("codeVOCompanyScaleList",codeVOCompanyScaleList);
		model.addAttribute("CodeVORegionList",CodeVORegionList);
		return "empt/enp/enterprisePosting";
	}
	
	
}

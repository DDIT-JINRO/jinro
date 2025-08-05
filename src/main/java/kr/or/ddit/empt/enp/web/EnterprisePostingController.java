package kr.or.ddit.empt.enp.web;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/enp/enterprisePostingUpdate.do")
	public ResponseEntity<String> enterprisePostingUpdate(@RequestBody CompanyVO companyVO){
		
		log.info("companyVO"+companyVO);
		
		int cpId = enterprisePostingService.checkCompanyByCpId(companyVO);
		
		companyVO.setCpId(cpId);
		
		int cnt = enterprisePostingService.updateEnterprisePosting(companyVO);
		
		if(cnt >0) {
			return ResponseEntity.ok("sucess");
		}else {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("처리중 문제발생");
		}
	}
	
	@PostMapping("/enp/enterprisePostingDelete.do")
	public ResponseEntity<String> enterprisePostingDelete(@RequestBody CompanyVO companyVO){
		log.info("companyVO"+companyVO);
		int cnt = enterprisePostingService.deleteEnterprisePosting(companyVO);
		if(cnt>0) {
			return ResponseEntity.ok("sucess");
		}else {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("처리중 문제발생");
		}
		
	}
	
}

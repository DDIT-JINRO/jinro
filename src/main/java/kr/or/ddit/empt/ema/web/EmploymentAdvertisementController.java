package kr.or.ddit.empt.ema.web;

import java.security.Principal;
import java.util.ArrayList;
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

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.empt.ema.service.EmploymentAdvertisementService;
import kr.or.ddit.empt.ema.service.HireVO;
import kr.or.ddit.empt.enp.service.EnterprisePostingService;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;
import kr.or.ddit.util.ArticlePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/empt")
public class EmploymentAdvertisementController {

	private final EmploymentAdvertisementService employmentAdvertisementService;
	private final EnterprisePostingService enterprisePostingService;

	@GetMapping("/ema/employmentAdvertisement.do")
	public String emplymentAdvertisementList(@ModelAttribute HireVO hireVO, Model model, Principal principal) {

		int total = employmentAdvertisementService.selectFilteredHireTotalCount(hireVO);
		// 실제 검색된 hire
		List<HireVO> hireVOList = employmentAdvertisementService.selectFilteredHireList(hireVO);

		// 필터 보내줄 것//
		ComCodeVO comCodeVO = new ComCodeVO();
		// 지역코드
		comCodeVO.setClCode("G23");
		List<ComCodeVO> CodeVORegionList = employmentAdvertisementService.selectCodeVOList(comCodeVO);
		// 직무 대문류
		comCodeVO.setClCode("G15");
		List<ComCodeVO> CodeVOHireClassList = employmentAdvertisementService.selectCodeVOList(comCodeVO);
		// 고용 형태별
		comCodeVO.setClCode("G17");
		List<ComCodeVO> CodeVOHireTypeList = employmentAdvertisementService.selectCodeVOList(comCodeVO);

		ArticlePage<HireVO> articlePage = new ArticlePage<HireVO>(total, hireVO.getCurrentPage(), hireVO.getSize(),
				hireVOList, hireVO.getKeyword());
		articlePage.setUrl("/empt/ema/employmentAdvertisement.do");

		// 북마크
		List<BookMarkVO> bookMarkVOList = new ArrayList<>();

		if (principal != null && !principal.getName().equals("anonymousUser")) {
			int memId = Integer.parseInt(principal.getName());
			BookMarkVO bookMarkVO = new BookMarkVO();
			bookMarkVO.setMemId(memId);
			bookMarkVO.setBmCategoryId("G03003");

			bookMarkVOList = enterprisePostingService.selectBookMarkVO(bookMarkVO);
		}

		model.addAttribute("CodeVORegionList", CodeVORegionList);
		model.addAttribute("CodeVOHireClassList", CodeVOHireClassList);
		model.addAttribute("CodeVOHireTypeList", CodeVOHireTypeList);
		model.addAttribute("articlePage", articlePage);
		model.addAttribute("bookMarkVOList", bookMarkVOList);

		return "empt/ema/employmentAdvertisement";
	}

	@PostMapping("/ema/employmentAdvertisementUpdate.do")
	public ResponseEntity<String> employmentAdvertisementUpdate(@RequestBody HireVO hireVO) {

		int hireId = employmentAdvertisementService.checkHireByHireId(hireVO);

		hireVO.setHireId(hireId);

		int cnt = employmentAdvertisementService.updateEmploymentAdvertisement(hireVO);

		if (cnt > 0) {
			return ResponseEntity.ok("sucess");
		} else {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("처리중 문제발생");
		}
	}

	@PostMapping("/ema/employmentAdvertisementDelete.do")
	public ResponseEntity<String> employmentAdvertisementDelete(@RequestBody HireVO hireVO) {
		int cnt = employmentAdvertisementService.deleteEmploymentAdvertisement(hireVO);
		if (cnt > 0) {
			return ResponseEntity.ok("sucess");
		} else {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("처리중 문제발생");
		}

	}

}

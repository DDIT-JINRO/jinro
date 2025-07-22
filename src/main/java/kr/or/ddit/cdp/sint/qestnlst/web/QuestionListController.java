package kr.or.ddit.cdp.sint.qestnlst.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.cdp.rsm.rsm.web.ResumeController;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.util.ArticlePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/sint/qestnlst")
@Controller
@Slf4j
@RequiredArgsConstructor
public class QuestionListController {

	private final SelfIntroService selfIntroService;

	@GetMapping()
	public String questionList(@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(required = false) String keyword, @RequestParam(required = false) String siqJob,
			Model model) {
		int size = 5; // 한 페이지에 10개
		int startRow = (currentPage - 1) * size + 1;
		int endRow = currentPage * size;

		SelfIntroQVO selfIntroQVO = new SelfIntroQVO();
		selfIntroQVO.setKeyword(keyword);
		selfIntroQVO.setSiqJobFilter(siqJob);
		selfIntroQVO.setStartRow(startRow);
		selfIntroQVO.setEndRow(endRow);

		log.info("questionList -> " + selfIntroQVO);
		// 서비스 호출
		int total = selfIntroService.selectSelfIntroQCount(selfIntroQVO);
		log.info("questionList -> total" + total);
		List<SelfIntroQVO> selfIntroQVOLsit = selfIntroService.selectSelfIntroQList(selfIntroQVO);
		log.info("questionList -> selfIntroQVOLsit" + selfIntroQVOLsit);

		ArticlePage<SelfIntroQVO> page = new ArticlePage<>(total, currentPage, size, selfIntroQVOLsit, keyword);
		page.setUrl("/sint/qestnlst");

		model.addAttribute("articlePage", page);
		model.addAttribute("siqJobFilter", siqJob); // 직무 필터 유지용
		return "cdp/sint/qestnlst/questionList"; // JSP
	}

}

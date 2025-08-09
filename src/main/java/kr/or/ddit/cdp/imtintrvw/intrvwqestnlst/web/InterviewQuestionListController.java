package kr.or.ddit.cdp.imtintrvw.intrvwqestnlst.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.util.ArticlePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/imtintrvw/intrvwqestnlst")
@Controller
@Slf4j
@RequiredArgsConstructor
public class InterviewQuestionListController {

	private final SelfIntroService selfIntroService;

	@GetMapping("/intrvwQuestionList.do")
	public String intrvwQuestionList(@RequestParam(required = false) String keyword, 
			@RequestParam(value = "siqJobFilter", required = false) List<String> siqJobFilter,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size, 
			Principal principal, 
			Model model) {

		if (siqJobFilter == null || (siqJobFilter.size() == 1 && siqJobFilter.get(0).isEmpty())) {
			siqJobFilter = new ArrayList<>();
		}

		SelfIntroQVO selfIntroQVO = new SelfIntroQVO();
		selfIntroQVO.setKeyword(keyword);
		selfIntroQVO.setSiqJobFilter(siqJobFilter);
		selfIntroQVO.setStartRow((currentPage - 1) * size);
		selfIntroQVO.setEndRow(currentPage * size);

		List<ComCodeVO> codeVOList = selfIntroService.selectSelfIntroComCodeList();

		Map<String, String> codeMap = new HashMap<>();

		for (ComCodeVO code : codeVOList) {
			String key = code.getCcId();
			String value = code.getCcName();
			codeMap.put(key, value);
		}

		int total = selfIntroService.selectSelfIntroQCount(selfIntroQVO);

		List<SelfIntroQVO> selfIntroQVOList = selfIntroService.selectSelfIntroQList(selfIntroQVO);

		ArticlePage<SelfIntroQVO> page = new ArticlePage<>(total, currentPage, size, selfIntroQVOList, keyword);
		page.setUrl("/cdp/imtintrvw/intrvwqestnlst/intrvwQuestionList.do");

		model.addAttribute("memId", principal.getName());
		model.addAttribute("codeMap", codeMap);
		model.addAttribute("codeVOList", codeVOList);
		model.addAttribute("articlePage", page);
		model.addAttribute("siqJobFilter", siqJobFilter);

		return "cdp/imtintrvw/intrvwqestnlst/intrvwQuestionList";
	}

	@PostMapping("/cart")
	public String saveCart(
			@RequestParam("questionIds") String questionIds, 
			HttpSession session, 
			@AuthenticationPrincipal String memId, 
			HttpServletRequest requset) {
		int id = Integer.parseInt(memId);
		List<Integer> questionIdList = Arrays.stream(questionIds.split(",")).filter(s -> !s.isBlank()).map(Integer::valueOf).collect(Collectors.toList());

		SelfIntroVO selfIntroVO = new SelfIntroVO();
		selfIntroVO.setMemId(id);

		int siId = selfIntroService.insertIntroToQList(selfIntroVO, questionIdList);

		requset.setAttribute("siId", siId);
		return "redirect:/cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangementList.do?siId=" + siId;
	}

}

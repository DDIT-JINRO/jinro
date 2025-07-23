package kr.or.ddit.cdp.sint.qestnlst.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.or.ddit.cdp.rsm.rsm.web.ResumeController;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.com.ComCodeVO;
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
			Model model,
			@AuthenticationPrincipal String memId) {
		
		int size = 5; // 한 페이지에 10개
		int startRow = (currentPage - 1) * size + 1;
		int endRow = currentPage * size;

		SelfIntroQVO selfIntroQVO = new SelfIntroQVO();
		selfIntroQVO.setKeyword(keyword);
		selfIntroQVO.setSiqJobFilter(siqJob);
		selfIntroQVO.setStartRow(startRow);
		selfIntroQVO.setEndRow(endRow);

		// 서비스 호출
		List<ComCodeVO> codeVOList = selfIntroService.selectSelfIntroComCodeList();
		log.info("codeVOList",codeVOList);
		
		Map<String,String> codeMap = new HashMap();

		// 2) 코드 리스트를 한 줄씩 순회하면서
		for (ComCodeVO code : codeVOList) {
		    // 2‑1) 키와 값을 꺼낸 뒤
		    String key   = code.getCcId();    // 예: "001"
		    String value = code.getCcName();  // 예: "프로그래밍"
		    // 2‑2) Map에 저장
		    codeMap.put(key, value);
		}
		
		int total = selfIntroService.selectSelfIntroQCount(selfIntroQVO);
		List<SelfIntroQVO> selfIntroQVOList = selfIntroService.selectSelfIntroQList(selfIntroQVO);

		ArticlePage<SelfIntroQVO> page = new ArticlePage<>(total, currentPage, size, selfIntroQVOList, keyword);
		page.setUrl("/sint/qestnlst");

		model.addAttribute("memId",memId);
		model.addAttribute("codeMap",codeMap);
		model.addAttribute("codeVOList",codeVOList);
		model.addAttribute("articlePage", page);
		model.addAttribute("siqJobFilter", siqJob); // 직무 필터 유지용
		return "cdp/sint/qestnlst/questionList"; // JSP
	}
	
    @PostMapping("/cart")
    public String saveCart(
        @RequestParam("questionIds") String questionIds,
        HttpSession session,
        @AuthenticationPrincipal String memId
    ) {
    	int id = Integer.parseInt(memId);
        // "1,3,5" → List<Long>
        List<Long> questionIdList = Arrays.stream(questionIds.split(","))
                               .filter(s -> !s.isBlank())
                               .map(Long::valueOf)
                               .collect(Collectors.toList());
        
        log.info("questionIdList"+questionIdList);
        
        SelfIntroVO selfIntroVO = new SelfIntroVO();
        selfIntroVO.setMemId(id);
        
        log.info("selfIntroVO"+selfIntroVO);
        
        int siId = selfIntroService.insertIntro(selfIntroVO,questionIdList);
        log.info("siId : "+siId);
        session.setAttribute("siId", siId);
        return "redirect:/sint/qestnlst/sintwrt";
    }
    
    @GetMapping("/sintwrt")
    public String writeForm(Model model, HttpSession session) {
    	int siId = (Integer) session.getAttribute("siId");
		log.info("siId : "+siId);
		model.addAttribute("siId", siId);
		return "cdp/sint/sintwrt/selfIntroWriting";
    }
    
}

package kr.or.ddit.prg.ctt.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.prg.ctt.service.ContestService;
import kr.or.ddit.prg.ctt.service.ContestVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/prg/ctt")
@Slf4j
public class ContestController {
	
	@Autowired
	ContestService contestService;
	
	@GetMapping("/cttList.do")
	public String cttList(
	        @RequestParam(defaultValue = "1") int currentPage,
	        @RequestParam(required = false) String keyword,
            @RequestParam(value = "contestGubunFilter", required = false) List<String> contestGubunFilter, // 변수명 수정
	        @RequestParam(value = "contestTargetFilter", required = false) List<String> contestTargetFilter,
	        @RequestParam(value = "contestTypeFilter", required = false) List<String> contestTypeFilter,
	        Model model) {
	        
	        log.info("cttList : contestGubunFilter={}, contestTargetFilter={}, contestTypeFilter={}", contestGubunFilter, contestTargetFilter, contestTypeFilter);

	        ContestVO contestVO = new ContestVO();
	        contestVO.setKeyword(keyword);
	        
	        contestVO.setContestGubunFilter(contestGubunFilter);
	        contestVO.setContestTargetFilter(contestTargetFilter);
	        contestVO.setContestTypeFilter(contestTypeFilter);
	        
            // 페이징 정보 설정 
            int size = 6;
            int startRow = (currentPage - 1) * size + 1;
            int endRow = currentPage * size;
            contestVO.setStartRow(startRow);
            contestVO.setEndRow(endRow);

	        int total = contestService.selectCttCount(contestVO);
            List<ContestVO> contestList = contestService.selectCttList(contestVO);
	        
            log.info("조회된 공모전 목록: {}개", contestList.size());
	        
            // ⭐⭐⭐ [수정된 부분] 컨트롤러에서 ArticlePage 객체를 생성합니다. ⭐⭐⭐
	        ArticlePage<ContestVO> page = new ArticlePage<>(total, currentPage, size, contestList, keyword);
	        page.setUrl("/prg/ctt/cttList.do"); // URL 설정
	        model.addAttribute("articlePage", page);
	        model.addAttribute("checkedFilters", contestVO); // 체크된 필터 상태 유지를 위해 전달
	        
            log.info("JSP로 전달할 articlePage: {}", page);
	        return "prg/ctt/cttList";
	    }
	
	@GetMapping("/cttDetail.do")
	public String selectCttDetail(@RequestParam String cttId, Model model) {
		
		ContestVO cttDetail = contestService.selectCttDetail(cttId);
		model.addAttribute("cttDetail", cttDetail);
		
		log.info("JSP로 전달할 상세 정보: {}", cttDetail);
		return "prg/ctt/cttDetail";
	}
}
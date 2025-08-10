package kr.or.ddit.prg.ctt.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.prg.ctt.service.ContestService;
import kr.or.ddit.prg.ctt.service.ContestVO;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.com.ComCodeVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/prg/ctt")
@Slf4j
public class ContestController {
	
	@Autowired
	private ContestService contestService;
	
	@GetMapping("/cttList.do")
	public String cttList(Model model,
	        @RequestParam(defaultValue = "1") int currentPage,
	        @RequestParam(required = false) String keyword,
            @RequestParam(value = "contestGubunFilter", required = false) List<String> contestGubunFilter,
	        @RequestParam(value = "contestTargetFilter", required = false) List<String> contestTargetFilter,
	        @RequestParam(value = "contestTypeFilter", required = false) List<String> contestTypeFilter,
	        @RequestParam(value = "contestStatusFilter", required = false) List<String> contestStatusFilter) {
	        
	        log.info("cttList : contestGubunFilter={}, contestTargetFilter={}, contestTypeFilter={}, contestStatusFilter={}", contestGubunFilter, contestTargetFilter, contestTypeFilter, contestStatusFilter);

	        ContestVO contestVO = new ContestVO();
	        contestVO.setKeyword(keyword);
	        contestVO.setCurrentPage(currentPage);
	        contestVO.setSize(6);
	        contestVO.setContestGubunFilter(contestGubunFilter);
	        contestVO.setContestTargetFilter(contestTargetFilter);
	        contestVO.setContestTypeFilter(contestTypeFilter);
	        contestVO.setContestStatusFilter(contestStatusFilter);
	        
	        int total = contestService.selectCttCount(contestVO);
            List<ContestVO> contestList = contestService.selectCttList(contestVO);
	        
            List<ComCodeVO> contestTypeList = contestService.getContestTypeList();
            List<ComCodeVO> contestTargetList = contestService.getContestTargetList();
            List<ComCodeVO> contestStatusList = new ArrayList<>();
            
            // JSP 필터에 사용될 데이터
            model.addAttribute("contestTypeList", contestTypeList);
            model.addAttribute("contestTargetList", contestTargetList);
            
            log.info("조회된 공모전 목록: {}개", contestList.size());
	        
	        ArticlePage<ContestVO> page = new ArticlePage<>(total, currentPage, 6, contestList, keyword);
	        page.setUrl("/prg/ctt/cttList.do");
	        model.addAttribute("articlePage", page);
	        model.addAttribute("checkedFilters", contestVO);
	        
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
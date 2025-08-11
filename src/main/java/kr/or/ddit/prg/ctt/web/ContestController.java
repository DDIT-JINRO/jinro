package kr.or.ddit.prg.ctt.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String cttList(Model model, @RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(required = false) String keyword,
			@RequestParam(value = "contestGubunFilter", required = false) List<String> contestGubunFilter,
			@RequestParam(value = "contestTargetFilter", required = false) List<String> contestTargetFilter,
			@RequestParam(value = "contestTypeFilter", required = false) List<String> contestTypeFilter,
			@RequestParam(value = "contestStatusFilter", required = false) List<String> contestStatusFilter) {

		log.info(
				"cttList : contestGubunFilter={}, contestTargetFilter={}, contestTypeFilter={}, contestStatusFilter={}",
				contestGubunFilter, contestTargetFilter, contestTypeFilter, contestStatusFilter);

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

	@PostMapping("/contestUpdate.do")
	@ResponseBody
	public ResponseEntity<String> saveContest(@RequestBody ContestVO contestVO) {
		int result = contestService.updateContest(contestVO);
		if (result > 0) {
			return ResponseEntity.ok("성공적으로 저장되었습니다.");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장에 실패했습니다.");
	}

	@PostMapping("/contestDelete.do")
	@ResponseBody
	public ResponseEntity<String> deleteContest(@RequestBody ContestVO contestVO) {
		int result = contestService.deleteContest(contestVO.getContestId());
		if (result > 0) {
			return ResponseEntity.ok("성공적으로 삭제되었습니다.");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제에 실패했습니다.");
	}
}
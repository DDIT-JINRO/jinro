package kr.or.ddit.admin.cmg.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.cmg.service.ContentsManagementService;
import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.prg.act.service.ActivityVO;
import kr.or.ddit.prg.act.vol.service.ActivityVolunteerService;
import kr.or.ddit.prg.ctt.service.ContestService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.impl.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/cmg")
@Slf4j
public class ContentsManagementController {

	@Autowired
	ContentsManagementService contentsManagementService;
	
	@Autowired
	ActivityVolunteerService activityVolunteerService;
	
	@Autowired
	ContestService contestService;
	
	@Autowired
	FileServiceImpl fileServiceImpl;

	@GetMapping("/getEntList.do")
	public ArticlePage<CompanyVO> getEntList(CompanyVO companyVO) {

		companyVO.setStartNo(1);
		return contentsManagementService.getEntList(companyVO);

	}

	@PostMapping("/entDetail.do")
	public Map<String, Object> entDetail(@RequestParam String id) {

		return contentsManagementService.entDetail(id);
	}
	
	@GetMapping("/selectActList.do")
	public ResponseEntity<Map<String, Object>> selectActList(
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) List<String> contestGubunFilter,
			@RequestParam(required = false) List<String> contestTargetFilter,
			@RequestParam(required = false) List<String> contestStatusFilter) {
		Map<String, Object> response = new HashMap<>();
		
		ActivityVO activityVO = new ActivityVO();
		activityVO.setKeyword(keyword);
		activityVO.setCurrentPage(currentPage);
		activityVO.setSize(size);
		activityVO.setContestGubunFilter(contestGubunFilter);
		activityVO.setContestTargetFilter(contestTargetFilter);
		activityVO.setContestStatusFilter(contestStatusFilter);

		int total = activityVolunteerService.selectVolCount(activityVO);
		List<ActivityVO> activityList = activityVolunteerService.selectVolList(activityVO);
		if(activityList == null || activityList.isEmpty()) {
			response.put("success", false);
			response.put("message", "리스트 로딩 중 에러 발생");
			return ResponseEntity.ok(response);
		}

		// 공모전과 동일하게 모집대상(청년/청소년)을 사용하는것이니 만들어둔 매소드를 활용한다
		List<ComCodeVO> contestTargetList = contestService.getContestTargetList();
		List<ComCodeVO> contestTypeList = contestService.getContestTypeList();
		
		ArticlePage<ActivityVO> articlePage = new ArticlePage<>(total, currentPage, 6, activityList, keyword);
		articlePage.setUrl("/prg/ctt/cttList.do");
		response.put("success", true);
		response.put("contestTargetList", contestTargetList);
		response.put("articlePage", articlePage);
		response.put("activityVO", activityVO);
		response.put("contestTypeList", contestTypeList);
		
		return ResponseEntity.ok(response);
	}
	
}

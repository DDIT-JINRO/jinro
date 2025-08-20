package kr.or.ddit.admin.csmg.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.csmg.service.CounselManagementService;
import kr.or.ddit.util.ArticlePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/csmg")
public class CounselManagementController {
	private final CounselManagementService counselManagementService;
	
	@GetMapping("/selectMonthlyCounselingStatList.do")
	public Map<String, Object> selectMonthlyCounselingStatList() {
		Map<String, Object> map = counselManagementService.selectMonthlyCounselingStatList();
		
		return map;
	}
	
	@GetMapping("/selectCounselorStatList.do")
	public ArticlePage<Map<String, Object>> selectCounselorStatList(
			@RequestParam(defaultValue = "1") String currentPage,
			@RequestParam(defaultValue = "10") String size,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String status,
			@RequestParam(defaultValue = "id", required = false) String sortBy,
			@RequestParam(defaultValue = "asc",required = false) String sortOrder,
			@RequestParam(required = false) String userListInFilter
			) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("currentPage", currentPage);
		map.put("size", size);
		map.put("keyword", keyword);
		map.put("status", status);
		map.put("sortBy", sortBy);
		map.put("sortOrder", sortOrder);
		map.put("userListInFilter", userListInFilter);
		
		ArticlePage<Map<String, Object>> articlePage = counselManagementService.selectCounselorStatList(map);
		
		return articlePage;
	}
}

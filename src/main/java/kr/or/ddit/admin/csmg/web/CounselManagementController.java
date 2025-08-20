package kr.or.ddit.admin.csmg.web;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.csmg.service.CounselManagementService;
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
}

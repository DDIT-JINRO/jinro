package kr.or.ddit.admin.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.service.AdminCommonChartService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/chart")
@Slf4j
public class AdminDashBoard {
	
	@Autowired
	AdminCommonChartService adminCommonChartService;
	
	@GetMapping("/getAdminDashboard.do")
	public Map<String, Object> getAdminDashboard() {
		
		return adminCommonChartService.getAdminDashboard();
	}
	
}

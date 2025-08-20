package kr.or.ddit.admin.csmg.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.ArticlePage;

public interface CounselManagementService {
	Map<String, Object> selectMonthlyCounselingStatList();
	
	ArticlePage<Map<String, Object>> selectCounselorStatList(Map<String, Object> map);
}

package kr.or.ddit.admin.umg.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.umg.service.UserManagementService;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/umg")
@Slf4j
public class UserManagementController {

	@Autowired
	UserManagementService userManagementService;

	@GetMapping("/getMemberList.do")
	public ArticlePage<MemberVO> getMemberList(
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "memRole") String memRole) {

		log.info("방문 : " + currentPage + "사이즈" + size + "키워드" + keyword + "상태" + status + "맴롤" + memRole);

		return userManagementService.getMemberList(currentPage, size, keyword, status, memRole);
	}

	@PostMapping("/getMemberDetail.do")
	public Map<String, Object> getMemberDetail(@RequestParam("id") String id) {

		Map<String, Object> memberDetail = userManagementService.getMemberDetail(id);

		return memberDetail;
	}

}

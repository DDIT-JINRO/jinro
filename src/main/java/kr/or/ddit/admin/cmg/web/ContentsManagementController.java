package kr.or.ddit.admin.cmg.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.admin.cmg.service.ContentsManagementService;
import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/cmg")
@Slf4j
public class ContentsManagementController {

	@Autowired
	ContentsManagementService contentsManagementService;

	@GetMapping("/getEntList.do")
	public ArticlePage<CompanyVO> getEntList(CompanyVO companyVO) {

		companyVO.setStartNo(1);
		return contentsManagementService.getEntList(companyVO);

	}

	@PostMapping("/entDetail.do")
	public Map<String, Object> entDetail(@RequestParam String id) {

		contentsManagementService.entDetail(id);

		return null;
	}

}

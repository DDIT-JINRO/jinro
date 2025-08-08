package kr.or.ddit.cnsLeader.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.cns.service.CounselingLogVO;
import kr.or.ddit.cns.service.CounselingVO;
import kr.or.ddit.cns.web.CounselorApiController;
import kr.or.ddit.cnsLeader.service.CounselLeaderService;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/cnsld")
public class CnsLeaderApiController {

	@Autowired
	CounselLeaderService counselLeaderService;

	@GetMapping("/counselList.do")
	public ResponseEntity<ArticlePage<CounselingVO>> counselList(CounselingVO counselingVO, @AuthenticationPrincipal String counselStr){
		counselingVO.setCounsel(Integer.parseInt(counselStr));
		ArticlePage<CounselingVO> articlePage = this.counselLeaderService.selectCounselLogList(counselingVO);
		return ResponseEntity.ok(articlePage);
	}

	@PostMapping("/updateCounselLog.do")
	public ResponseEntity<Boolean> updateCounselLog(CounselingLogVO counselingLogVO){
		return ResponseEntity.ok(this.counselLeaderService.updateCounselLog(counselingLogVO));
	}

}

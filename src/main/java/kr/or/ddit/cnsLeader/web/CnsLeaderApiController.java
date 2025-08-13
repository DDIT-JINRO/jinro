package kr.or.ddit.cnsLeader.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.cns.service.CounselingLogVO;
import kr.or.ddit.cns.service.CounselingVO;
import kr.or.ddit.cns.service.VacationVO;
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

	@GetMapping("/vacationList.do")
	public ResponseEntity<ArticlePage<VacationVO>> vacationList(VacationVO vacationVO){
		ArticlePage<VacationVO> articlePage = this.counselLeaderService.selectVacationList(vacationVO);
		return ResponseEntity.ok(articlePage);
	}

	@GetMapping("/vacationDetail.do")
	public ResponseEntity<VacationVO> vacationDetail(@RequestParam int vaId){
		return ResponseEntity.ok(this.counselLeaderService.vacationDetail(vaId));
	}

	@PostMapping("/updateVacation.do")
	public ResponseEntity<Boolean> updateVacation(VacationVO vacationVO, @AuthenticationPrincipal String approverStr){
		vacationVO.setVaApprover(Integer.parseInt(approverStr));
		return ResponseEntity.ok(this.counselLeaderService.updateVacation(vacationVO));
	}

	@GetMapping("/checkCounselList.do")
	public ResponseEntity<List<CounselingVO>> checkCounselList(@RequestParam int vaId){
		return ResponseEntity.ok(this.counselLeaderService.selectRequestedCounselBetweenVacation(vaId));
	}
	
	@GetMapping("/counselScheduleList.do")
	public  ResponseEntity<ArticlePage<CounselingVO>> counselScheduleList(
			@RequestParam("counselReqDatetime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date counselReqDatetime,
			@RequestParam String keyword,
			@RequestParam int currentPage,
			@RequestParam int size
			){
		CounselingVO counselingVO = new CounselingVO();
		counselingVO.setCounselReqDatetime(counselReqDatetime);
		counselingVO.setKeyword(keyword);
		counselingVO.setCurrentPage(currentPage);
		counselingVO.setSize(size);
		log.info("counselingVO"+counselingVO);
		ArticlePage<CounselingVO> articlePage = this.counselLeaderService.selectCounselScheduleList(counselingVO);
		
		return ResponseEntity.ok(articlePage);
		
	}

}

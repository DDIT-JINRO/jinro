package kr.or.ddit.worldcup.web;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.worldcup.service.ComCodeVO;
import kr.or.ddit.worldcup.service.JobsVO;
import kr.or.ddit.worldcup.service.WorldCupService;
import kr.or.ddit.worldcup.service.WorldCupVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/worldcup")
public class WorldCupController {

	private final WorldCupService worldCupService;
	
	@GetMapping("/selectCategories")
	public ResponseEntity<List<ComCodeVO>> selectCategories(@RequestParam String round) {
		List<ComCodeVO> comCodeVOList = worldCupService.selectCategories(round);
		log.info("WorldCupController - > selectCategories : " + comCodeVOList);
		return ResponseEntity.ok(comCodeVOList);

	}

	@PostMapping("/selectJobsByCategory")
	public ResponseEntity<List<JobsVO>> selectJobsByCategory(@RequestBody ComCodeVO comCodeVO) {
		log.info("WorldCupController -> comCodeVO :" + comCodeVO);
		List<JobsVO> jobsVOList = worldCupService.selectJobsByCategory(comCodeVO);
		log.info("WorldCupController -> jobsVOList :" + jobsVOList);

		return ResponseEntity.ok(jobsVOList);

	}
	
	@PostMapping("/selectJobById")
	public ResponseEntity<JobsVO> selectJobById(@RequestBody JobsVO jobsVO) {
		jobsVO = worldCupService.selectJobById(jobsVO);
		log.info("selectJobById -> jobsVO :" + jobsVO);
		
		return ResponseEntity.ok(jobsVO);

	}
	
	@PostMapping("/insertWorldcupResult")
	public ResponseEntity<Integer> insertWorldcupResult(@AuthenticationPrincipal String memId,@RequestBody JobsVO jobsVO) {

		int id = Integer.valueOf(memId);
		
		int cnt = worldCupService.insertWorldcupResult(jobsVO,id);
		
		log.info("cnt : "+cnt);
		return (ResponseEntity<Integer>) ResponseEntity.ok();

	}
	
	
	
}

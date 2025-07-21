package kr.or.ddit.worldcup.web;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.worldcup.service.ComCodeVO;
import kr.or.ddit.worldcup.service.WorldCupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/worldcup")
public class WorldCupController {

	private final WorldCupService worldCupService;
	
	@GetMapping("/selectCategories")
	public ResponseEntity<List<ComCodeVO>> selectCategories(@RequestParam String round){
		List<ComCodeVO> comCodeVOList = worldCupService.selectCategories(round);
		log.info("WorldCupController - > selectCategories : "+comCodeVOList);
		return ResponseEntity.ok(comCodeVOList);
		
	}
	
	
}

package kr.or.ddit.worldcup.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.worldcup.service.WorldCupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/wouldcup")
public class WorldCupController {

	private final WorldCupService worldCupService;
	
	
	
}

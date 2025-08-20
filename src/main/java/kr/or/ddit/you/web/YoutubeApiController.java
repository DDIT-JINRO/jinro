package kr.or.ddit.you.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.ddit.you.service.YoutubeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class YoutubeApiController {

	@Autowired
	YoutubeService service;
	
	@Value("${DEV.YOUTUBE.API_KEY}")
	private String API_KEY;

	@GetMapping("/main/youtubeJsp")
	public String youtube(@AuthenticationPrincipal String memId, Model model) {	
		
		// 채널 검색 ID
		String channelId = "UC7veJl4E23uPDXoVu-0qYAA";
		// 키워드
		String result ="";
		
		if(!memId.equals("anonymousUser")) {
			log.info("서비스 진행");
			result  = service.getKeyword(memId);
			log.info("result : "+result);
		}else {
			result ="취업";
		}
		
		model.addAttribute("result", result);
		model.addAttribute("channelId", channelId);
		
		return "you/youtube";
	}
}

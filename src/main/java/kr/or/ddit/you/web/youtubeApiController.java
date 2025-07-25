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
public class youtubeApiController {

	@Autowired
	YoutubeService service;
	
	//AIzaSyA8qDS5UDxsmNB9MmHCCinfgQrfhY94IXc
	@Value("${DEV.YOUTUBE.API_KEY}")
	private String API_KEY;
	
	@Value("${DEV.YOUTUBE.ACCESS_TOKEN}")
	private String ACCESS_TOKEN; // 실제 액세스 토큰으로 교체


	
	@GetMapping("/main/youtubeJsp")
	public String youtube(@AuthenticationPrincipal String memId, Model model) {	
		log.info(memId);	
		
		// 채널 검색 ID
		String channelId = "UCFCtZJTuJhE18k8IXwmXTYQ";
		// 키워드
		String result ="";
		
		if(!memId.equals("anonymousUser")) {
			log.info("서비스 진행");
			result  = service.getKeyword(memId)+"적성";
			log.info("result : "+result);
		}else {
			result ="적성|진로";
		}
		
		model.addAttribute("result", result);
		model.addAttribute("channelId", channelId);
		
		return "you/youtube";
	}
}

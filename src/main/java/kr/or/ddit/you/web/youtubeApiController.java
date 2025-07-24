package kr.or.ddit.you.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.you.service.YoutubeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class youtubeApiController {

	@Autowired
	YoutubeService service;
	
	//AIzaSyA8qDS5UDxsmNB9MmHCCinfgQrfhY94IXc
	private static final String API_KEY = "AIzaSyBFbyrGwjuDTxWcD0_Wg9M5WP3vRUz8Xwk";
	private static final String ACCESS_TOKEN = "ya29.a0AfB_byEXAMPLE-TOKEN"; // 실제 액세스 토큰으로 교체


	
	@GetMapping("/main/youtubeJsp")
	public String youtube(@AuthenticationPrincipal String id ,Model model) {	
		log.info(id);	
		
		// 채널 검색 ID
		String channelId = "UCFCtZJTuJhE18k8IXwmXTYQ";
		// 키워드
		String result ="";
		
		if(id!="anonymousUser") {
			log.info("서비스 진행");
			result  = service.getKeyword(id)+"적성";
			log.info("result : "+result);
		}else {
			result ="적성|진로";
		}
		
		model.addAttribute("result", result);
		model.addAttribute("channelId", channelId);
		
		return "you/youtube";
	}
}

package kr.or.ddit.video.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.video.service.VideoService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class VideoController {

	@Autowired
	VideoService videoService;
	
	@GetMapping("/video")
	public String videoHome(@RequestParam int counselId , Model model) throws Exception {
		
		int result = videoService.createVideoChatRoom(counselId);
		log.info(result+"");
		model.addAttribute("data", result);
		
		// 💡 "/WEB-INF/views/video/video.jsp"로 이동
		return "video/video";
	}


}

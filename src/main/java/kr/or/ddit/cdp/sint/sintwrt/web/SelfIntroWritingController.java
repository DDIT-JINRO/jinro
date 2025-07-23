package kr.or.ddit.cdp.sint.sintwrt.web;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.model.Model;
import kr.or.ddit.cdp.sint.sintlst.web.SelfIntroListController;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/sint/sintwrt")
@Controller
@Slf4j
public class SelfIntroWritingController {

	@GetMapping()
	public String selfIntroWritingPage(@RequestParam(value = "siId", required = false) String siId, Model model) {
		log.info(siId);

		return "cdp/sint/sintwrt/selfIntroWriting";
	}
}

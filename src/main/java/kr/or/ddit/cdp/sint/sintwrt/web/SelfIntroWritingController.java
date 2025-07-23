package kr.or.ddit.cdp.sint.sintwrt.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.cdp.sint.service.SelfIntroContentVO;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.cdp.sint.sintlst.web.SelfIntroListController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/sint/sintwrt")
@Controller
@Slf4j
@RequiredArgsConstructor
public class SelfIntroWritingController {
	
	private final SelfIntroService selfIntroService;

	@GetMapping()
	public String selfIntroWritingPage(
	    @RequestParam(value = "siId", required = false) String siId,
	    Model model
	) {
	    if (siId != null) {
	        int id = Integer.parseInt(siId);
	        SelfIntroVO selfIntroVO = new SelfIntroVO();
	        selfIntroVO.setSiId(id);
	        selfIntroVO = selfIntroService.selectBySelfIntroId(selfIntroVO);
	        List<SelfIntroContentVO> contentList = selfIntroService.selectBySelfIntroContentIdList(selfIntroVO);
	        // contentList 기반으로 질문 VO 리스트
	        List<SelfIntroQVO> qList = contentList.stream()
	            .map(c -> selfIntroService.selectBySelfIntroQId(c))
	            .collect(Collectors.toList());

	        model.addAttribute("selfIntroVO", selfIntroVO);
	        model.addAttribute("selfIntroContentVOList", contentList);
	        model.addAttribute("selfIntroQVOList", qList);
	    } else {
	        // 신규 작성 모드엔 모든 리스트를 비워둡니다.
	        model.addAttribute("selfIntroVO", new SelfIntroVO());
	        model.addAttribute("selfIntroContentVOList", Collections.emptyList());
	        model.addAttribute("selfIntroQVOList", Collections.emptyList());
	    }
	    return "cdp/sint/sintwrt/selfIntroWriting";
	}

}

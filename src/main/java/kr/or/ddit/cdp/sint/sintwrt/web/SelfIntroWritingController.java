package kr.or.ddit.cdp.sint.sintwrt.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.cdp.sint.service.SelfIntroContentVO;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import kr.or.ddit.cdp.sint.sintlst.web.SelfIntroListController;
import kr.or.ddit.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/sint/sintwrt")
@Controller
@Slf4j
@RequiredArgsConstructor
public class SelfIntroWritingController {
	
	private final SelfIntroService selfIntroService;

//	@PostMapping()
//	public String selfIntroWritingPostPage(
//			@RequestAttribute("siId") String siId,
//	    Model model
//	) {
//	    if (siId != null) {
//	        int id = Integer.parseInt(siId);
//	        SelfIntroVO selfIntroVO = new SelfIntroVO();
//	        selfIntroVO.setSiId(id);
//	        selfIntroVO = selfIntroService.selectBySelfIntroId(selfIntroVO);
//	        List<SelfIntroContentVO> contentList = selfIntroService.selectBySelfIntroContentIdList(selfIntroVO);
//	        // contentList 기반으로 질문 VO 리스트
//	        List<SelfIntroQVO> qList = contentList.stream()
//	            .map(c -> selfIntroService.selectBySelfIntroQId(c))
//	            .collect(Collectors.toList());
//
//	        model.addAttribute("selfIntroVO", selfIntroVO);
//	        model.addAttribute("selfIntroContentVOList", contentList);
//	        model.addAttribute("selfIntroQVOList", qList);
//	    } else {
//	        return "redirect:/sint/sintwrt";
//	    }
//	    return "cdp/sint/sintwrt/selfIntroWriting";
//	}
	
	@GetMapping()
	public String showSelfIntroWrite(
			@RequestParam(value = "siId", required = false) String siId ,
			Model model,
			@AuthenticationPrincipal String memId,
			RedirectAttributes ra) {
		// 1) 공통 질문: SIQ_JOB IS NULL
		List<SelfIntroQVO> commonQList = Collections.emptyList();
		
		log.info("siId : "+siId);

		SelfIntroVO selfIntroVO = new SelfIntroVO();
		// 2) 이미 저장된 자기소개서(siId)가 있으면, 그에 딸린 질문·답변
		List<SelfIntroQVO> selfIntroQVOList = Collections.emptyList();
		List<SelfIntroContentVO> selfIntroContentVOList = Collections.emptyList();
		 if (siId != null) {
		        int id = Integer.parseInt(siId);
		        selfIntroVO.setSiId(id);
		        
		        
		        //맴버 아이디 확인 후 아닐경우 에러 반환
		        try {
		        	selfIntroVO = selfIntroService.selectBySelfIntroId(selfIntroVO);
		        	selfIntroService.cheakselfIntrobyMemId(selfIntroVO , memId);
		        	List<SelfIntroContentVO> contentList = selfIntroService.selectBySelfIntroContentIdList(selfIntroVO);
		        	// contentList 기반으로 질문 VO 리스트
		        	log.info("contentList : "+contentList);
		        	List<SelfIntroQVO> qList = contentList.stream()
		        			.map(c -> selfIntroService.selectBySelfIntroQId(c))
		        			.collect(Collectors.toList());
		        	
		        	model.addAttribute("selfIntroVO", selfIntroVO);
		        	model.addAttribute("selfIntroContentVOList", contentList);
		        	model.addAttribute("selfIntroQVOList", qList);
				} catch (CustomException ex) {
					ra.addFlashAttribute("errorMessage", ex.getMessage());
					 return "redirect:/sint/sintwrt";
				}
		        
	    } else {
			// 새 글 쓰기일 때 빈 VO 하나
			commonQList = selfIntroService.selectCommonQuestions();
			model.addAttribute("selfIntroVO", selfIntroVO);
			model.addAttribute("commonQList", commonQList);
			model.addAttribute("selfIntroQVOList", selfIntroQVOList);
			model.addAttribute("selfIntroContentVOList", selfIntroContentVOList);
		}

		return "cdp/sint/sintwrt/selfIntroWriting";
	}

}

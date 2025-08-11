package kr.or.ddit.cdp.imtintrvw.intrvwqestnmn.web;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewDetailListVO;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewDetailVO;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewQuestionVO;
import kr.or.ddit.cdp.imtintrvw.intrvwqestnmn.service.InterviewQuestionMangementService;
import kr.or.ddit.cdp.sint.service.SelfIntroContentVO;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/imtintrvw/intrvwqestnmn")
@Controller
@Slf4j
public class InterviewQuestionMangementController {
	
	@Autowired
	private InterviewQuestionMangementService interviewQuestionMangementService;
	
	@GetMapping("/interviewQuestionMangementList.do")
	public String interviewQuestionMangement(Principal principal, Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) String status,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size) {
		
		if (principal != null && !principal.getName().equals("anonymousUser")) {
			String memId = principal.getName();

			InterviewDetailListVO interviewDetailListVO = new InterviewDetailListVO();
			interviewDetailListVO.setMemId(Integer.parseInt(memId));
			interviewDetailListVO.setCurrentPage(currentPage);
			interviewDetailListVO.setSize(5);
			interviewDetailListVO.setKeyword(keyword);
			interviewDetailListVO.setStatus(status);

			int total = interviewQuestionMangementService.selectInterviewQuestionTotalBymemId(interviewDetailListVO);
			// 사용자 면접질문 리스트
			List<InterviewDetailListVO> SelfIntroVOList = interviewQuestionMangementService.selectInterviewQuestionBymemId(interviewDetailListVO);

			ArticlePage<InterviewDetailListVO> articlePage = new ArticlePage<InterviewDetailListVO>(total, currentPage, 5,
					SelfIntroVOList, keyword);
			articlePage.setUrl("/cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangementList.do");

			model.addAttribute("articlePage", articlePage);

		} else {
			return "redirect:/login";
		}
		
		return "cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangement";
	}
	
	@GetMapping("/detail.do")
	public String interviewQuestionMangementDetail(@RequestParam(value="idlId", required=false) String idlId,
            Principal principal, Model model) {
		
		if (principal == null || "anonymousUser".equals(principal.getName())) {
            return "redirect:/login";
        }
		
		// 1) 공통 질문: SIQ_JOB IS NULL
		List<InterviewQuestionVO> commonQList = Collections.emptyList();
		InterviewDetailListVO interviewDetailListVO = new InterviewDetailListVO();
		
		String memId = principal.getName();
		interviewDetailListVO.setMemId(Integer.parseInt(memId));
		// 2) 이미 저장된 면접질문(idlId)가 있으면, 그에 딸린 질문·답변
		List<InterviewQuestionVO> interviewQuestionVOlist = Collections.emptyList();
		List<InterviewDetailVO> interviewDetailVOList = Collections.emptyList();
		
		if (idlId != null) {
			int id = Integer.parseInt(idlId);
			interviewDetailListVO.setIdlId(id);
			interviewDetailListVO.setMemId(Integer.parseInt(memId));

			// 면접이 존재하지 않을 경우 에러 반환
			interviewDetailListVO = interviewQuestionMangementService.selectByInterviewQuestionId(interviewDetailListVO);

			// 맴버 아이디 확인 후 아닐경우 에러 반환
			interviewQuestionMangementService.cheakInterviewQuestionbyMemId(interviewDetailListVO, memId);

			// 면접 Id로 면접 내용 리스트 반환
			List<InterviewDetailVO> contentList = interviewQuestionMangementService.selectByInterviewQuestionContentIdList(interviewDetailListVO);

			// contentList 기반으로 질문 VO 리스트
			List<InterviewQuestionVO> qList = contentList.stream().map(c -> interviewQuestionMangementService.selectByInterviewQuestionQId(c))
					.collect(Collectors.toList());

			model.addAttribute("interviewDetailListVO", interviewDetailListVO);
			model.addAttribute("interviewDetailVOList", contentList);
			model.addAttribute("interviewQuestionVOList", qList);

		} else {
			// 새 글 쓰기일 때 빈 VO 하나
			commonQList = interviewQuestionMangementService.selectCommonQuestions();
			model.addAttribute("interviewDetailListVO", interviewDetailListVO);
			model.addAttribute("commonQList", commonQList);
			model.addAttribute("interviewQuestionVOList", interviewQuestionVOlist);
			model.addAttribute("interviewDetailVOList", interviewDetailVOList);
		}
		
		return "cdp/imtintrvw/intrvwqestnmn/interviewQuestionWriting";
	}
	
	@PostMapping("/save")
	public String saveInterviewQuestion(@RequestParam String idlTitle,
		    @RequestParam Integer idlId, // 신규면 0 또는 null로 보낼 것
		    @RequestParam List<Integer> iqIdList,
		    @RequestParam List<String> idAnswerList,
		    @RequestParam String memId,
		    @RequestParam String idlStatus) {
		
		int memberId = Integer.parseInt(memId);
		InterviewDetailListVO interviewDetailListVO = new InterviewDetailListVO();
		interviewDetailListVO.setMemId(memberId);
		interviewDetailListVO.setIdlTitle(idlTitle);
		interviewDetailListVO.setIdlStatus(idlStatus);
		
		if(idlId == 0) {
			//신규저장
			int newIdlId = interviewQuestionMangementService.insertInterviewQuestionId(interviewDetailListVO);
			
		}
		
		

		return "redirect:/cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangementList.do";
	}
}

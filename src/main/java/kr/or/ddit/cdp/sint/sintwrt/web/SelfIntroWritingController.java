package kr.or.ddit.cdp.sint.sintwrt.web;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.cdp.sint.service.SelfIntroContentVO;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/sint/sintwrt")
@Controller
@Slf4j
@RequiredArgsConstructor
public class SelfIntroWritingController {
	
	private final SelfIntroService selfIntroService;

	@GetMapping()
	public String showSelfIntroWrite(
			@RequestParam(value = "siId", required = false) String siId ,
			Model model,
			@AuthenticationPrincipal String memId) {
		// 1) 공통 질문: SIQ_JOB IS NULL
		List<SelfIntroQVO> commonQList = Collections.emptyList();
		
		log.info("memId : "+memId);
		log.info("siId : "+siId);

		SelfIntroVO selfIntroVO = new SelfIntroVO();
		selfIntroVO.setMemId(Integer.parseInt(memId));
		// 2) 이미 저장된 자기소개서(siId)가 있으면, 그에 딸린 질문·답변
		List<SelfIntroQVO> selfIntroQVOList = Collections.emptyList();
		List<SelfIntroContentVO> selfIntroContentVOList = Collections.emptyList();
		 if (siId != null) {
		        int id = Integer.parseInt(siId);
		        selfIntroVO.setSiId(id);
		        selfIntroVO.setMemId(Integer.parseInt(memId));
		        
		        
		        //맴버 아이디 확인 후 아닐경우 에러 반환
				selfIntroVO = selfIntroService.selectBySelfIntroId(selfIntroVO);

				selfIntroService.cheakselfIntrobyMemId(selfIntroVO, memId);
				List<SelfIntroContentVO> contentList = selfIntroService.selectBySelfIntroContentIdList(selfIntroVO);
				// contentList 기반으로 질문 VO 리스트
				log.info("contentList : " + contentList);
				List<SelfIntroQVO> qList = contentList.stream().map(c -> selfIntroService.selectBySelfIntroQId(c))
						.collect(Collectors.toList());

				model.addAttribute("selfIntroVO", selfIntroVO);
				model.addAttribute("selfIntroContentVOList", contentList);
				model.addAttribute("selfIntroQVOList", qList);

		        
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
	
	@PostMapping("/save")
	public String saveSelfIntro(
	    @RequestParam("siTitle") String siTitle,
	    @RequestParam("siId") Integer siId,
	    @RequestParam("siqIdList") List<Long> siqIdList,
	    @RequestParam("sicContentList") List<String> sicContentList,
	    @RequestParam("memId") String memId
	) {

	    int memberId = Integer.parseInt(memId);

		
		log.info("memId : " + memId); //memId memId : 1
		log.info("siTitle : " + siTitle); //제목 siTitle : 나의 자소서 1
		log.info("siId : " + siId); // 자소서 아이디 새로만든 거면 0 siId : 1
		log.info("siqIdList : " + siqIdList); // 질문 리스트 번호 siqIdList : [161, 162, 163, 1, 2]
		log.info("sicContentList : " + sicContentList); //[공통1, 공통2, 공통3, 선택1, 선택2]
		

	    // 1) SelfIntroVO 셋팅
	    SelfIntroVO selfIntroVO = new SelfIntroVO();
	    selfIntroVO.setMemId(memberId);
	    selfIntroVO.setSiTitle(siTitle);
	    selfIntroVO.setSiStatus("완료");

	    if (siId == 0) {
	        // 신규 저장
	        // 1-1) SELF_INTRO INSERT → newSiId 리턴
	        int newSiId = selfIntroService.insertIntroId(selfIntroVO); // 
	        // 1-2) 각 질문별 내용 INSERT
	        for (int i = 0; i < siqIdList.size(); i++) {
	            Long questionId = siqIdList.get(i); // 질문 아이디 
	            String answer    = sicContentList.get(i); // 질문 답변
	            selfIntroService.insertContent(newSiId, questionId, answer, i + 1);
	        }

	    } else {
	        // 기존 글 업데이트
	    	selfIntroVO.setSiId(siId);
	        // 2-1) 제목·상태 업데이트
	        selfIntroService.updateIntro(selfIntroVO);

	     // 1) 기존 CONTENT 리스트 조회
	        List<SelfIntroContentVO> existingList = 
	            selfIntroService.selectBySelfIntroContentIdList(selfIntroVO);
	        // 2) Map<siqId, sicId> 생성
	        Map<Integer,Integer> qToSicId = existingList.stream()
	            .collect(Collectors.toMap(
	                SelfIntroContentVO::getSiqId,
	                SelfIntroContentVO::getSicId
	            ));

	        // 3) 전달받은 siqIdList, sicContentList 순회하며 업데이트
	        for(int i = 0; i < siqIdList.size(); i++) {
	            int siqId    = siqIdList.get(i).intValue();
	            String content= sicContentList.get(i);
	            int sicId = qToSicId.get(siqId);
	            // sicId가 null일 일 없으므로 바로 호출
	            selfIntroService.updateContent(
	                sicId,           // 수정할 PK
	                siqId,           // 질문 ID
	                content,         // 답변 텍스트
	                i + 1            // 순서(order)
	            );
	        }
	    }

	    return "redirect:/sint/sintlst";
	}

}

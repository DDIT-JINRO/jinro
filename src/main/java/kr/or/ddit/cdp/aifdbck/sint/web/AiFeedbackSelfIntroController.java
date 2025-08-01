package kr.or.ddit.cdp.aifdbck.sint.web;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.cdp.aifdbck.sint.service.SelfIntroDetailDto;
import kr.or.ddit.cdp.sint.service.SelfIntroContentVO;
import kr.or.ddit.cdp.sint.service.SelfIntroQVO;
import kr.or.ddit.cdp.sint.service.SelfIntroService;
import kr.or.ddit.cdp.sint.service.SelfIntroVO;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/aifdbck/sint")
@Controller
@Slf4j
public class AiFeedbackSelfIntroController {


	@Autowired
	private SelfIntroService selfIntroService;

	@GetMapping("/aiFeedbackSelfIntroList.do")
	public String aiImitationInterviewPage(Principal principal, Model model) {

		// 로그인하지 않은 경우 처리
		if (principal == null || principal.getName().equals("anonymousUser")) {
			return "redirect:/login"; // 로그인 페이지로 리다이렉트
		}

		String memId = principal.getName();

		// SelfIntroListController와 동일한 SelfIntroVO를 사용
		SelfIntroVO selfIntroVO = new SelfIntroVO();
		selfIntroVO.setMemId(Integer.parseInt(memId));
		// 이 페이지에서는 페이지네이션이 필요 없으므로 다른 파라미터는 설정하지 않습니다.

		selfIntroVO.setCurrentPage(1); // 시작 번호
		selfIntroVO.setSize(9999); // 충분히 큰 값으로 설정하여 모든 데이터를 가져옴
		// 서비스 메서드를 호출하여 해당 사용자의 자기소개서 전체 목록을 가져옴
		// selectSelfIntroBymemId는 페이지네이션을 위한 로직이 포함되어 있을 수 있으므로
		// 서비스에 전체 목록을 가져오는 새로운 메서드를 추가하는 것이 더 좋을 수 있습니다.
		// 예: selfIntroService.getAllSelfIntrosByMemId(memId);
		List<SelfIntroVO> selfIntroList = selfIntroService.selectSelfIntroBymemId(selfIntroVO);

		System.out.println("이거 확인해봑라" + selfIntroList);
		// JSP로 넘겨줄 모델에 담기. JSTL <c:forEach>의 items 속성과 일치하도록 "selfIntroList"로 명명.
		model.addAttribute("selfIntroList", selfIntroList);

		log.info("AI 피드백 페이지에 전달되는 자기소개서 목록: {}", selfIntroList);

		return "cdp/aifdbck/sint/aiFeedbackSelfIntro";
	}
	
	// AiFeedbackSelfIntroController.java에 추가
	@GetMapping("/getSelfIntroDetail.do")
	@ResponseBody // JSON 데이터를 반환하기 위해 필요
	public SelfIntroDetailDto getSelfIntroDetail(@RequestParam(value = "siId", required = false) String siId, Principal principal) {
	    // 1. 로그인 확인
	    if (principal == null || principal.getName().equals("anonymousUser")) {
	        // 인증 실패 시 적절한 응답 코드 반환
	        log.error("유효하지 않은 memId로 요청이 들어왔습니다.");
	        return null; // 또는 DTO에 에러 메시지를 담아 반환
	    }
	    int siIdInt = Integer.parseInt(siId);

	    log.info("요청 파라미터 siId: {}", siId);
	    String memId = principal.getName();
	    log.info("Principal에서 가져온 memId: {}", memId);
	    // 2. 서비스 로직을 통해 데이터 조회
	    SelfIntroVO selfIntroVO = new SelfIntroVO();
	    selfIntroVO.setSiId(siIdInt);
	    selfIntroVO.setMemId(Integer.parseInt(memId));

	    selfIntroService.cheakselfIntrobyMemId(selfIntroVO, memId);

	    // 자기소개서 기본 정보 조회
	    SelfIntroVO detailVO = selfIntroService.selectBySelfIntroId(selfIntroVO);
	    // 질문과 답변 목록 조회
	    List<SelfIntroContentVO> contentList = selfIntroService.selectBySelfIntroContentIdList(detailVO);
	    List<SelfIntroQVO> qList = contentList.stream().map(c -> selfIntroService.selectBySelfIntroQId(c)).collect(Collectors.toList());


	    // 4. 필요한 데이터를 담을 DTO 객체를 생성하여 반환
	    SelfIntroDetailDto dto = new SelfIntroDetailDto();
	    dto.setTitle(detailVO.getSiTitle());
	    dto.setQuestions(qList);
	    dto.setContents(contentList);
	    // dto.setAiFeedback(aiFeedback);

	    return dto; // JSON 형태로 변환되어 클라이언트로 전송됨
	}
}

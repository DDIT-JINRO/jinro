package kr.or.ddit.cdp.aifdbck.sint.web;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

		selfIntroVO.setCurrentPage(1); // 시작 번호
		selfIntroVO.setSize(9999); // 충분히 큰 값으로 설정하여 모든 데이터를 가져옴
		// 서비스 메서드를 호출하여 해당 사용자의 자기소개서 전체 목록을 가져옴
		List<SelfIntroVO> selfIntroList = selfIntroService.selectSelfIntroBymemId(selfIntroVO);

		// JSP로 넘겨줄 모델에 담기. JSTL <c:forEach>의 items 속성과 일치하도록 "selfIntroList"로 명명.
		model.addAttribute("selfIntroList", selfIntroList);

		return "cdp/aifdbck/sint/aiFeedbackSelfIntro";
	}

	@GetMapping("/getSelfIntroDetail.do")
	@ResponseBody // JSON 데이터를 반환하기 위해 필요
	public SelfIntroDetailDto getSelfIntroDetail(@RequestParam(value = "siId", required = false) String siId,
			Principal principal) {
		// 1. 로그인 확인
		if (principal == null || principal.getName().equals("anonymousUser")) {
			// 인증 실패 시 적절한 응답 코드 반환
			return null; // 또는 DTO에 에러 메시지를 담아 반환
		}
		int siIdInt = Integer.parseInt(siId);

		String memId = principal.getName();
		// 2. 서비스 로직을 통해 데이터 조회
		SelfIntroVO selfIntroVO = new SelfIntroVO();
		selfIntroVO.setSiId(siIdInt);
		selfIntroVO.setMemId(Integer.parseInt(memId));

		selfIntroService.cheakselfIntrobyMemId(selfIntroVO, memId);

		// 자기소개서 기본 정보 조회
		SelfIntroVO detailVO = selfIntroService.selectBySelfIntroId(selfIntroVO);
		// 질문과 답변 목록 조회
		List<SelfIntroContentVO> contentList = selfIntroService.selectBySelfIntroContentIdList(detailVO);
		List<SelfIntroQVO> qList = contentList.stream().map(c -> selfIntroService.selectBySelfIntroQId(c))
				.collect(Collectors.toList());

		// 4. 필요한 데이터를 담을 DTO 객체를 생성하여 반환
		SelfIntroDetailDto dto = new SelfIntroDetailDto();
		dto.setTitle(detailVO.getSiTitle());
		dto.setQuestions(qList);
		dto.setContents(contentList);

		return dto; // JSON 형태로 변환되어 클라이언트로 전송됨
	}
}

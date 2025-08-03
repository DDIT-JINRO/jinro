package kr.or.ddit.util.apr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.util.apr.service.AiProofreadSelfIntroService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class AiProofreadSelfIntroController {

	@Autowired
	public AiProofreadSelfIntroService aiProofreadSelfIntroService;

	@PostMapping("/ai/proofread/coverletter")
	@ResponseBody
	public ResponseEntity<String> proofreadCoverLetter(@RequestBody Map<String, List<Map<String, String>>> requestPayload) {
		log.info("📩 AI 첨삭 요청 수신 - URI: /ai/proofread/coverletter");
		long startTime = System.currentTimeMillis();

		List<Map<String, String>> selfIntroSections = requestPayload.get("sections");
		
		// AI 첨삭 서비스의 proofreadCoverLetter() 메서드를 호출하여
		// 병렬 처리와 응답 조합까지 한 번에 처리하도록 변경
		String result = aiProofreadSelfIntroService.proofreadCoverLetter(selfIntroSections);

		log.info("🚀 AI 첨삭 응답 완료 | 전체 소요: {}ms", System.currentTimeMillis() - startTime);
		return ResponseEntity.ok(result);
	}
}

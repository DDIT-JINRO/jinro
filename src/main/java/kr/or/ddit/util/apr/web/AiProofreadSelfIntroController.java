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
        List<Map<String, String>> selfIntroSections = requestPayload.get("sections");

        String aiResponseJson = aiProofreadSelfIntroService.proofreadCoverLetter(selfIntroSections);

        return ResponseEntity.ok(aiResponseJson);
    }
}

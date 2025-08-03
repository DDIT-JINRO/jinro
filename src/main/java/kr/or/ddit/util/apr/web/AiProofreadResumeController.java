package kr.or.ddit.util.apr.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.or.ddit.util.apr.service.AiProofreadResumeService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ai/proofread")
public class AiProofreadResumeController {

    @Autowired
    private AiProofreadResumeService aiProofreadResumeService;

    @PostMapping("/resume")
    public ResponseEntity<String> proofreadResume(@RequestBody Map<String, List<Map<String, String>>> payload) {
        List<Map<String, String>> resumeItems = payload.get("resume_items");
        String feedback = aiProofreadResumeService.proofreadResume(resumeItems);
        return ResponseEntity.ok(feedback);
    }
}


package kr.or.ddit.util.apr.service;

import java.util.List;
import java.util.Map;

public interface AiProofreadResumeService {
    String proofreadResume(List<Map<String, String>> resumeItems);
}


package kr.or.ddit.util.apr.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import kr.or.ddit.util.apr.service.AiProofreadResumeService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AiProofreadResumeServiceImpl implements AiProofreadResumeService {

    private final String apiKey = "AIzaSyAZr-m-tAKezgsWhJ-hPmUIeVP1nCnm_Cs"; // 실제 키
    private final Client client = Client.builder().apiKey(apiKey).build();
    private ExecutorService executor;

    @PostConstruct
    public void init() {
        executor = Executors.newCachedThreadPool();
    }

    @PreDestroy
    public void shutdown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    @Override
    public String proofreadResume(List<Map<String, String>> resumeItems) {
        if (resumeItems == null || resumeItems.isEmpty()) {
            throw new IllegalArgumentException("이력서 항목이 비어 있습니다.");
        }

        List<CompletableFuture<String>> futures = new ArrayList<>();

        for (int i = 0; i < resumeItems.size(); i++) {
            final int index = i;
            final Map<String, String> item = resumeItems.get(index);

            futures.add(CompletableFuture.supplyAsync(() -> {
                String label = item.getOrDefault("label", "항목");
                String content = item.getOrDefault("content", "");

                String prompt = String.format("""
                    당신은 이력서 첨삭 전문가입니다.
                    아래 항목을 간결하고 명확하게 평가하고, 300자 이내로 피드백을 작성해주세요.
                    
                    항목명: %s
                    입력 내용: %s
                    
                    피드백은 다음 기준으로 작성해주세요:
                    - 개선할 점
                    - 더 나은 표현 제안
                    - 누락 또는 과장된 부분
                    """, label, content);

                try {
                    GenerateContentResponse response = client.models.generateContent("gemini-1.5-flash", prompt, null);
                    return String.format("[항목 %d - %s]\n%s", index + 1, label, response.text());
                } catch (Exception e) {
                    log.error("❌ [%s] 처리 실패: {}", label, e.getMessage());
                    return String.format("[항목 %d - %s]\n(피드백 생성 실패)", index + 1, label);
                }
            }, executor));
        }

        List<String> feedbacks = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        return String.join("\n---\n", feedbacks);
    }
}


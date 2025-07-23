package kr.or.ddit.util.ai.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
//제미나이 챗봇 컨트롤러
public class GenerateTextFromTextInput {
	
	//API 키
	String apiKey = "AIzaSyA8qDS5UDxsmNB9MmHCCinfgQrfhY94IXc";
	
	// 심리 상담 챗봇 role 프롬프트
	String systemPrompt1 = "넌 심리상담 챗봇이야. 사용자가 묻는 말에 대해 공감해주고, 이에 대한 해결책을 주면 될거여. "
			+ "근데 만약 사용자가 심리 상담과 먼 얘기를 한다면 넌 심리 챗봇이니까 다시 화제를 심리로 잡아야해."; 

	// 취업 상담 챗봇 role 프롬프트
	String systemPrompt2 = "넌 취업상담 챗봇이야. 사용자가 묻는 말에 대해 공감해주고, 이에 대한 해결책을 주면 될거여. "
			+ "근데 만약 사용자가 취업 상담과 먼 얘기를 한다면 넌 심리 챗봇이니까 다시 화제를 취업으로 잡아야해."; 

	// 진로 상담 챗봇 role 프롬프트
	String systemPrompt3 = "넌 진로상담 챗봇이야. 사용자가 묻는 말에 대해 공감해주고, 이에 대한 해결책을 주면 될거여. "
			+ "근데 만약 사용자가 진로 상담과 먼 얘기를 한다면 넌 심리 챗봇이니까 다시 화제를 진로로 잡아야해."; 

	
	//테스트용 JSP
	@GetMapping("/ai")
	public String openJSP() {
		return "aiChat/aiChatUI";
	}
	

    @PostMapping("/ai/chatbot")
    @ResponseBody
    public String geminiChat(@RequestBody Map<String, String> message) {
        
        // 제미니 api 연결
        Client client = Client.builder().apiKey(apiKey).build();

        String prompt = systemPrompt1 + "\n" + message.get("message");

        try {
        	// text 생성 요청
            GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", prompt, null);
            String result = response.text();
            log.info("응답 내용: {}", result);
            return result;
        } catch (Exception e) {
            log.error("Gemini API 오류", e);
            return "죄송합니다. 상담 도중 문제가 발생했어요.";
        }
    }
}
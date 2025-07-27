package kr.or.ddit.util.setle.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.or.ddit.util.setle.service.IamportApiClient;

@SpringBootApplication
public class IamportApiTest implements CommandLineRunner {

    private final IamportApiClient iamportApiClient;

    public IamportApiTest(IamportApiClient iamportApiClient) {
        this.iamportApiClient = iamportApiClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(IamportApiTest.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            String token = iamportApiClient.getAccessToken();
            System.out.println("✅ 발급된 Access Token: " + token);
        } catch (JsonProcessingException e) {
            System.err.println("❌ 토큰 발급 중 예외 발생: " + e.getMessage());
        }
    }
}

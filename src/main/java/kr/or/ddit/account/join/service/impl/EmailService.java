package kr.or.ddit.account.join.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendAuthCode(String toEmail) {
        log.info("코드발급 및 전송 도착");

        String authCode = generateAuthCode();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            // 👇 여기서 이름 포함해서 설정 가능
            try {
				helper.setFrom("jooth5501@gmail.com", "Career Path");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            helper.setTo(toEmail);
            helper.setSubject("이메일 인증 코드");
            helper.setText("<p>인증 코드: <strong>" + authCode + "</strong></p><p>3분 안에 입력해주세요.</p>", true); // HTML도 가능

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("메일 발송 실패", e);
            throw new RuntimeException("메일 발송 실패");
        }

        return authCode;
    }

    private String generateAuthCode() {
        int length = 6;
        String chars = "0123456789";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            code.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return code.toString();
    }

}

package com.LeagueOfLegendsGG.LOL.GG.util;

import com.LeagueOfLegendsGG.LOL.GG.config.EmailSendConfig;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class EmailSender {
    private final EmailSendConfig emailSendConfig;
    public String emailSender(String email) throws Exception {
        String to = email; // 받는 사람의 이메일 주소
        String from = emailSendConfig.getUsername(); // 보내는 사람의 이메일 주소
        String password = emailSendConfig.getPassword(); // 보내는 사람의 이메일 계정 비밀번호
        String host = emailSendConfig.getHost(); // 구글 메일 서버 호스트 이름

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", emailSendConfig.getPort());
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        String otp = generateOTP();
        String otpMessage = "인증번호: " + otp;

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject("인증번호");
        msg.setText(otpMessage);

        Transport.send(msg);

        return otp;
    }
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private String generateOTP() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}

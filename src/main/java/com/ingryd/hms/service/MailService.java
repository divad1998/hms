package com.ingryd.hms.service;

import com.ingryd.hms.entity.User;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmailVerificationMail(User user, int token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            messageHelper.setFrom("hospitalmanagementsystem1.0@gmail.com");
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Verify your email");
            messageHelper.setText("Dear " + user.getFirstName() + "," + " kindly hit: " + "http://localhost:8080/api/v1/verify-email/" + token + " to verify your email.", "utf-8");
            mailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(this.getClass().getName());
            logger.error(e.getMessage());
        }
    }

}

package com.ingryd.hms.service;

import com.ingryd.hms.entity.User;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            messageHelper.setTo(user.getEmail());
            messageHelper.setFrom("hospitalmanagementsystem1.0@gmail.com");
            messageHelper.setSubject("Verify your email");
            messageHelper.setText("Dear " + user.getFirstName() + "," + " kindly hit: " + "http://localhost:8080/api/v1/verify-email/" + token + " to verify your email.");
            mailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(this.getClass().getName());
            logger.error(e.getMessage());
        }
    }

    @Async
    public void sendResetPasswordMail(User user, int token){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            messageHelper.setSubject("Reset your password");
            messageHelper.setTo(user.getEmail());
            messageHelper.setFrom("hospitalmanagementsystem1.0@gmail.com");
            messageHelper.setText(String.format("Dear %s, \nYour reset token is %s. Click kindly hit: %s to reset your password. \nIf you didn't request this code, you can safely ignore this email", user.getFirstName(), token, "http://localhost:8080/api/v1/reset-password"));
            mailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e){
            Logger logger = LoggerFactory.getLogger(this.getClass().getName());
            logger.error(e.getMessage());
        }
    }

}

package com.ingryd.hms.service;

import com.ingryd.hms.entity.Token;
import com.ingryd.hms.entity.User;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
            messageHelper.setText("Dear " + user.getFirstName() + "," + " kindly hit: " + "http://localhost:8080/api/v1/auth/email_verification/" + token + " to verify your email.");

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
            messageHelper.setText(String.format("Dear %s, \nKindly hit: %s to reset your password. \nIf you didn't request this code, you can ignore this email", user.getFirstName(), "http://localhost:8080/api/v1/reset_password/" + token));
            mailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e){
            Logger logger = LoggerFactory.getLogger(this.getClass().getName());
            logger.error(e.getMessage());
        }
    }

    public void sendHospitalVerificationMail(User user, int token){
        try{
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

package com.ingryd.hms.service;

import com.ingryd.hms.dto.ForgottenPasswordDto;
import com.ingryd.hms.dto.PasswordDTO;
import com.ingryd.hms.entity.Token;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.repository.TokenRepository;
import com.ingryd.hms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidAlgorithmParameterException;
import java.util.Optional;

@Service
public class PasswordService {
    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final MailService mailService;

    private final TokenRepository tokenRepository;

    @Autowired
    public PasswordService (
            UserRepository userRepository,
            TokenService tokenService,
            MailService mailService,
            TokenRepository tokenRepository
        ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.mailService = mailService;
        this.tokenRepository = tokenRepository;
    }

    public void forgottenPassword(String email) throws Exception{
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new InvalidException("Invalid email.");
        if (!user.isEnabled())
            throw new InvalidException("Only verified users are allowed.");

        int token = tokenService.generateToken();
        tokenService.saveToken(token, user);
        mailService.sendResetPasswordMail(user, token);
    }

    public void passwordReset(PasswordDTO dto) throws Exception{
        Token token = tokenRepository.findByValue(dto.getToken());
        if (token != null){
            User user = token.getUser();
            assert user != null;
            if (dto.getNewPassword().equals(dto.getNewPasswordRetyped())) {
                user.setPassword(dto.getNewPassword());
                userRepository.save(user);
            }
        }
    }
}

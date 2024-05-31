package com.ingryd.hms.service;

import com.ingryd.hms.dto.PasswordDTO;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.repository.TokenRepository;
import com.ingryd.hms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenRepository tokenRepository;

    public void forgottenPassword(String email){
        User user = userRepository.findByEmail(email);
        int token = tokenService.generateToken();
        tokenService.saveToken(token, user);
        mailService.sendResetPasswordMail(user, token);
    }

    public void resetPassword(PasswordDTO dto){
        if (tokenRepository.findByValue(dto.getValue()).isPresent()){
            User user = tokenRepository.findByValue(dto.getValue()).get().getUser();
            assert user != null;
            if (dto.getNewPassword().equals(dto.getReconfirmPassword())) user.setPassword(dto.getNewPassword());
        }
    }
}

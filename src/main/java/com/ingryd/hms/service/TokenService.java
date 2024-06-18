package com.ingryd.hms.service;

import com.ingryd.hms.entity.Token;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    protected int generateToken() {
        int token = new Random().nextInt(0,9999);
        //existing?
        boolean exists = tokenRepository.findByValue(token).isPresent();
        if (exists) {
            generateToken();
        }
        return token;
    }

    protected Token saveToken(int value, User user) {
        Token token = new Token();
        token.setValue(value);
        token.setUser(user);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15L));
        return tokenRepository.save(token);
    }

    public Optional<Token> fetchByUserId(Long userId) {
        return tokenRepository.findByUser_id(userId);
    }

    public void delete(Token token) {
        tokenRepository.delete(token);
    }
}

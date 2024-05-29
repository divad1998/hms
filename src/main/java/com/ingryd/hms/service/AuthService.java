package com.ingryd.hms.service;

import com.ingryd.hms.dto.LoginDTO;
import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.Token;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Role;
import com.ingryd.hms.mapper.Mapper;
import com.ingryd.hms.repository.UserRepository;
import com.ingryd.hms.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final MailService mailService;

    @Transactional
    public void clientSignup(UserDTO userDTO) throws Exception {
        User user = Mapper.mapper.mapToUser(userDTO);
        user.setPassword(userDTO.getPassword()); // Set password from DTO
        user.setRole(Role.PATIENT);
        user = userRepository.save(user);
        int token = tokenService.generateToken();
        Token savedToken = tokenService.saveToken(token, user); // Save token
        //send verification mail
        mailService.sendEmailVerificationMail(user, savedToken.getValue());
    }

    public String login(LoginDTO loginDTO) {
        try {
            //is email valid?
            if (userRepository.findByEmail(loginDTO.getEmail()) == null) {
                throw new UsernameNotFoundException("Invalid email.");
            }
            //attempt auth
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            Authentication authentication = authManager.authenticate(token);
            //authentication success -> generate jwt token
            return jwtService.createToken((User) authentication.getPrincipal());
        } catch (AuthenticationException e) {
            throw e;
        }
    }
}

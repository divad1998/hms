package com.ingryd.hms.service;

import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.Token;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Role;
import com.ingryd.hms.mapper.Mapper;
import com.ingryd.hms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    //private final MailService mailService;

    @Transactional
    public void clientSignup(UserDTO userDTO) throws Exception {
        User user = Mapper.mapper.mapToUser(userDTO);
        user.setPassword(userDTO.getPassword()); // Set password from DTO
        user.setRole(Role.PATIENT);
        user = userRepository.save(user);
        int token = tokenService.generateToken();
        Token savedToken = tokenService.saveToken(token, user); // Save token
        //send verification mail
        //mailService.sendEmailVerificationMail(user, savedToken.getValue());
    }

}

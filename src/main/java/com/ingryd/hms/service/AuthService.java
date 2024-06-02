package com.ingryd.hms.service;

import com.ingryd.hms.dto.HospitalDTO;
import com.ingryd.hms.dto.LoginDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.Token;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Role;
import com.ingryd.hms.mapper.Mapper;
import com.ingryd.hms.repository.TokenRepository;
import com.ingryd.hms.repository.HospitalRepository;
import com.ingryd.hms.repository.UserRepository;
import com.ingryd.hms.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final HospitalRepository hospitalRepository;

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final MailService mailService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Transactional
    public ResponseEntity<Response> postHospital(HospitalDTO hospitalDTO){
        //save user
        User adminUser = new User();
        adminUser.setFirstName(hospitalDTO.getRegistrant_firstName());
        adminUser.setLastName(hospitalDTO.getRegistrant_lastName());
        adminUser.setPhoneNumber(hospitalDTO.getContactNumber());
        adminUser.setContactAddress(hospitalDTO.getAddress());
        adminUser.setRole(Role.ADMIN);
        adminUser.setPassword(hospitalDTO.getPassword());
        adminUser.setEmail(hospitalDTO.getEmail());
        User savedUser = userRepository.save(adminUser);

        //save hospital
        Hospital hospital = new Hospital();
        hospital.setHospitalName(hospitalDTO.getHospitalName());
        hospital.setAddress(hospitalDTO.getAddress());
        hospital.setBranch(hospitalDTO.getBranch());
        hospital.setCity(hospitalDTO.getCity());
        hospital.setCountry(hospitalDTO.getCountry());
        hospital.setState(hospitalDTO.getState());
        hospital.setHfrn(hospitalDTO.getHfrn());
        hospital.setContactNumber(hospitalDTO.getContactNumber());
        hospital.setEmail(hospitalDTO.getEmail());
        hospital.setWebsite(hospitalDTO.getWebsite());
        hospital.setRegisteredBy(savedUser);
        hospitalRepository.save(hospital);

        //generate token and send verification mail
        int token = tokenService.generateToken();
        Token savedToken = tokenService.saveToken(token, adminUser);
        //ToDo: send mail below

        //response
        Response response = new Response(true, "Signed up. Check mailbox to verify email quickly.", null);
        return ResponseEntity.status(201).body(response);
    }

    @Transactional
    public void clientSignup(UserDTO userDTO) throws Exception {
        User user = Mapper.mapper.mapToUser(userDTO);
        user.setPassword(userDTO.getPassword()); // Set password from DTO
        user.setRole(Role.PATIENT);
        user = userRepository.save(user);
        int token = tokenService.generateToken();
        Token savedToken = tokenService.saveToken(token, user);
        System.out.println(token);// Save token
        //send verification mail
        mailService.sendEmailVerificationMail(user, savedToken.getValue());
    }

    public void verifyEmail(int value){
        Token token = tokenRepository.findByValue(value).get();
        System.out.println(token);
        User user = token.getUser();
        user.setEnabled(true);
        userRepository.save(user);
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

    public void logout(String authToken){
        jwtService.invalidateToken(authToken);
    }
}

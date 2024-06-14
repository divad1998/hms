package com.ingryd.hms.service;

import com.ingryd.hms.dto.*;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.Token;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Profession;
import com.ingryd.hms.enums.Role;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.mapper.Mapper;
import com.ingryd.hms.repository.StaffRepository;
import com.ingryd.hms.repository.TokenRepository;
import com.ingryd.hms.repository.HospitalRepository;
import com.ingryd.hms.repository.UserRepository;
import com.ingryd.hms.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private final StaffRepository staffRepository;

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
        mailService.sendEmailVerificationMail(adminUser, token);

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
        //send verification mail
        mailService.sendEmailVerificationMail(user, savedToken.getValue());
    }

    public void emailVerification(int value) throws Exception{
        if (value > 0){
            Optional<Token> token = tokenRepository.findByValue(value);
            User user = token.get().getUser();
            assert user != null;
            user.setEnabled(true);
            userRepository.save(user);
        }
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

    @Transactional
    public ResponseEntity<Response> createStaff(StaffDTO staffDTO) throws InternalServerException {
        //create user
        User user = Mapper.mapper.mapToUser(staffDTO);
        if (staffDTO.getProfession().equals(Profession.MEDICAL_DOCTOR))
            user.setRole(Role.CONSULTANT);
        if (staffDTO.getProfession().equals(Profession.PHARMACIST))
            user.setRole(Role.PHARMACIST);
        if (staffDTO.getProfession().equals(Profession.LABORATORY_SCIENTIST))
            user.setRole(Role.LAB_SCIENTIST);

        User savedUser = userRepository.save(user);

        //create staff
        Staff staff = Mapper.mapper.mapToStaff(staffDTO);
        staff.setUser(savedUser);
        //get hospital and set on staff
        User admin = getAuthUser();
        Hospital hospital = hospitalRepository.findByEmail(admin.getEmail());
        if (hospital == null) {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            logger.error("Admin with email: " + admin.getEmail() + " not related with any hospital.");
            throw new InternalServerException("Internal server error. Kindly reach out to support.");
        }
        staff.setHospital(hospital);
        staffRepository.save(staff);

        //Token Service
        int token = tokenService.generateToken();
        tokenService.saveToken(token, user);
        mailService.sendEmailVerificationMail(user, token);

        //build response
        Response response = new Response();
        response.setStatus(true);
        response.setMessage("Staff created successfully. Verification mail is on its way to staff's mailbox.");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Fetches the authenticated user.
     * @return the authenticated user
     */
    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}

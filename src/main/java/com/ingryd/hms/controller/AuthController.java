package com.ingryd.hms.controller;


import com.ingryd.hms.dto.*;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.service.*;

import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/hospitals/signup")
    public ResponseEntity<Response> postHospital(@RequestBody @Valid HospitalDTO hospitalDTO) {
        return authService.postHospital(hospitalDTO);
    }

    @PostMapping("/patients/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> patientSignup(@RequestBody @Valid UserDTO userDTO) throws Exception {
        authService.patientSignup(userDTO);
        //build response on success
        Response response = new Response(true, "Signed up. Check mailbox to verify email quickly.", null);
        Link loginLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).login(null)).withRel("login");
        Link resendLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).resendVerificationMail()).withRel("resend_verification_mail");
        response.add(loginLink, resendLink);
        return ResponseEntity.of(Optional.of(response));
    }

    @GetMapping("/verify_email")
    public ResponseEntity<?> verifyEmail(int value) {
        authService.verifyEmail(value);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        String authToken = authService.login(loginDTO);
        //build response
        Map<String, Object> data = new HashMap<>();
        data.put("authToken", authToken);
        Response response = new Response(true, "Login successful.", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/email_verification/repeat")
    public ResponseEntity<?> resendVerificationMail() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader ("Authorization") String authToken, HttpServletRequest request){
        //get actual token
        String jwtToken = authToken.substring(7);

        authService.logout(jwtToken);
        Response response = new Response (true, "logout successful.", null);
        return ResponseEntity.ok(response);
    }
  
    @PostMapping("/forgotten-password")
    public ResponseEntity<String> forgottenPassword(@RequestBody @Valid ForgottenPasswordDto dto){
        passwordService.forgottenPassword(dto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(PasswordDTO dto){
        passwordService.resetPassword(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/staff/signup")
    public ResponseEntity<Response> createStaff(@RequestBody @Valid StaffDTO staffDTO) throws InternalServerException {
        return authService.createStaff(staffDTO);
    }
}

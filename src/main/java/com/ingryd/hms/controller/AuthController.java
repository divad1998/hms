package com.ingryd.hms.controller;

import com.ingryd.hms.dto.*;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.service.AuthService;
import com.ingryd.hms.service.PasswordService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingryd.hms.dto.PasswordDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.UserDTO;

import com.ingryd.hms.dto.HospitalDTO;

import com.ingryd.hms.dto.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
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
    public ResponseEntity<?> clientSignup(@RequestBody @Valid UserDTO userDTO) throws Exception {
        authService.clientSignup(userDTO);
        //build response on success
        Response response = new Response(true, "Signed up. Check mailbox to verify email quickly.", null);
        Link loginLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).login(null)).withRel("login");
        Link resendLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).resendVerificationMail()).withRel("resend_verification_mail");
        response.add(loginLink, resendLink);
        return ResponseEntity.status(201).body(Optional.of(response));
    }



    @PostMapping("/email_verification/{token}")
    public ResponseEntity<?> verifyEmail(@PathVariable @Valid int token) throws Exception {
        authService.emailVerification(token);
        Response response = new Response(true, "email verified.", null);
        return ResponseEntity.ok(response);
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
    public ResponseEntity<?> logout(@RequestHeader ("Authorization") String authToken){
        authService.logout(authToken);
        Response response = new Response (true, "logout successful", null);
        return ResponseEntity.ok(response);
    }
  
    @PostMapping("/forgotten_password/{email}")
    public ResponseEntity<?> forgottenPassword(@PathVariable @Valid String email) throws Exception {
        passwordService.forgottenPassword(email);
        Response response = new Response(true, "A token has been sent to your email address.", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> passwordReset(@RequestBody @Valid PasswordDTO dto) throws Exception {
        passwordService.passwordReset(dto);
        Response response = new Response(true, "password successfully changed.", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/staff/signup")
    public ResponseEntity<Response> staffSignup(@RequestBody @Valid StaffDTO staffDTO) throws InternalServerException {
        return authService.createStaff(staffDTO);
    }
}

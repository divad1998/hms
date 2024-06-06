package com.ingryd.hms.controller;
import com.ingryd.hms.dto.*;
import com.ingryd.hms.service.AuthService;
import com.ingryd.hms.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingryd.hms.dto.PasswordDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.UserDTO;

import com.ingryd.hms.dto.HospitalDTO;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ingryd.hms.dto.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private final AuthService authService;
    @Autowired
    private PasswordService passwordService;

    @PostMapping("/hospitals/signup")
    public ResponseEntity<Response> postHospital(@RequestBody @Valid HospitalDTO hospitalDTO) {
        return authService.postHospital(hospitalDTO);
    }

    @PostMapping("/patients/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> clientSignup(@RequestBody @Valid UserDTO userDTO) throws Exception {
        authService.clientSignup(userDTO);
        //build response on success
        Response response = new Response(true, "Signed up. Check mailbox to verify email quickly.", null);
        Link loginLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).login(null)).withRel("login");
        Link resendLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).resendVerificationMail()).withRel("resend_verification_mail");
        response.add(loginLink, resendLink);
        return ResponseEntity.of(Optional.of(response));
    }

    @PostMapping("/email_verification")
    public ResponseEntity<String> verifyEmail(@RequestBody @Valid VerificationDTO dto) {
        authService.emailVerification(dto.getValue());
        return new ResponseEntity<>("verified!", HttpStatus.OK); //ToDo: refactor
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
  
    @PostMapping("/forgotten_password")
    public ResponseEntity<String> forgottenPassword(@RequestBody @Valid ForgottenPasswordDto dto){
        passwordService.forgottenPassword(dto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@PathVariable @Valid PasswordDTO dto){
        passwordService.resetPassword(dto);
        return ResponseEntity.ok().build();
    }
}

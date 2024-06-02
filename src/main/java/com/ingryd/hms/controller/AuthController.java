package com.ingryd.hms.controller;
import com.ingryd.hms.dto.*;
import com.ingryd.hms.service.AuthService;
import com.ingryd.hms.service.PasswordService;
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

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/hospitals/signup")
    public ResponseEntity<Response> postHospital(@RequestBody @Valid HospitalDTO hospitalDTO) {
        return authService.postHospital(hospitalDTO);
    }

    @Autowired
    private PasswordService passwordService;


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
    public ResponseEntity<String> verifyEmail(VerificationDTO dto) {
        authService.verifyEmail(dto.getValue());
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
}

package com.ingryd.hms.controller;

import com.ingryd.hms.dto.PasswordDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.service.AuthService;
import com.ingryd.hms.service.PasswordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/patients/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> clientSignup(@RequestBody @Valid UserDTO userDTO) throws Exception {
        authService.clientSignup(userDTO);
        Response response = new Response(true, "Signed up. Check mailbox to verify email quickly.", null);
        Link loginLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).login()).withRel("login");
        Link resendLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).resendVerificationMail()).withRel("resend_verification_mail");
        response.add(loginLink, resendLink);
        return ResponseEntity.of(Optional.of(response));
    }

    @GetMapping("/verify_email")
    public ResponseEntity<?> verifyEmail() {
        return ResponseEntity.ok().build(); //ToDo: refactor
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().build(); //ToDo: refactor
    }

    @PostMapping("/email_verification/repeat")
    public ResponseEntity<?> resendVerificationMail() {
        return ResponseEntity.ok().build(); //ToDo: refactor
    }

    @PostMapping("/forgotten-password")
    public ResponseEntity<String> forgottenPassword(@RequestBody @Valid String email){
        passwordService.forgottenPassword(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(PasswordDTO dto){
        passwordService.resetPassword(dto);
        return ResponseEntity.ok().build();
    }
}

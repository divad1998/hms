package com.ingryd.hms.controller;

import com.ingryd.hms.dto.HospitalDTO;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ingryd.hms.dto.LoginDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospitals")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/allHospital")
    public ResponseEntity<Iterable<Hospital>> getAllHospital(){
        return authService.getAllHospital();
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable int id){
        return authService.getHospitalById(id);
    }

    @GetMapping("/hospital")
    public ResponseEntity<Hospital> getByHospitalName(@RequestParam String hospitalName){
        return authService.getByHospitalName(hospitalName);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> postHospital(@RequestBody HospitalDTO hospitalDTO) {
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

    @GetMapping("/verify_email")
    public ResponseEntity<?> verifyEmail() {
        return ResponseEntity.ok().build(); //ToDo: refactor
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        String authToken = authService.login(loginDTO);
        //build response
        Map<String, Object> data = new HashMap<>();
        data.put("authToken", authToken);
        Response response = new Response(true, "Login successful", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/email_verification/repeat")
    public ResponseEntity<?> resendVerificationMail() {
        return ResponseEntity.ok().build(); //ToDo: refactor
    }
}

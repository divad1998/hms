package com.ingryd.hms.controller;

import com.ingryd.hms.dto.HttpResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/logout")
    public ResponseEntity<HttpResponseDto> logout() {
        HttpResponseDto response = new HttpResponseDto("Logout successful", HttpStatus.OK, "AuthController");
        return ResponseEntity.ok(response);
    }
}

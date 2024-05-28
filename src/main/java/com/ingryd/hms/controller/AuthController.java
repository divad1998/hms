package com.ingryd.hms.controller;

import com.ingryd.hms.dto.HospitalDTO;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Hospital> postHospital(@RequestBody HospitalDTO hospitalDTO){
        return authService.postHospital(hospitalDTO);
    }
}

package com.ingryd.hms.controller;

import com.ingryd.hms.dto.RegisterPatientDto;
import com.ingryd.hms.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PatientController {

    private PatientService patientAccountService;


//    @PostMapping(value = ApiRoute.USERS)
    @PostMapping
    public ResponseEntity<?> registerPatient(@RequestBody  @Valid RegisterPatientDto dto) {
        RegisterPatientDto acct = patientAccountService.registerUser(dto);
        return new ResponseEntity<>(acct, HttpStatus.CREATED);
    }

}

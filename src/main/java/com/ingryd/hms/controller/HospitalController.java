package com.ingryd.hms.controller;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.service.HospitalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<Response> getAllHospitals(){
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getHospitalById(@PathVariable int id){
        return hospitalService.getHospitalById(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<Hospital> getByHospitalName(@RequestParam String hospitalName){
        return hospitalService.getByHospitalName(hospitalName);
    }

    @PostMapping("/{hospitalId}/patient-registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerPatientWithHospital (@PathVariable Long hospitalId, @RequestParam Long patientId) throws InternalServerException {
        hospitalService.registerPatientWithHospital(patientId, hospitalId);
        Response response = new Response(true, "Patient registered with hospital successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
//    @GetMapping
//    public ResponseEntity<?> registerPatientWithHospital (@PathVariable Long id) {
//        hospitalService.registerPatientWithHospital(id, hospitalId);
//        Response response = new Response(true, "Registration successful.", null);
//        return ResponseEntity.ok(response);
//
//    }
}
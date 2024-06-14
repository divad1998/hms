package com.ingryd.hms.controller;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.service.HospitalService;
import com.ingryd.hms.service.StaffService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;
    private final StaffService staffService;

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

    @PostMapping("/{id}/patient-registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerPatientWithHospital (@PathVariable Long id) throws InternalServerException {
        hospitalService.registerPatientWithHospital(id);
        Response response = new Response(true, "Registration successful.", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/patient-registration/hmo")
    public ResponseEntity<Response> registerPatientViaHMO(@PathVariable Long id, @NotEmpty(message = "hmo number can't be empty.") @RequestParam("hmo_number") String hmo_number) throws InternalServerException {
        hospitalService.registerPatientWithHMO(id, hmo_number);
        Response response = new Response(true, "Registration successful.", null);
        return ResponseEntity.status(201).body(response);
    }
}
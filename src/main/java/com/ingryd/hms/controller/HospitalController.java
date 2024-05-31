package com.ingryd.hms.controller;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.service.HospitalService;
import lombok.AllArgsConstructor;
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
}
package com.ingryd.hms.controller;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.service.HospitalPatientService;
import com.ingryd.hms.service.HospitalService;
import com.ingryd.hms.service.StaffService;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/hospitals")
@CrossOrigin
public class HospitalController {
    private final HospitalService hospitalService;
    private final HospitalPatientService patientService;
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

    @PostMapping("/{hospitalId}/patient-registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerPatientWithHospital (@PathVariable Long hospitalId) throws InternalServerException {
        hospitalService.registerPatientWithHospital(hospitalId);
        Response response = new Response(true, "Patient registered with hospital successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/patient-registration/hmo")
    public ResponseEntity<Response> registerPatientViaHMO(@PathVariable Long id, @NotEmpty(message = "hmo number can't be empty.") @RequestParam("hmo_number") String hmo_number) throws InternalServerException {
        hospitalService.registerPatientWithHMO(id, hmo_number);
        Response response = new Response(true, "Registration successful.", null);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/hospital_patients/{hospital_id}")
    public ResponseEntity<Response> getAllHospitalPatients (@PathVariable Long hospital_id) {
        List<HospitalPatient> patientList =  patientService.getAllHospitalPatients(hospital_id);
        Response response = Response.build(true, "Successful.", "Patients", patientList);
        return ResponseEntity.ok(response);
    }
}
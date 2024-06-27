package com.ingryd.hms.controller;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.StaffDTO;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {
    private final StaffService staffService;

    @GetMapping("/{hospital_Id}/consultants")
    public ResponseEntity<Response> getConsultantsBySpecialty(@RequestParam String specialty, @PathVariable Long hospital_Id) {
        List<Staff> consultants = staffService.getConsultantsBySpecialty(specialty, hospital_Id);
        Map<String, Object> map = new HashMap<>();
        map.put("consultants", consultants);
        Response response = new Response(true, "Successful.", map);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{hospital_Id}/consultant_specialties")
    public ResponseEntity<Response> getAllConsultantSpecialties(@PathVariable Long hospital_Id) throws InternalServerException, InvalidException {
        Set<String> specialties = staffService.getAllConsultantSpecialties(hospital_Id);
        Map<String, Object> map = new HashMap<>();
        map.put("specialties", specialties);
        Response response = new Response(true, "Successful.", map);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{hospital_Id}/consultants/no_specialty")
    //@PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> getNullSpecialtyConsultants (@PathVariable Long hospital_Id) throws InternalServerException, InvalidException {
        List<Staff> consultants = staffService.getNullSpecialtyConsultants(hospital_Id);
        //build response
        Response response = Response.build(true, "Successful.", "consultants", consultants);
        return ResponseEntity.ok(response);
    }
}
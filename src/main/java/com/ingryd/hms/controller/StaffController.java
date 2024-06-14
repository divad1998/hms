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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @GetMapping("/{hospital_Id}/consultants")
    public ResponseEntity<List<Staff>> getConsultantsBySpecialty(@RequestParam String specialty, @PathVariable Long hospital_Id) {
        List<Staff> consultants = staffService.getConsultantsBySpecialty(specialty, hospital_Id);
        return ResponseEntity.ok(consultants);
    }

    @GetMapping("/{hospital_Id}/consultant_specialties")
    public ResponseEntity<Set<String>> getAllConsultantSpecialties(@PathVariable Long hospital_Id) throws InternalServerException, InvalidException {
        Set<String> specialties = staffService.getAllConsultantSpecialties(hospital_Id);
        return ResponseEntity.ok(specialties);
    }
}

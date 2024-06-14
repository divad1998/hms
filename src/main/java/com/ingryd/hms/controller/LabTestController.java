package com.ingryd.hms.controller;

import com.ingryd.hms.dto.LabTestDTO;
import com.ingryd.hms.entity.LaboratoryTest;
import com.ingryd.hms.service.LabTestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lab_test")
public class LabTestController {

    private final LabTestService testService;

    @Autowired
    public LabTestController (LabTestService testService) {
        this.testService = testService;
    }

    @PostMapping("/create_test")
    @PreAuthorize("hasRole('LAB_SCIENTIST')")
    public ResponseEntity<LaboratoryTest> createLabTest(@RequestBody @Valid LabTestDTO testDTO) {
        return testService.createLabTest(testDTO);
    }
}

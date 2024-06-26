package com.ingryd.hms.controller;

import com.ingryd.hms.dto.LabTestDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.LaboratoryTest;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.service.LabTestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lab_tests")
public class LabTestController {

    private final LabTestService testService;

    public LabTestController (LabTestService testService) {
        this.testService = testService;
    }

    @PostMapping
    public ResponseEntity<Response> createLabTest(@RequestBody @Valid LabTestDTO testDTO) throws InvalidException {
        LaboratoryTest test = testService.createLabTest(testDTO);
        //response
        Response response = Response.build(true, "Successful.", "test", test);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> editLabTest(@PathVariable Long id, @RequestBody @Valid LabTestDTO dto) throws InvalidException {
        LaboratoryTest test = testService.editTest(id, dto);
        //response
        Response response = Response.build(true, "Successful.", "lab_test", test);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{consultation_id}")
    public ResponseEntity<Response> fetchTestsByConsultationId(@PathVariable Long consultation_id) throws InternalServerException, InvalidException {
        List<LaboratoryTest> laboratoryTests = testService.fetchTests(consultation_id);
        //response
        Response response = Response.build(true, "Successful.", "lab_tests", laboratoryTests);
        return ResponseEntity.ok(response);

    }
}

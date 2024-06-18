package com.ingryd.hms.controller;


import com.ingryd.hms.dto.MedicalHistoryDto;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.service.MedicalHistoryService;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medical-history")
public class MedicalHistoryController {
    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @GetMapping
    public ResponseEntity<Response> patientGetMedicalHistory() throws InvalidException {
        List<MedicalHistoryDto> medicalHistory = medicalHistoryService.patientGetMedicalHistory();
        //response
        Response response = Response.build(true, "Successful.", "medical_history", medicalHistory);
        return ResponseEntity.ok(response);

    }
}

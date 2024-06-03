package com.ingryd.hms.controller;

import com.ingryd.hms.dto.ConsultationDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;
    @PostMapping("create")
    public ResponseEntity<Response> createConsultation(@RequestBody @Valid ConsultationDTO consultationDTO){
        return consultationService.createConsultation(consultationDTO);
    }
}
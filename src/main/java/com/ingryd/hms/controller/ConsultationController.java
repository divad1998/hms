package com.ingryd.hms.controller;

import com.ingryd.hms.dto.ConsultationDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<Response> createConsultation(@RequestBody @Valid ConsultationDTO consultationDTO) throws InternalServerException {
        return consultationService.createConsultation(consultationDTO);
    }
}

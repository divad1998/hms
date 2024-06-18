package com.ingryd.hms.controller;

import com.ingryd.hms.dto.ConsultationDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<Response> createConsultation(@RequestBody @Valid ConsultationDTO consultationDTO) throws InternalServerException {
        return consultationService.createConsultation(consultationDTO);
    }

    @GetMapping
    public ResponseEntity<Response> fetchConsultations() throws InternalServerException {
        List<Consultation> consultations = consultationService.fetchConsultations();
        //build response
        Map<String, Object> map = new HashMap<>();
        map.put("consultations", consultations);
        Response response = new Response(true, "Successful.", map);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> fetchConsultationById(@PathVariable Long id) throws InternalServerException, InvalidException {
        Consultation consultation = consultationService.fetchConsultationById(id);
        //build response
        Map<String, Object> map = new HashMap<>();
        map.put("consultation", consultation);
        Response response = new Response(true, "Successful.", map);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> editConsultation(@PathVariable Long id, @RequestBody @Valid ConsultationDTO dto) throws InvalidException {
        Consultation consultation = consultationService.editConsultation(id, dto);
        Response response = Response.build(true,"Successful","consultation",consultation);
        return ResponseEntity.ok(response);
    }

}

package com.ingryd.hms.controller;

import com.ingryd.hms.dto.ConsultationDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping("/create")
    public ResponseEntity<Response> createConsultation(@RequestBody @Valid ConsultationDTO consultationDTO) throws InternalServerException {
        return consultationService.createConsultation(consultationDTO);
    }

    @GetMapping("/consultant/consultantId")
    public ResponseEntity<List<Consultation>> getConsultationsByConsultant(@PathVariable Long consultantId) {
        // You should add error handling and authentication logic to ensure only authorized consultants can access this endpoint
        Staff consultant = new Staff();
        consultant.setId(consultantId); // Assuming the Staff class has a setId method
        List<Consultation> consultations = consultationService.getAllConsultationsByConsultant(consultant);
        return ResponseEntity.ok(consultations);
    }
}

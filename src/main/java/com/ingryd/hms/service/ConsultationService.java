package com.ingryd.hms.service;

import com.ingryd.hms.dto.ConsultationDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.repository.ConsultationRepository;
import com.ingryd.hms.repository.HospitalClientRepository;
import com.ingryd.hms.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;

    private final StaffRepository staffRepository;

    private final HospitalClientRepository hospitalClientRepository;

    public ResponseEntity<Response> createConsultation(ConsultationDTO consultationDTO) {
        Optional<Staff> adminStaff = staffRepository.findById(consultationDTO.getStaff().getId());
        if (adminStaff.isEmpty()) {
            throw new RuntimeException("Staff not found");
        }
        Staff staff = adminStaff.get();

        Optional<HospitalPatient> existingHospital = hospitalClientRepository.findById(consultationDTO.getHospitalPatient().getId());
        if (existingHospital.isEmpty()) {
            throw new RuntimeException("HospitalPatient not found");
        }
        HospitalPatient hospitalPatient = existingHospital.get();

        Consultation consultation = Consultation.builder()
                .hospitalPatient(hospitalPatient)
                .staff(staff)
                .comment(consultationDTO.getComment())
                .preDiagnosis(consultationDTO.getPreDiagnosis())
                .testsToRun(consultationDTO.getTestsToRun())
                .prescription(consultationDTO.getPrescription())
                .referredTo(consultationDTO.getReferredTo())
                .completed(consultationDTO.isCompleted())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        consultationRepository.save(consultation);

        Response response = new Response();
        response.setMessage("Consultation created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

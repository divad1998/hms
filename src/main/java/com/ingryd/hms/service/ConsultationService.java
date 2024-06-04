package com.ingryd.hms.service;

import com.ingryd.hms.dto.ConsultationDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Profession;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.repository.ConsultationRepository;
//import com.ingryd.hms.repository.HospitalClientRepository;
import com.ingryd.hms.repository.HospitalPatientRepository;
import com.ingryd.hms.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultationService {

    private final ConsultationRepository consultationRepository;

    private final StaffRepository staffRepository;

    private final HospitalPatientRepository hospitalPatientRepository;
    private final AuthService authService;
    private final StaffService staffService;

    public ResponseEntity<Response> createConsultation(ConsultationDTO consultationDTO) throws InternalServerException {
        //validate logged in consultant
        User authUser = authService.getAuthUser();
        Staff consultant = staffService.getStaffByUserId(authUser.getId());
        if (consultant == null) {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            logger.error("User with id " + authUser.getId() + " is not related to any staff.");
            throw new InternalServerException("Internal error. Kindly contact support.");
        }

        //validate hospital patient
        Optional<HospitalPatient> existingPatient = hospitalPatientRepository.findById(consultationDTO.getHospital_patient_id());
        if (existingPatient.isEmpty()) {
            throw new IllegalArgumentException("HospitalPatient not found.");
        }
        HospitalPatient hospitalPatient = existingPatient.get();
        if (!hospitalPatient.getUser().isEnabled()) {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            logger.error("Hospital patient with id " + hospitalPatient.getId() + " is related to a disabled user with id " + hospitalPatient.getUser().getId());
            throw new InternalServerException("Internal error. Kindly contact support.");
        }

        //hospital patient has to be in same hospital as auth user
        if (!hospitalPatient.getHospital().getId().equals(consultant.getHospital().getId())) {
            throw new IllegalArgumentException("The patient is not registered with your hospital.");
        }

        //create consultation
        Consultation consultation = Consultation.builder()
                .hospitalPatient(hospitalPatient)
                .staff(consultant)
                .comment(consultationDTO.getComment())
                .preDiagnosis(consultationDTO.getPreliminary_diagnosis())
                .testsToRun(consultationDTO.getTestsToRun())
                .prescription(consultationDTO.getPrescription())
                .diagnosis(consultationDTO.getDiagnosis())
                .referredTo(consultationDTO.getReferredTo())
                .completed(consultationDTO.isCompleted())
                .hospital(consultant.getHospital())
                .build();

        consultationRepository.save(consultation);

        Response response = new Response();
        response.setMessage("Consultation created successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

package com.ingryd.hms.service;

import com.ingryd.hms.dto.ConsultationDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Profession;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
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
import java.util.List;
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
    private final HospitalPatientService patientService;

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
        Optional<HospitalPatient> existingPatient = hospitalPatientRepository.findById(Long.valueOf(consultationDTO.getHospital_patient_id()));
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

    public List<Consultation> fetchConsultations() throws InternalServerException {
        Staff consultant = staffService.validateAuthenticatedConsultant();
        return consultationRepository.findByStaff_IdAndHospital_Id(consultant.getId(), consultant.getHospital().getId());
    }

    /**
     * Fetches a consultation of the authenticated consultant
     * @param id of the consultation
     * @return
     * @throws InternalServerException
     */
    public Consultation fetchConsultationById(Long id) throws InternalServerException, InvalidException {
        Staff consultant = staffService.validateAuthenticatedConsultant();
        Consultation consultation = consultationRepository.findByIdAndStaff_IdAndHospital_Id(id, consultant.getId(), consultant.getHospital().getId());
        if (consultation == null)
            throw new InvalidException("Invalid Consultation.");
        return consultation;
    }

    public Consultation editConsultation(Long id, ConsultationDTO dto) throws InvalidException {
        //Test cases: endpoint, Consultant role, valid id (get by id, consultant id, and hospital id), validate patient, update, OK response
        //validate consultation
        User authUser = authService.getAuthUser();
        Staff consultant = staffService.getStaffByUserId(authUser.getId());
        Consultation consultation = consultationRepository.findByIdAndStaff_IdAndHospital_Id(id, consultant.getId(), consultant.getHospital().getId());
        if (consultation == null)
            throw new InvalidException("Invalid consultation.");
        //validate patient
        HospitalPatient hospitalPatient = patientService.getPatient(Long.valueOf(dto.getHospital_patient_id()), consultant.getHospital().getId());
        //update
        consultation.setHospitalPatient(hospitalPatient);
        consultation.setComment(dto.getComment());
        consultation.setPreDiagnosis(dto.getPreliminary_diagnosis());
        consultation.setTestsToRun(dto.getTestsToRun());
        consultation.setPrescription(dto.getPrescription());
        consultation.setDiagnosis(dto.getDiagnosis());
        consultation.setReferredTo(dto.getReferredTo());
        consultation.setCompleted(dto.isCompleted());

        return consultationRepository.save(consultation);
    }

    public Consultation fetchConsultationByIdAndHospitalId(Long id, Long hospitalId) throws InvalidException {
        Consultation consultation = consultationRepository.findByIdAndHospital_Id(id, hospitalId);
        if (consultation == null)
            throw new InvalidException("Invalid Consultation.");
        return consultation;
    }

    public List<Consultation> fetchConsultationsByPatientId(Long patientId) {
        return consultationRepository.findByHospitalPatient_Id(patientId);
    }
}

package com.ingryd.hms.service;

import com.ingryd.hms.dto.MedicalHistoryDto;
import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.LaboratoryTest;
//import com.ingryd.hms.entity.MedicalHistory;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.repository.LabTestRepository;
//import com.ingryd.hms.repository.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MedicalHistoryService {
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private AuthService authService;
    @Autowired
    private HospitalPatientService hospitalPatientService;
    @Autowired
    private LabTestService labTestService;


    public List<MedicalHistoryDto> patientGetMedicalHistory() throws InvalidException {
        //Test cases: endpoint, role, auth patient isn't registered with any hospital, consultations are empty, if consultation has no test, OK response
        //get all hospital patients related with auth user
        User authUser = authService.getAuthUser();
        List<HospitalPatient> list = hospitalPatientService.getHospitalPatients(authUser.getId());
        //get all consultations and lab tests
        List<MedicalHistoryDto> medical_history = new ArrayList<>();
        for (HospitalPatient patient : list) {
            List<Consultation> consultations = consultationService.fetchConsultationsByPatientId(patient.getId());
            if (!consultations.isEmpty()) {
                for (Consultation consultation : consultations) {
                    if (consultation.getTestsToRun() != null) {
                        List<LaboratoryTest> tests = labTestService.fetchLabTests(consultation.getId());
                        //add to medical history list
                        MedicalHistoryDto dto = new MedicalHistoryDto();
                        dto.setConsultation(consultation);
                        dto.setLab_tests(tests);
                        medical_history.add(dto);
                    }
                }
            }
        }
        return medical_history;
    }
}

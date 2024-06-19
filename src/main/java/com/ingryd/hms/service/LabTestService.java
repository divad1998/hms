package com.ingryd.hms.service;

import com.ingryd.hms.dto.LabTestDTO;
import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.entity.LaboratoryTest;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.repository.LabTestRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LabTestService {
    private final LabTestRepository testRepository;
    private final AuthService authService;
    private final StaffService staffService;
    private final ConsultationService consultationService;

    public LaboratoryTest createLabTest(LabTestDTO testDTO) throws InvalidException {
        //validate consultation
        User authUser = authService.getAuthUser();
        Staff labScientist = staffService.getStaffByUserId(authUser.getId());
        Consultation consultation = consultationService.fetchConsultationByIdAndHospitalId(Long.valueOf(testDTO.getConsultation_id()), labScientist.getHospital().getId());
        if (consultation.isCompleted())
            throw new InvalidException("Update refused. The Consultation is already complete.");
        //save
        LaboratoryTest labTest = new LaboratoryTest();
        labTest.setConsultation(consultation);
        labTest.setSample(testDTO.getSample());
        labTest.setLabUnit(testDTO.getLabUnit());
        labTest.setInvestigation(testDTO.getInvestigation());
        labTest.setResult(testDTO.getResult());
        labTest.setStaff(labScientist);

        return testRepository.save(labTest);
    }

    public LaboratoryTest editTest(Long id, LabTestDTO dto) throws InvalidException {
        //validate test
        User authUser = authService.getAuthUser();
        Staff labScientist = staffService.getStaffByUserId(authUser.getId());
        LaboratoryTest test = fetchTest(id, labScientist.getId());
        //validate consultation
        Consultation consultation = consultationService.fetchConsultationByIdAndHospitalId(Long.valueOf(dto.getConsultation_id()), labScientist.getHospital().getId());
        if (consultation.isCompleted())
            throw new InvalidException("Update refused. The Consultation is already complete.");
        //update
        test.setConsultation(consultation);
        test.setSample(dto.getSample());
        test.setLabUnit(dto.getLabUnit());
        test.setInvestigation(dto.getInvestigation());
        test.setResult(dto.getResult());

        return testRepository.save(test);
    }

    /**
     * Fetches a lab test from the database
     * @param id identifier of the test
     * @param scientistId
     * @return
     * @throws InvalidException
     */
    private LaboratoryTest fetchTest(Long id, Long scientistId) throws InvalidException {
        LaboratoryTest test = testRepository.findByIdAndStaff_Id(id, scientistId);
        if (test == null)
            throw new InvalidException("Invalid Lab test.");
        return test;
    }

    public List<LaboratoryTest> fetchLabTests(Long consultationId) {
        return testRepository.findByConsultation_Id(consultationId);
    }

    public List<LaboratoryTest> fetchTests(Long consultationId) throws InternalServerException, InvalidException {
        User authUser = authService.getAuthUser();
        Staff consultant = staffService.getStaffByUserId(authUser.getId());
        if (consultant == null) {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            logger.error("User with id " + authUser.getId() + " is not related to any staff.");
            throw new InternalServerException("Internal error. Kindly contact support.");
        }
        Consultation consultation = consultationService.fetchConsultation(consultationId, consultant.getId(),  consultant.getHospital().getId());
        return testRepository.findByConsultation_Id(consultation.getId());
    }
}

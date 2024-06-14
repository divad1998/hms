package com.ingryd.hms.service;

import com.ingryd.hms.dto.LabTestDTO;
import com.ingryd.hms.entity.LaboratoryTest;
import com.ingryd.hms.repository.LabTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LabTestService {

    private final LabTestRepository testRepository;

    @Autowired
    public LabTestService (LabTestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public ResponseEntity<LaboratoryTest> createLabTest(LabTestDTO testDTO) {
        LaboratoryTest labTest = new LaboratoryTest();
        labTest.setConsultation(testDTO.getConsultation());
        labTest.setSample(testDTO.getSample());
        labTest.setLabUnit(testDTO.getLabUnit());
        labTest.setInvestigation(testDTO.getInvestigation());
        labTest.setResult(testDTO.getResult());
        labTest.setStaff(testDTO.getStaff());
        return new ResponseEntity<>(testRepository.save(labTest), HttpStatus.CREATED);
    }

}

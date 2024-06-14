package com.ingryd.hms.service;

import com.ingryd.hms.dto.MedicalHistoryDto;
import com.ingryd.hms.entity.MedicalHistory;
import com.ingryd.hms.repository.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MedicalHistoryService {
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    public List<MedicalHistoryDto> getMedicalHistoryByPatientId(Long patientId){

        List<MedicalHistory> histories = medicalHistoryRepository.findByPatientId(patientId);
        return histories.stream().map(this::convertToDTO).collect(Collectors.toList());

    }

    private MedicalHistoryDto convertToDTO(MedicalHistory medicalHistory){

        MedicalHistoryDto dto =  new MedicalHistoryDto();
        dto.setId(medicalHistory.getId());
        dto.setPatientId(medicalHistory.getPatientId());
        dto.setDescription(medicalHistory.getDescription());
        dto.setDate(medicalHistory.getDate());
        return dto;
    }


}

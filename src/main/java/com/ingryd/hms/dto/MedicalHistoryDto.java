package com.ingryd.hms.dto;

import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.entity.LaboratoryTest;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MedicalHistoryDto {
    private Consultation consultation;
    private List<LaboratoryTest> lab_tests;
}

package com.ingryd.hms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MedicalHistoryDto {

    private Long id;
    private Long patientId;
    private String description;

    private Date date;


}

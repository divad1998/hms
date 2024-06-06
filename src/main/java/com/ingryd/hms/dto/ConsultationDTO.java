package com.ingryd.hms.dto;

import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.Staff;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationDTO {
    private long hospital_patient_id;

//    @NotEmpty(message = "Consultant id must contain a non-whitespace value.")
//    private long consultant_id;

    @NotBlank(message = "Comment can't be blank.")
    @NotNull(message = "A comment is required.")
    @Length(max = 1000, message = "Comment cannot exceed 1000 characters.")
    private String comment;

    @Length(max = 500, message = "Preliminary diagnosis cannot exceed 500 characters.")
    private String preliminary_diagnosis;

    @Length(max = 1000, message = "Tests to be ran can't exceed 1000 characters.")
    private String testsToRun;

    @Length(max = 5000, message = "Prescription can't exceed 5000 characters.")
    private String prescription;

    //private boolean medicationDispensed;

    @Length(max = 500, message = "Diagnosis cannot exceed 500 characters.")
    private String diagnosis;

    @Length(max = 1000, message = "The Referred can't exceed 1000 characters.")
    private String referredTo;

    private boolean completed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

package com.ingryd.hms.dto;

import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.Staff;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationDTO {

    private HospitalPatient hospitalPatient;

    private Staff staff;

    @NotBlank(message = "Comment can't be blank.")
    @NotNull(message = "A comment is required.")
    @Length(max = 1000, message = "Comment cannot exceed 1000 characters.")
    private String comment;

    @NotBlank(message = "Preliminary diagnosis can't be blank.")
    @Length(max = 100, message = "Preliminary diagnosis cannot exceed 100 characters.")
    private String preDiagnosis;

    @NotBlank(message = "Tests to be ran can't be blank.")
    @Length(max = 1000, message = "Tests to be ran can't exceed 1000 characters.")
    private String testsToRun;

    @NotBlank(message = "Prescription can't be blank.")
    @Length(max = 5000, message = "Prescription can't exceed 5000 characters.")
    private String prescription;

    private boolean medicationDispensed;

    @NotBlank(message = "The Referred can't be blank.")
    @Length(max = 1000, message = "The Referred can't exceed 1000 characters.")
    private String referredTo;

    private boolean completed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

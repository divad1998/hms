package com.ingryd.hms.dto;

import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.entity.Staff;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LabTestDTO {

    private Consultation consultation;

    @NotBlank(message = "Sample cannot be blank.")
    @NotNull(message = "Sample is required.")
    @Length(max = 50, message = "Sample cannot exceed 50 characters.")
    private String sample;

    @NotBlank(message = "Lab unit cannot be blank.")
    @NotNull(message = "Lab unit is required.")
    @Length(max = 50, message = "Lab unit cannot exceed 50 characters.")
    private String labUnit;

    @NotBlank(message = "Investigation cannot be blank.")
    @NotNull(message = "Investigation is required.")
    @Length(max = 255, message = "Investigation can't exceed 255 characters.")
    private String investigation;

    @NotBlank(message = "Result cannot be blank.")
    @NotNull(message = "Result is required.")
    @Length(max = 2000, message = "Result can't exceed 2000 characters")
    private String result;

    private Staff staff;
}

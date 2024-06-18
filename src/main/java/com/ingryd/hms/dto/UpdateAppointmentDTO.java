package com.ingryd.hms.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateAppointmentDTO {

//    @Length(max = 1000, message = "Reason cannot exceed 255 characters.")
//    private String reason;
    private boolean emergency;
    private LocalDate preferredDate;
    private LocalTime preferredTime;

    @NotEmpty(message = "Hospital patient must be specified.")
    private String hospitalPatientId;

    @Length(max = 50, message = "Consultant's specialty can't exceed 50 characters.")
    private String consultantSpecialty;

    @NotEmpty(message = "Consultant must be specified.")
    private String desiredConsultantId;
}

package com.ingryd.hms.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ConfirmAppointmentDto {

    @NotEmpty(message = "Appointment can't be empty.")
    private String appointmentId;
}

package com.ingryd.hms.dto;

import lombok.Data;

@Data
public class ConfirmAppointmentDto {

    private Long appointmentId;
    private boolean confirmed;

   private boolean issued;

}

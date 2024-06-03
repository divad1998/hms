package com.ingryd.hms.dto;

import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.Staff;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {

    @Length(max = 255, message = "Reason cannot exceed 255 characters.")
    private String reason;

    private boolean emergency;

    private LocalDate preferredDate;

    private LocalTime preferredTime;

    private HospitalPatient hospitalPatient;

    @Length(max = 50, message = "Consultant's specialty can't exceed 50 characters.")
    private String consultantSpecialty;

    private Staff desiredConsultant;

    private boolean issued;

    private Hospital hospital;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

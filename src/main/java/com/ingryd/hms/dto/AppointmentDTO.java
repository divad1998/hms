package com.ingryd.hms.dto;

import com.ingryd.hms.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {

//    private Long id;


    @Length(max = 1000, message = "Reason cannot exceed 255 characters.")
    private String reason;
    private boolean emergency;
    private LocalDate preferredDate;
    private LocalTime preferredTime;
//    private Long hospitalPatientId;

//    @Size(max = 50, message = "Consultant's specialty can't exceed 50 characters.")

    //private HospitalPatient hospitalPatient;

    @Length(max = 50, message = "Consultant's specialty can't exceed 50 characters.")

    private String consultantSpecialty;

    private Long desiredConsultantId;
//
//    private boolean issued;
//
//    private Long hospitalId;
//    private Staff desiredConsultant;

//    public Staff getDesiredConsultant() {
//        return desiredConsultant;
//    }
//
//    public void setDesiredConsultant(Staff desiredConsultant) {
//        this.desiredConsultant = desiredConsultant;
//    }

}

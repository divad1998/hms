package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByHospitalPatient_IdAndPreferredDateAndPreferredTime(Long hospitalPatientId, LocalDate preferredDate, LocalTime preferredTime);
    Appointment findByIdAndHospitalPatient_idAndHospital_id(Long id, Long hospitalPatientId, Long hospitalId);
    Appointment findByStaff_idAndPreferredDateAndPreferredTime(Long staff_id, LocalDate preferredDate, LocalTime preferredTime);
}

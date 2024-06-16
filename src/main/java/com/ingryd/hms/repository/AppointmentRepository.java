package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByHospitalPatient_IdAndPreferredDateAndPreferredTime(Long hospitalPatientId, LocalDate preferredDate, LocalTime preferredTime);
    Appointment findByIdAndHospitalPatient_idAndHospital_id(Long id, Long hospitalPatientId, Long hospitalId);
    Optional<Appointment> findByPreferredDateAndPreferredTime(LocalDate preferredDate, LocalTime preferredTime);

    List<Appointment> findByStaff_idAndPreferredDateAndPreferredTime(Long id, LocalDate preferredDate, LocalTime preferredTime);
}

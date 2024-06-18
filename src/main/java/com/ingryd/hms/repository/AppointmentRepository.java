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
    List<Appointment> findByStaff_idAndPreferredDateAndPreferredTime(Long staff_id, LocalDate preferredDate, LocalTime preferredTime);
    Appointment findByIdAndHospital_Id(Long id, Long hospitalId);
    List<Appointment> findByHospital_Id(Long hospital_Id);
    Optional<Appointment> findByPreferredDateAndPreferredTime(LocalDate preferredDate, LocalTime preferredTime);
    Appointment findByIdAndStaff_idAndHospital_id(Long id, Long staffId, Long hospitalId);
}

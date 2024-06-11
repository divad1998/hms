package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByPreferredDateAndPreferredTime(LocalDate preferredDate, LocalTime preferredTime);
}

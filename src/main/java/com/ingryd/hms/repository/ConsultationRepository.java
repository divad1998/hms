package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Consultation;
import com.ingryd.hms.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByStaff(Staff staff);
}

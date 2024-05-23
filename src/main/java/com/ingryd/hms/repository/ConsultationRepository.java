package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Long, Consultation> {
}

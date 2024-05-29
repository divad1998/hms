package com.ingryd.hms.repository;

import com.ingryd.hms.dto.RegisterPatientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<RegisterPatientDto, Long> {
}


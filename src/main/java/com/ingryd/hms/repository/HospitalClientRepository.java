package com.ingryd.hms.repository;

import com.ingryd.hms.entity.HospitalPatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalClientRepository extends JpaRepository<HospitalPatient, Long> {
}

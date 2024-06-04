package com.ingryd.hms.repository;

import com.ingryd.hms.entity.HospitalPatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalPatientRepository extends JpaRepository<HospitalPatient, Long> {
    HospitalPatient findByUser_IdAndHospital_Id(Long userId, Long hospitalId);
    HospitalPatient findByHospitalNumber(String hospitalNumber);
}

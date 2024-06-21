package com.ingryd.hms.repository;

import com.ingryd.hms.entity.HospitalPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HospitalPatientRepository extends JpaRepository<HospitalPatient, Long> {
    List<HospitalPatient> findByUser_IdAndHospital_Id(Long userId, Long hospitalId);
    HospitalPatient findByHospitalNumber(String hospitalNumber);
    HospitalPatient findByUser_IdAndIdAndHospital_Id(Long userId, Long hospitalPatientId, Long hospitalId);
    HospitalPatient findByIdAndUser_Id(Long id, Long userId);
    HospitalPatient findByIdAndHospital_Id(Long id, Long hospitalId);
    List<HospitalPatient> findByUser_Id(Long userId);
}

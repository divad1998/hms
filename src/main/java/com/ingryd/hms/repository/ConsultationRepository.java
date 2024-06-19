package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByStaff_IdAndHospital_Id(Long staffId, Long hospitalId);
    Consultation findByIdAndStaff_IdAndHospital_Id(Long id, Long staffId, Long hospitalId);
    Consultation findByIdAndHospital_Id(Long id, Long hospitalId);
    List<Consultation> findByHospitalPatient_Id(Long hospitalPatient_Id);
    List<Consultation> findByHospital_Id(Long hospital_Id);
}

package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.PatientHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientHospitalRepository  extends JpaRepository <Hospital, Long> {
    void save(PatientHospital patientHospital);
}

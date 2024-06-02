package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Hospital findHospitalByHospitalName(String hospitalName);

    Hospital findHospitalById(int id);
}

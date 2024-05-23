package com.ingryd.hms.repository;

import com.ingryd.hms.entity.HospitalClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalClientRepository extends JpaRepository<Long, HospitalClient> {
}

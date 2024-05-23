package com.ingryd.hms.repository;

import com.ingryd.hms.entity.LaboratoryTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabTestRepository extends JpaRepository<LaboratoryTest, Long> {
}

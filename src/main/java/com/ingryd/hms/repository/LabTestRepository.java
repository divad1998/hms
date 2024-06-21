package com.ingryd.hms.repository;

import com.ingryd.hms.entity.LaboratoryTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabTestRepository extends JpaRepository<LaboratoryTest, Long> {
    LaboratoryTest findByIdAndStaff_Id(Long id, Long staffId);
    List<LaboratoryTest> findByConsultation_Id(Long consultationId);
}

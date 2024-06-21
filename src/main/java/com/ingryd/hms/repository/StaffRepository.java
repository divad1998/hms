package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.enums.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findBySpecialtyAndProfession(String specialty, Profession profession);
    Staff findByIdAndProfession(Long id, Profession profession);
    List<Staff> findByHospital_Id(Long hospitalId);
    Staff findByUser_Id(Long userId);
    List<Staff> findByHospital_IdAndProfession(Long hospitalId, Profession profession);
    List<Staff> findBySpecialtyAndHospital_IdAndProfession(String specialty, Long hospitalId, Profession profession);
}

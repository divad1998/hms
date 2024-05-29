package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
StaffRepository extends JpaRepository<Staff, Long> {
}

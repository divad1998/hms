package com.ingryd.hms.dao;


import com.ingryd.hms.dto.RegisterPatientDto;
import com.ingryd.hms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);

}

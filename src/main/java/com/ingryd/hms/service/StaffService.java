package com.ingryd.hms.service;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.StaffDTO;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Profession;
import com.ingryd.hms.enums.Role;
import com.ingryd.hms.repository.HospitalRepository;
import com.ingryd.hms.repository.StaffRepository;
import com.ingryd.hms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    private final UserRepository userRepository;

    private final HospitalRepository hospitalRepository;

    private final PasswordEncoder passwordEncoder;

    public List<Staff> getConsultantsBySpecialty(String specialty) {
        return staffRepository.findBySpecialtyAndProfession(specialty, Profession.MEDICAL_DOCTOR);
    }

    public Staff getConsultantById(Long id) {
        return staffRepository.findByIdAndProfession(id, Profession.MEDICAL_DOCTOR);
    }

    public List<Staff> getAllConsultantsOfAHospital(Long hospitalId) {
        return staffRepository.findByHospital_Id(hospitalId);
    }

    public Staff getStaffByUserId(Long userId) {
        return staffRepository.findByUser_Id(userId);
    }
}

package com.ingryd.hms.service;

import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Profession;
import com.ingryd.hms.enums.Role;
import com.ingryd.hms.repository.HospitalRepository;
import com.ingryd.hms.repository.StaffRepository;
import com.ingryd.hms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;

    private final MailService mailService;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;


    public boolean isAdminUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> adminUser = Optional.ofNullable(userRepository.findByEmail(email));

        if (adminUser.isPresent()){
            User user = adminUser.get();
            return user.getRole() == Role.ADMIN;
        }else {
            return false;
        }
    }


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

    public Set<String> getAllConsultantSpecialties(Long hospital_Id) {
        List<Staff> allStaff = staffRepository.findByHospital_Id(hospital_Id);
        Set<String> specialties = new HashSet<>();

        for (Staff staff : allStaff) {
            String specialty = staff.getSpecialty();
            if (specialty != null && !specialty.isEmpty()) {
                specialties.add(specialty.toLowerCase()); // Convert to lowercase and add to the set
            }
        }
        return specialties;
    }
}

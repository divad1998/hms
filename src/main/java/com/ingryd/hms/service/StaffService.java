package com.ingryd.hms.service;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.StaffDTO;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.User;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    private final UserRepository userRepository;

    private final HospitalRepository hospitalRepository;

    private final PasswordEncoder passwordEncoder;

    public boolean isAdminUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> adminUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (adminUser.isPresent()) {
            User user = adminUser.get();
            return user.getRole() == Role.ADMIN;
        } else {
            return false;
        }
    }
    public ResponseEntity<Response> createStaff(StaffDTO staffDTO) {
        if (!isAdminUser()) {
            throw new RuntimeException("Only admins can create staff members.");
        }

        Optional<Hospital> OptionalHospital = hospitalRepository.findById(staffDTO.getHospitalId());
        if (!OptionalHospital.isPresent()) {
            throw new RuntimeException("Hospital not found");
        }
        Hospital hospital = OptionalHospital.get();

        Optional<User> existingUser = userRepository.findById(staffDTO.getUserId());
        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {

            user = new User();
            user.setId(staffDTO.getUserId());
            user.setEmail(staffDTO.getEmail());
            user.setFirstName(staffDTO.getFirstName());
            user.setLastName(staffDTO.getLastName());
            user.setMiddleName(staffDTO.getMiddleName());
            user.setPhoneNumber(staffDTO.getPhoneNumber());
            user.setRole(staffDTO.getRole());
            user.setContactAddress(staffDTO.getContactAddress());
            user.setPassword(passwordEncoder.encode(staffDTO.getPassword()));

            userRepository.save(user);
        }

        Staff staff = new Staff();
        staff.setFirstName(staffDTO.getFirstName());
        staff.setMiddleName(staffDTO.getMiddleName());
        staff.setLastName(staffDTO.getLastName());
        staff.setPhoneNumber(staffDTO.getPhoneNumber());
        staff.setEmail(staffDTO.getEmail());
        staff.setRole(staffDTO.getRole());
        staff.setSpecialty(staffDTO.getSpecialty());
        staff.setLevel(staffDTO.getLevel());
        staff.setQualifications(staffDTO.getQualifications());
        staff.setUser(user);
        staff.setHospital(hospital);

        staffRepository.save(staff);
        Response response = new Response();
        response.setMessage("Staff created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

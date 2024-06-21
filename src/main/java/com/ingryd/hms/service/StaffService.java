package com.ingryd.hms.service;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.StaffDTO;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Profession;
import com.ingryd.hms.enums.Role;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.repository.HospitalRepository;
import com.ingryd.hms.repository.StaffRepository;
import com.ingryd.hms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffService {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final TokenService tokenService;
    private final AuthService authService;
    private final HospitalService hospitalService;

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
  
    public List<Staff> getConsultantsBySpecialty(String specialty, Long hospital_Id) {
        //Test cases:
        //valid hospital id in request, PATIENT, endpoint, case sensitivity, OK response
        return staffRepository.findBySpecialtyAndHospital_IdAndProfession(specialty, hospital_Id, Profession.MEDICAL_DOCTOR);
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

    /**
     * Fetches all specialties of Consultant staff
     * @param hospital_Id
     * @return
     * @throws InvalidException
     * @throws InternalServerException
     */
    public Set<String> getAllConsultantSpecialties(Long hospital_Id) throws InvalidException, InternalServerException {
        //validate hospital
        Hospital hospital = hospitalService.validateHospital(hospital_Id);
        //hospital has consultants?
        List<Staff> allConsultants = staffRepository.findByHospital_IdAndProfession(hospital.getId(), Profession.MEDICAL_DOCTOR);
        if (allConsultants.isEmpty())
            throw new InvalidException("The Hospital has no Consultant yet.");
        //get specialties
        Set<String> specialties = new HashSet<>();
        for (Staff consultant : allConsultants) {
            String specialty = consultant.getSpecialty();
            if (specialty != null) {
                specialties.add(specialty.toLowerCase()); // Convert to lowercase and add to the set
            }
        }
        if (specialties.isEmpty())
            throw new InvalidException("No Specialists found in the hospital.");
        return specialties;
    }

    public List<Staff> getNullSpecialistConsultant (Long hospital_id) {
        List<Staff> hospitalStaff = staffRepository.findByHospital_Id(hospital_id);

        List<Staff> filtered = hospitalStaff.stream()
                .filter(staff -> staff.getUser().isEnabled())
                .filter(staff -> Objects.equals(staff.getSpecialty(), null))
                .collect(Collectors.toList());

        System.out.println(filtered.size());

        return filtered;
    }

    /**
     * Checks whether auth user is linked to a valid consultant.
     * @return
     */
    public Staff validateAuthenticatedConsultant() throws InternalServerException {
        User authUser = authService.getAuthUser();
        Staff consultant = getStaffByUserId(authUser.getId());
        if (consultant == null) {
            log.error("User with id: " + authUser.getId() + " isn't related with a consultant entity.");
            throw new InternalServerException("Internal error. Kindly contact support.");
        }
        return consultant;
    }
}

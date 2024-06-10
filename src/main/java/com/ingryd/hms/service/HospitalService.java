package com.ingryd.hms.service;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.repository.HospitalPatientRepository;
import com.ingryd.hms.repository.HospitalRepository;
import com.ingryd.hms.repository.StaffRepository;
import com.ingryd.hms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalPatientRepository hospitalPatientRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final StaffRepository staffRepository;

    public ResponseEntity<Response> getAllHospitals(){
        Map<String, Object> data = new HashMap<>();
        data.put("hospitals", hospitalRepository.findAll());
        Response response = new Response(true, "Success.", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Response> getHospitalById(int id){
        Map<String, Object> data = new HashMap<>();
        data.put("hospital", hospitalRepository.findHospitalById(id));
        Response response = new Response(true, "Success.", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Hospital> getByHospitalName(String hospitalName){
        return new ResponseEntity<>(hospitalRepository.findHospitalByHospitalName(hospitalName), HttpStatus.OK);
    }

    /**
     * Registers a patient with a hospital.
     * @param hospitalId
     */
    @Transactional
    public void registerPatientWithHospital(Long hospitalId) throws InternalServerException {
        //ToDo: process payment
        //validate hospital
        Hospital hospital = validateHospital(hospitalId);
        User authUser = authService.getAuthUser();
        //is patient already registered?
        HospitalPatient hospitalPatient = hospitalPatientRepository.findByUser_IdAndHospital_Id(authUser.getId(), hospital.getId());
        if (hospitalPatient != null)
            throw new IllegalArgumentException("Patient already registered with the given hospital.");

        //register patient
        HospitalPatient hospitalPatientToBeSaved = new HospitalPatient();
        hospitalPatientToBeSaved.setHospitalNumber(generateHospitalNumber(hospital, authUser));
        hospitalPatientToBeSaved.setUser(authUser); //set patient
        hospitalPatientToBeSaved.setHospital(hospital);

        hospitalPatientRepository.save(hospitalPatientToBeSaved);
    }

    /**
     * Generates a unique hospital number for a patient.
     * @param hospital
     * @param user
     * @return
     */
    private String generateHospitalNumber(Hospital hospital, User user) {
        String hospitalInitials = hospital.getHospitalName().substring(0,3).toUpperCase();
        String patientInitials = user.getFirstName().substring(0,3).toUpperCase();
        int randInt = new Random().nextInt();
        String hospitalNumber = hospitalInitials + "-" + patientInitials + "-" + randInt;
        if (hospitalPatientRepository.findByHospitalNumber(hospitalNumber) != null) {
            return generateHospitalNumber(hospital, user);
        } else {
            return hospitalNumber;
        }
    }

    /**
     * Valdates hospital: checks whether hospital exists and whether the Hospital's admin has a verified email.
     * @param hospitalId
     * @return
     * @throws InternalServerException
     */
    public Hospital validateHospital(Long hospitalId) throws InternalServerException {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(()-> new IllegalArgumentException("Invalid hospital."));
        //has hospital's admin verified email?
        User hospitalAdmin = userRepository.findByEmail(hospital.getEmail());
        if (hospitalAdmin == null) {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            logger.error("Hospital with email: " + hospital.getEmail() + " not related with any user.");
            throw new InternalServerException("Internal server error. Kindly reach out to support.");
        }
        if (!hospitalAdmin.isEnabled())
            throw new IllegalStateException("The hospital isn't fully registered on this platform.");

        return hospital;
    }

    public List<Staff> getConsultantsBySpecialty(String specialty) {
        // Fetch all staff members from the repository
        List<Staff> allStaff = staffRepository.findAll();

        // Filter staff members by the given specialty (case-insensitive)
        return allStaff.stream()
                .filter(staff -> specialty.equalsIgnoreCase(staff.getSpecialty()))
                .collect(Collectors.toList());
    }
}

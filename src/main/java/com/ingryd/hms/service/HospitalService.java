package com.ingryd.hms.service;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.PatientHospital;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.repository.HospitalRepository;
import com.ingryd.hms.repository.PatientHospitalRepository;
import com.ingryd.hms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final PatientHospitalRepository patientHospitalRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public void registerPatientWithHospital(Long patientId, Long hospitalId){
        User patient = userRepository.findById(patientId).orElseThrow(()-> new IllegalArgumentException("Invalid patient id"));
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(()-> new IllegalArgumentException("invalid hospital id"));

        PatientHospital patientHospital = new PatientHospital();
        patientHospital.setPatient(patient);
        patientHospital.setHospital(hospital);
        patientHospital.setRegistrationDate(LocalDateTime.now().toString());

        patientHospitalRepository.save(patientHospital);
    }
}

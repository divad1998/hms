package com.ingryd.hms.service;

import com.ingryd.hms.dto.HospitalDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.object.Role;
import com.ingryd.hms.repository.HospitalRepository;
import com.ingryd.hms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final HospitalRepository hospitalRepository;

    private final UserRepository userRepository;

    public ResponseEntity<Iterable<Hospital>> getAllHospital(){
        return new ResponseEntity<>(hospitalRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Hospital> getHospitalById(int id){
        return new ResponseEntity<>(hospitalRepository.findHospitalById(id), HttpStatus.OK);
    }

    public ResponseEntity<Hospital> getByHospitalName(String hospitalName){
        return new ResponseEntity<>(hospitalRepository.findHospitalByHospitalName(hospitalName), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Hospital> postHospital(HospitalDTO hospitalDTO){
        //save user
        User adminUser = new User();
        adminUser.setFirstName(hospitalDTO.getFirstName());
        adminUser.setLastName(hospitalDTO.getLastName());
        adminUser.setPhoneNumber(hospitalDTO.getContactNumber());
        adminUser.setContactAddress(hospitalDTO.getAddress());
        adminUser.setRole(Role.ADMIN);
        adminUser.setPassword(hospitalDTO.getPassword());
        adminUser.setEmail(hospitalDTO.getEmail());
        User savedUser = userRepository.save(adminUser);

        //save hospital
        Hospital hospital = new Hospital();
        hospital.setHospitalName(hospitalDTO.getHospitalName());
        hospital.setAddress(hospitalDTO.getAddress());
        hospital.setBranch(hospitalDTO.getBranch());
        hospital.setCity(hospitalDTO.getCity());
        hospital.setCountry(hospitalDTO.getCountry());
        hospital.setState(hospitalDTO.getState());
        hospital.setHfrn(hospitalDTO.getHfrn());
        hospital.setContactNumber(hospitalDTO.getContactNumber());
        hospital.setEmail(hospitalDTO.getEmail());
        hospital.setWebsite(hospitalDTO.getWebsite());
        hospital.setRegisteredBy(savedUser);
        hospital.setCreatedAt(hospitalDTO.getCreatedAt());
        hospital.setUpdatedAt(hospitalDTO.getUpdatedAt());


        Hospital savedHospital = hospitalRepository.save(hospital);
        Response response = new Response(true, "Signed up. Check mailbox to verify email quickly.", null); ResponseEntity.status(201).body(response);
        return new ResponseEntity<>(savedHospital, HttpStatus.CREATED);
    }

}

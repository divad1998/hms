package com.ingryd.hms.service;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.repository.HospitalRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public ResponseEntity<Response> getAllHospitals(){
        Map<String, Object> data = new HashMap<>();
        data.put("hospitals", hospitalRepository.findAll());
        Response response = new Response(true, "Success.", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Response> getHospitalById(int id){
        Map<String, Object> data = new HashMap<>();
        data.put("hospitals", hospitalRepository.findHospitalById(id));
        Response response = new Response(true, "Success.", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Hospital> getByHospitalName(String hospitalName){
        return new ResponseEntity<>(hospitalRepository.findHospitalByHospitalName(hospitalName), HttpStatus.OK);
    }
}

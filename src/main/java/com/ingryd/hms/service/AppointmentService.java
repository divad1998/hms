package com.ingryd.hms.service;

import com.ingryd.hms.entity.Appointment;
import com.ingryd.hms.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    @Autowired
    private final AppointmentRepository appointmentRepository;

    public ResponseEntity<List<Appointment>> getAllAppointment(){
        return new ResponseEntity<>(appointmentRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Appointment> findById(long id){
        return new ResponseEntity<>(appointmentRepository.findById(id).get(), HttpStatus.OK);
    }

}

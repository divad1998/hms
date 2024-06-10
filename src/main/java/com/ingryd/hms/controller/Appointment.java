package com.ingryd.hms.controller;

import com.ingryd.hms.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class Appointment {

    @Autowired
    private static AppointmentService appointmentService;

    @GetMapping("/all")
    public ResponseEntity<List<com.ingryd.hms.entity.Appointment>> getAllAppointment(){
        return appointmentService.getAllAppointment();
    }

    @GetMapping("{id}")
    public ResponseEntity<com.ingryd.hms.entity.Appointment> getAppointmentById(@PathVariable long id){
        return appointmentService.findById(id);
    }
}
